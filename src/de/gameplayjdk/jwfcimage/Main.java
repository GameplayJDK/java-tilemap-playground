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

import javax.swing.*;

public class Main {

    public static final String WINDOW_TITLE = "java-wfc-image";
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = (Main.WINDOW_WIDTH / 16) * 9;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        MainContractInterface.View view = new MainView();
        MainContractInterface.Presenter presenter = new MainPresenter(view);

        view.openView();
    }

//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//            ex.printStackTrace();
//        }
//
//        JFrame frame = new JFrame(Main.WINDOW_TITLE);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        //JMenuBar menuBar = Main.createMenuBar();
//        //frame.setJMenuBar(menuBar);
//
//        JCanvas canvas = new JCanvas(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
//        frame.add(canvas);
//
//        frame.setResizable(false);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//
//        ImageLogic logic = new ImageLogic(canvas);
//
//        Loop loop = new Loop(logic);
//        loop.setActive(true);
//        loop.start();
//    }
}
