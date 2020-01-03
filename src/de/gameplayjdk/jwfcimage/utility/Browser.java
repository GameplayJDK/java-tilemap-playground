package de.gameplayjdk.jwfcimage.utility;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public final class Browser {

    private Browser() {
    }

    public static boolean open(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

        if (null != desktop && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);

                return true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }

    public static boolean open(URL url) {
        try {
            URI uri = url.toURI();

            return Browser.open(uri);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean open(String string) {
        // TODO: Make sure exceptions are handled.
        URI uri = URI.create(string);

        return Browser.open(uri);
    }
}
