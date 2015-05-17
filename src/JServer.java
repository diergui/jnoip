
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.swing.JOptionPane;
import status.INoipStatus;
import status.NoipStatusFailedUpdate;
import status.NoipStatusNochange;
import status.NoipStatusSuccessfulUpdate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author diego
 */
public class JServer implements INoipStatusListener {

    private final JServer thisInstance = this;
    private INoipStatus lastStatus = null;

    private int threadDelay = 0;

    public JServer() {

        if (Configs.criticalError != null) {
            JOptionPane.showMessageDialog(null, Configs.criticalError);
            System.exit(1);
        }

        log("Initialization OK.");

        checkIt();
    }

    @Override
    public void setNoipStatus(INoipStatus status) {

        if (status instanceof NoipStatusFailedUpdate) {
            final NoipStatusFailedUpdate st = (NoipStatusFailedUpdate) status;

            if (st.getError() != null) {
                log(st.getError().getMessage());
            }

            if (st.getNextUpdate() != null) {
                log("Next update: " + DateTools.formatearFechaHora2(st.getNextUpdate()));
            }

            changeTimerDelay(Configs.updatetime_fail);

        } else if (status instanceof NoipStatusSuccessfulUpdate) {
            final NoipStatusSuccessfulUpdate st = (NoipStatusSuccessfulUpdate) status;

            if (st.getIp() != null) {
                log("CurrentIP: " + st.getIp());
            }

            log("Updated: " + DateTools.formatearFechaHora2(st.getUpdated()));
            log("Next update: " + DateTools.formatearFechaHora2(st.getNextUpdate()));

            if (st.getMessage() != null) {
                log(st.getMessage());
            }

            changeTimerDelay(Configs.updatetime_ok);

        } else if (status instanceof NoipStatusNochange) {
            final NoipStatusNochange st = (NoipStatusNochange) status;

            if (!(lastStatus instanceof NoipStatusNochange)) {
                log("Next update: " + DateTools.formatearFechaHora2(st.getNextUpdate()));
                log("No IP changed, until further notice.");
            }

            changeTimerDelay(Configs.updatetime_ok);
        }

        lastStatus = status;

    }

    private void checkIt() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(threadDelay);
                    } catch (InterruptedException ex) {
                    }

                    final UpdaterEngine noip = new UpdaterEngine(thisInstance);
                    noip.update();
                }

            }
        }).start();
    }

    private void log(final String message) {
        try {
            final File logFile = new File(ReadWriteTextFile.getMyPath() + "jnoip.log");

            final String logLine = "[" + DateTools.millisToHHmmss(new Date()) + "] " + message;

            System.out.println(logLine);
            ReadWriteTextFile.append(logFile, logLine);

        } catch (IOException ex) {
            System.err.println("ERROR LOGGING: " + ex);
        }
    }

    public void changeTimerDelay(int minutes) {

        if (minutes == 0) {
            threadDelay = 20000;
        } else {
            threadDelay = minutes * 60000;
        }

    }

}
