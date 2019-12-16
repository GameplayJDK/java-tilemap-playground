/*
 * The MIT License (MIT)
 * Copyright (c) 2019 GameplayJDK
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.gameplayjdk.jwfcimage;

import de.gameplayjdk.jwfcimage.utility.OperatingSystem;

import javax.swing.*;

public class Main {

    public static final String WINDOW_TITLE = "java-tilemap-playground";
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = (Main.WINDOW_WIDTH / 16) * 9;

    public static final int WINDOW_HEIGHT_BAR = 16 + 8;

    public static void main(String[] args) {
        Main.setupStyle();

        MainContractInterface.View view = new MainView();
        MainContractInterface.Presenter presenter = new MainPresenter(view);

        view.openView();
    }

    /**
     * Value for "apple.awt.brushMetalLook". Decide if the mac brushed metal style should be used for the application window.
     */
    public static final String SYSTEM_PROPERTY_APPLE_BRUSH_METAL_LOOK = "false";

    /**
     * Value for "apple.awt.textantialiasing". Decide if the fonts should be rendered using antialiasing or not.
     */
    private static final String SYSTEM_PROPERTY_APPLE_TEXT_ANTIALIASING = "false";

    /**
     * Value for "apple.laf.useScreenMenuBar". Decide if the mac system menu bar should be used for JMenuBar instead.
     */
    private static final String SYSTEM_PROPERTY_APPLE_USE_SCREEN_MENU_BAR = "true";

    private static void setupStyle() {
//        System.getProperties()
//                .list(System.out);

        if (OperatingSystem.isMac()) {
            // For more information on what is happening, see https://developer.apple.com/library/archive/technotes/tn2007/tn2196.html.
            // For even more information, see https://developer.apple.com/library/archive/documentation/Java/Conceptual/Java14Development/07-NativePlatformIntegration/NativePlatformIntegration.html.
            // Also, this mirror of a dead developer.apple.com article was a very useful resource: http://mirror.informatimago.com/next/developer.apple.com/documentation/Java/Conceptual/JavaPropVMInfoRef/Articles/JavaSystemProperties.html.

            System.setProperty("apple.awt.brushMetalLook", Main.SYSTEM_PROPERTY_APPLE_BRUSH_METAL_LOOK);
            System.setProperty("apple.awt.textantialiasing", Main.SYSTEM_PROPERTY_APPLE_TEXT_ANTIALIASING);

            System.setProperty("apple.laf.useScreenMenuBar", Main.SYSTEM_PROPERTY_APPLE_USE_SCREEN_MENU_BAR);

            System.setProperty("com.apple.mrj.application.apple.menu.about.name", Main.WINDOW_TITLE);
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }
}
