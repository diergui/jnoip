/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import status.NoipStatusFailedUpdate;
import status.NoipStatusSuccessfulUpdate;

/**
 *
 * @author diego
 */
public class UpdaterEngine {

    private final INoipStatusListener listener;

    public UpdaterEngine(final INoipStatusListener listener) {
        this.listener = listener;
    }

    public void update() {

        if (Configs.hostname.isEmpty()) {

            final NoipStatusFailedUpdate status = new NoipStatusFailedUpdate();
            status.setError(new Exception("Hostname can not be empty."));

            listener.setNoipStatus(status);

            return;
        }

        final String IP;

        try {
            IP = getCurrentIP();

        } catch (Exception ex) {

            final NoipStatusFailedUpdate status = new NoipStatusFailedUpdate();
            status.setError(new IOException("IP WAN not found. Detail: " + ex.getMessage()));
            status.setNextUpdate(new Date(System.currentTimeMillis() + (Configs.updatetime_fail * 60000)));

            listener.setNoipStatus(status);

            return;
        }

        try {

            submitIpUpdate(IP, Configs.hostname);

        } catch (Exception e) {

            final NoipStatusFailedUpdate status = new NoipStatusFailedUpdate();
            status.setError(e);
            status.setNextUpdate(new Date(System.currentTimeMillis() + (Configs.updatetime_fail * 60000)));

            listener.setNoipStatus(status);

        }

    }

    private String getCurrentIP() throws MalformedURLException, IOException {
        String IP;
        String host = "http://bot.whatismyipaddress.com";

        final URL url = new URL(host);

        final HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("User-Agent", Configs.APP_NAME + "/" + Configs.APP_VERSION + " diergui@gmail.com");

        final BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
        IP = br.readLine();
        br.close();

        System.out.println("Current IP: " + IP);

        return IP.trim();
    }

    private void submitIpUpdate(String newIP, String hostname) throws MalformedURLException, IOException, Exception {
        String host = "http://dynupdate.no-ip.com/nic/update?hostname=" + hostname + "&myip=" + newIP;

        final URL url = new URL(host);

        final HttpURLConnection http = (HttpURLConnection) url.openConnection();

        final String auth = Base64.encodeString(Configs.username + ":" + Configs.password);
        http.setRequestProperty("Authorization", "Basic " + auth);
        http.setRequestProperty("User-Agent", Configs.APP_NAME + "/" + Configs.APP_VERSION + " diergui@gmail.com");

        final BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));

        String line;

        while ((line = br.readLine()) != null) {

            System.out.println("NO-IP response: " + line);

            if (line.startsWith("good")) {

                final NoipStatusSuccessfulUpdate status = new NoipStatusSuccessfulUpdate();
                status.setIp(newIP);
                status.setMessage("IP updated successful: " + line.substring(5));
                status.setUpdated(new Date());
                status.setNextUpdate(new Date(System.currentTimeMillis() + (Configs.updatetime_ok * 60000)));

                listener.setNoipStatus(status);

            } else if (line.startsWith("nochg")) {

                final NoipStatusSuccessfulUpdate status = new NoipStatusSuccessfulUpdate();
                status.setIp(newIP);
                status.setMessage("No changes.");
                status.setUpdated(new Date());
                status.setNextUpdate(new Date(System.currentTimeMillis() + (Configs.updatetime_ok * 60000)));

                listener.setNoipStatus(status);

            } else if (line.startsWith("badauth")) {
                throw new Exception("Invalid login data");
            } else if (line.startsWith("badagent")) {
                throw new Exception("Bad user agent supplied");
            } else if (line.startsWith("abuse")) {
                throw new Exception("Account has been disabled");
            } else if (line.startsWith("911")) {
                throw new Exception("Server fall over");
            } else if (line.startsWith("!donator")) {
                throw new Exception("You cannot use donator features");
            } else {
                throw new Exception("Submit error: " + line);
            }
        }

        br.close();

    }

}
