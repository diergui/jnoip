
import java.io.File;
import java.io.IOException;
import json.JSONException;
import json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author diego
 */
public class Configs {

    public static final String APP_NAME = "jNoip";
    public static final String APP_VERSION = "1.1";

    public static String criticalError = null;
    
    public static int updatetime_ok = 15;
    public static int updatetime_fail = 0;
    public static String hostname = "";
    public static String username = "";
    public static String password = "";

    public static void readConfigs(final File configFile) throws Exception {
        try {

            System.out.println("Config file path: " + configFile);

            final String fileContent = ReadWriteTextFile.read(configFile);

            final JSONObject json = new JSONObject(fileContent);
            Configs.hostname = json.getString("hostname").trim();
            Configs.username = json.getString("username").trim();
            Configs.password = json.getString("password").trim();
            Configs.updatetime_ok = json.getInt("updatetime_ok");
            Configs.updatetime_fail = json.getInt("updatetime_fail");

            if (Configs.updatetime_ok < 5) {
                Configs.updatetime_ok = 5;
            }

            if (Configs.updatetime_ok > 1440) {
                Configs.updatetime_ok = 1440;
            }

            if (Configs.updatetime_fail < 0) {
                Configs.updatetime_fail = 0;
            }

            if (Configs.updatetime_fail > 1440) {
                Configs.updatetime_fail = 1440;
            }

        } catch (JSONException ex) {
            throw new Exception("Invalid configuration file content. " + ex);

        } catch (IOException ex) {
            throw new Exception("Configuration file not found.");

        } catch (Exception ex) {
            throw ex;
        }
    }

}
