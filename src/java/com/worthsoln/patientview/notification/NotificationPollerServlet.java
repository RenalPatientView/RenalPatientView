package com.worthsoln.patientview.notification;

import com.worthsoln.HibernateUtil;
import com.worthsoln.patientview.notification.Notification;
import com.worthsoln.patientview.TestResult;
import com.worthsoln.patientview.User;
import com.worthsoln.patientview.logon.UserMapping;
import net.sf.hibernate.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class NotificationPollerServlet extends HttpServlet
{
    private static final String PERIOD_PARAMETER = "periodMinutes";
    private final Timer timer = new Timer();

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        int periodMinutes = Integer.parseInt(config.getInitParameter(PERIOD_PARAMETER));

        int periodMilliseconds = periodMinutes * 60 * 1000;
        timer.schedule(new NotificationPoller(), 0, periodMilliseconds);

        System.out.println(NotificationPollerServlet.class.getName() + " started!!!");
    }

    @Override
    public void destroy()
    {
        timer.cancel();

        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        System.out.println("GET!");
        new NotificationPoller().run();
        System.out.println("GOT!");
        resp.setStatus(469);
    }

    private class NotificationPoller extends TimerTask {
        @Override
        public void run()
        {
            List notifications;
            // TODO Fix awful error handling :s
            // TODO The transaction semantics are wrong; we should do everything in one transaction to avoid missing things
            try {
                Session session = HibernateUtil.currentSession();
                Transaction tx = session.beginTransaction();
                notifications = session.find("from " + Notification.class.getName()); // TODO Filter by name
                tx.commit();
                HibernateUtil.closeSession();
            } catch (HibernateException e) {
                System.out.println("Error reading notifications: " + e);
                return;
            }
            Notification notification;
            if (notifications.size() > 0) {
                notification = (Notification)notifications.get(0);
            } else {
                notification = new Notification();
                notification.setName("NOTIFICATION");
            }
            System.out.println("Last notification: " + notification.getLastnotification());

            // Do stuff

            // TODO This should really be some nice HQL but that's a pain to write and debug in current setup
            List<TestResult> newTestResults = loadResultsNewerThan(notification.getLastnotification());
            if (!newTestResults.isEmpty()) {
                System.out.println("!Got new test results!");
                Set<User> usersToBeNotified = usersFromResults(newTestResults);
                notifyUsersOfResults(usersToBeNotified);
            } else {
                System.out.println("Got no new test results");
            }

            // Done stuff

            notification.setLastnotification(new Date());
            System.out.println("Notification now: " + notification.getLastnotification());
            try {
                Session session = HibernateUtil.currentSession();
                Transaction tx = session.beginTransaction();
                session.saveOrUpdate(notification);
                tx.commit();
                HibernateUtil.closeSession();
            } catch (HibernateException e) {
                System.out.println("Error persisting notification: " + e);
                return;
            }
        }

        private List<TestResult> loadResultsNewerThan(Date threshold) {
            List<TestResult> results;
            System.out.println("Getting results newer than " + threshold);
            try {
                Session session = HibernateUtil.currentSession();
                Transaction tx = session.beginTransaction();
                Query query = session.createQuery("from " + TestResult.class.getName() + " as testresult where testresult.datestamp > :threshold"); //TODO Confirm > not >=
                query.setTimestamp("threshold", threshold);
                results = query.list();
                tx.commit();
                HibernateUtil.closeSession();
            } catch (HibernateException e) {
                System.out.println("Error getting test results: " + e);
                return null;
            }

            return results;
        }

        private void notifyUsersOfResults(Set<User> usersToBeNotified) {
            System.out.println("Notifying users of results: " + usersToBeNotified);
            for (User user : usersToBeNotified) {
                System.out.println("Notifying user: " + user.getEmail());
            }
            System.out.println("Notified users of results: " + usersToBeNotified);
        }

        private Set<User> usersFromResults(List<TestResult> results) {
            //TODO Express this more concisely with HQL
            List<UserMapping> userMappings = userMappingsFromResults(results);
            List<User> users = usersFromUserMappings(userMappings);

            return new HashSet<User>(users);
        }

        private List<UserMapping> userMappingsFromResults(List<TestResult> results) {
            // "Optimisation"
            Set<String> nhsNumberSet = new HashSet<String>();
            for (TestResult result : results) {
                nhsNumberSet.add(result.getNhsno());
            }

            List<UserMapping> userMappings;
            try {
                Session session = HibernateUtil.currentSession();
                Transaction tx = session.beginTransaction();
                Query query = session.createQuery("from " + UserMapping.class.getName() + " as usermapping where usermapping.nhsno in (:nhsnumbers) and usermapping.unitcode == 'PATIENT'");
                query.setParameterList("nhsnumbers", new ArrayList(nhsNumberSet));
                userMappings = query.list();
                tx.commit();
                HibernateUtil.closeSession();
            } catch (HibernateException e) {
                System.out.println("Error getting user mappings: " + e);
                return null;
            }

            return userMappings;
        }

        private List<User> usersFromUserMappings(List<UserMapping> userMappings) {
            Set<String> usernameSet = new HashSet<String>();
            for (UserMapping userMapping : userMappings) {
                usernameSet.add(userMapping.getUsername());
            }

            List<User> users;
            try {
                Session session = HibernateUtil.currentSession();
                Transaction tx = session.beginTransaction();
                Query query = session.createQuery("from " + User.class.getName() + " as user where user.username in (:usernames)");
                query.setParameterList("usernames", new ArrayList(usernameSet));
                users = query.list();
                tx.commit();
                HibernateUtil.closeSession();
            } catch (HibernateException e) {
                System.out.println("Error getting user mappings: " + e);
                return null;
            }

            return users;
        }
    }
}
