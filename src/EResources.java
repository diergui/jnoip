/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

/**
 *
 * @author Diego
 */
class Folder {

    public static final String IMAGES_PATH = "/images/";
}

public enum EResources {

    APP_LOGO(Folder.IMAGES_PATH + "ip.png");

    private final String filename;

    private EResources(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return filename;
    }

    public static Icon getIconPercent(final EResources iconName, int percent) {
        return getIcon(iconName, percent, 0);
    }

    public static Image getImagePercent(final EResources iconName, int percent) {
        return rescaleImage(iconName, percent, 0);
    }

    public static Icon getIcon(final EResources iconName, int wh) {
        return getIcon(iconName, wh, wh);
    }

    private static Image getImage(final EResources iconName, int w, int h) {
        return rescaleImage(iconName, w, h);
    }

    private static Icon getIcon(final EResources iconName, int w, int h) {
        Icon icono = null;

        try {
            icono = new ImageIcon(getImage(iconName, w, h));
        } catch (final Exception ex) {
            System.out.println("EResources >> No se pudo cargar el icono: " + ex);
        }

        return icono;
    }

    public static Image getImage(final EResources iconName, int wh) {
        return rescaleImage(iconName, wh, wh);
    }

    private static Image rescaleImage(EResources original, final int w, final int h) {
        try {

            BufferedImage src = ImageIO.read(getResource(original.getFilename()));

            if (original.getFilename().endsWith(".png")) {

                if (w == 0 && h == 0) { // No hacemos nada, pasa igual q como se levanta de archivo.

                } else if (w != 0 && h == 0) { // Asumimos que W es un porcentaje

                    final int pW = (w * src.getWidth()) / 100;
                    final int pH = (w * src.getHeight()) / 100;

                    src = Scalr.resize(src, Method.QUALITY, pW, pH, new BufferedImageOp[0]);

                } else { // Redimensionamos por tamanios W y H. (no porcentaje)

                    if (src.getWidth() != w || src.getHeight() != h) {

                        if (w > 32) {
                            src = Scalr.resize(src, Method.ULTRA_QUALITY, w, h, new BufferedImageOp[0]);
                        } else {
                            src = Scalr.resize(src, Method.QUALITY, w, h, new BufferedImageOp[0]/*OP_ANTIALIAS*/);
                        }

                    }

                }

            }

            return src;
        } catch (Exception ex) {
            System.out.println("EResources >> No se pudo cargar el icono: " + ex);
            return null;
        }
    }

    public static InputStream getResource(final String iconName) {
        return EResources.class
                .getResourceAsStream(iconName);
    }

    public static String getTextFileContent(final String txtResName) throws IOException {
        final StringBuilder sb = new StringBuilder();

        final BufferedReader br = new BufferedReader(new InputStreamReader(getResource(txtResName), "UTF-8"));

        for (int c = br.read(); c != -1; c = br.read()) {
            sb.append((char) c);
        }

        return sb.toString();
    }

    public static Icon getIcon(final EResources iconName) {
        return getIcon(iconName, 0);
    }

    public static Image getImage(final EResources iconName) {
        return rescaleImage(iconName, 0, 0);
    }

    public static Icon getIcon16(final EResources iconName) {
        return getIcon(iconName, 16);
    }

    public static Icon getIcon20(final EResources iconName) {
        return getIcon(iconName, 20);
    }

    public static Icon getIcon24(final EResources iconName) {
        return getIcon(iconName, 24);
    }

    public static Icon getIcon32(final EResources iconName) {
        return getIcon(iconName, 32);
    }

    public static Icon getIcon64(final EResources iconName) {
        return getIcon(iconName, 64);
    }

    public static Icon getIcon96(final EResources iconName) {
        return getIcon(iconName, 96);
    }

    public static Icon getIcon128(final EResources iconName) {
        return getIcon(iconName, 128);
    }

    public static Icon getIcon256(final EResources iconName) {
        return getIcon(iconName, 256);
    }

    public static Image getImage16(final EResources iconName) {
        return getImage(iconName, 16);
    }

    public static Image getImage20(final EResources iconName) {
        return getImage(iconName, 20);
    }

    public static Image getImage24(final EResources iconName) {
        return getImage(iconName, 24);
    }

    public static Image getImage32(final EResources iconName) {
        return getImage(iconName, 32);
    }

    public static Image getImage64(final EResources iconName) {
        return getImage(iconName, 64);
    }

    public static Image getImage96(final EResources iconName) {
        return getImage(iconName, 96);
    }

    public static Image getImage128(final EResources iconName) {
        return getImage(iconName, 128);
    }

    public static Image getImage256(final EResources iconName) {
        return getImage(iconName, 16);
    }
}
