/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.Scanner;

/**
 * Read and write a file using an explicit encoding. Removing the encoding from
 * this code will simply cause the system's default encoding to be used instead.
 */
public final class ReadWriteTextFile {

    private static final String encoding = "ISO-8859-1";

    public static void append(final File outputFile, final String text) throws IOException {
        final PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));

        try {
            out.println(text);
        } finally {
            out.close();
        }

    }

    public static void write(final File outputFile, final String text) throws IOException {
        final Writer out = new OutputStreamWriter(new FileOutputStream(outputFile), encoding);

        try {
            out.write(text);
        } finally {
            out.close();
        }
    }

    public static String read(final FileInputStream inputFile) throws IOException {
        final StringBuilder text = new StringBuilder();
        final String NL = System.getProperty("line.separator");

        final Scanner scanner = new Scanner(inputFile, encoding);

        try {
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine()).append(NL);
            }
        } finally {
            scanner.close();
        }

        return text.toString();
    }

    public static String read(final File inputFile) throws IOException {
        final StringBuilder text = new StringBuilder();
        final String NL = System.getProperty("line.separator");

        final Scanner scanner = new Scanner(new FileInputStream(inputFile), encoding);

        try {
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine()).append(NL);
            }
        } finally {
            scanner.close();
        }

        return text.toString();
    }

    public static void hide(final File src) {
        try {
            // win32 command line variant
            Process p = Runtime.getRuntime().exec("attrib +h \"" + src.getPath() + "\"");
            p.waitFor(); // p.waitFor() important, so that the file really appears as hidden immediately after function exit.
        } catch (Exception ex) {
            System.err.println("Err hidding file: " + ex);
        }
    }

    public static String getMyPath() {

        String ret = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        if (!ret.equals("/")) {
            final String[] splited = ret.split("/");

            ret = "";

            for (int i = 0; i < (splited.length - 1); i++) {
                ret = ret + splited[i] + "/";
            }

        }

        return ret;
    }

}
