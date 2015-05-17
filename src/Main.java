
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;


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

        if (args != null && args.length > 0) {
            iconified = args[0].equalsIgnoreCase("m");
        }

        final JMain win = new JMain(iconified);

    }

}
