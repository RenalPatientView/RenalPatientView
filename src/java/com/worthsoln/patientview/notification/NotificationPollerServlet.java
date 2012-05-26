package com.worthsoln.patientview.notification;

import com.worthsoln.HibernateUtil;
import com.worthsoln.patientview.notification.Notification;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.Type;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationPollerServlet extends HttpServlet
{
    private static final Logger LOGGER = Logger.getLogger(NotificationPollerServlet.class);
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
            // Now some awful error handling :s
            try {
                Session session = HibernateUtil.currentSession();
                Transaction tx = session.beginTransaction();
                notifications = session.find("from " + Notification.class.getName());
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

            //TODO Do stuff


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
    }
}
