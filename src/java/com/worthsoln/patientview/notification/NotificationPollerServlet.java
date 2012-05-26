package com.worthsoln.patientview.notification;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Timer;
import java.util.TimerTask;

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
    }

    private class NotificationPoller extends TimerTask {
        @Override
        public void run()
        {
            System.out.println("!!!!!!!!");
        }
    }
}
