
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author diego
 */
public class DateTools {

    public static String millisToHHmmss(final Date date) {
        try {
            final DateFormat df = new SimpleDateFormat("HH:mm:ss");
            return df.format(date);
        } catch (Exception e) {
            return "?";
        }
    }

    public static String formatearFechaHora2(final Date date) {
        try {
            final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return df.format(date);
        } catch (Exception e) {
            return "?";
        }
    }

}
