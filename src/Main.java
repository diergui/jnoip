
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import javax.swing.JOptionPane;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author diego
 */
public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException, IOException {

        boolean iconified = false;
        boolean server = false;

        // Reading parameters / arguments.
        if (args != null && args.length > 0) {

            for (final String arg : args) {

                if (arg.equalsIgnoreCase("m")) {
                    iconified = true;
                }

                if (arg.equalsIgnoreCase("s")) {
                    server = true;
                }

            }

        }

        // Reading de initial configurations.
        try {
            final File configFile = new File(ReadWriteTextFile.getMyPath() + "/config.cfg");
            Configs.readConfigs(configFile);
        } catch (Exception ex) {
            Configs.criticalError = ex.getMessage();
        }

        // Starting...
        if (server) {
            final JServer srv = new JServer();
        } else {
            final JMain win = new JMain(iconified);
        }

    }

}
