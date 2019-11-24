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

import de.gameplayjdk.jwfcimage.swing.JCanvas;
import de.gameplayjdk.jwfcimage.utility.Velocity;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class MainView extends JFrame implements MainContractInterface.View {

    private MainView.WindowListener windowListener;
    private MainView.KeyListener keyListener;

    private MainContractInterface.Presenter presenter;

    private JCanvas canvas;
    private Velocity offset;

    public MainView() throws HeadlessException {
        super();

        this.windowListener = new MainView.WindowListener(this);
        this.keyListener = new MainView.KeyListener(this);

        this.initialize();
    }

    private void initialize() {
        this.setTitle(Main.WINDOW_TITLE);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JMenuBar menuBar = this.createMenuBar();
        this.setJMenuBar(menuBar);

        JCanvas canvas = new JCanvas(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        this.add(canvas);

        this.canvas = canvas;
        this.offset = new Velocity();

        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);

        this.addWindowListener(this.windowListener);

        canvas.addKeyListener(this.keyListener);
        canvas.setFocusable(true);
        canvas.setFocusTraversalKeysEnabled(false);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        {
            JMenu menu = new JMenu("File");
            menu.setMnemonic(KeyEvent.VK_F);

            {
                JMenuItem menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
                menuItem.addActionListener(event -> this.closeView());
                menu.add(menuItem);
            }

            menuBar.add(menu);
        }

        {
            JMenu menu = new JMenu("Map");
            menu.setMnemonic(KeyEvent.VK_M);

            {
                JMenuItem menuItem = new JMenuItem("Load image...", KeyEvent.VK_I);
                menuItem.addActionListener(event -> this.showOpenImageDialog());
                menu.add(menuItem);
            }

            menuBar.add(menu);
        }

        {
            JMenu menu = new JMenu("Help");
            menu.setMnemonic(KeyEvent.VK_H);

            {
                JMenuItem menuItem = new JMenuItem("About", KeyEvent.VK_A);
                menuItem.addActionListener(event -> this.showAboutDialog());
                menu.add(menuItem);
            }

            menuBar.add(menu);
        }

        return menuBar;
    }

    @Override
    public void setPresenter(MainContractInterface.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void openView() {
        this.windowListener.setUserCommand(true);

        this.presenter.setImageData(this.canvas, this.offset);
        this.presenter.start();

        this.setVisible(true);

        this.canvas.requestFocus();
    }

    @Override
    public void closeView() {
        this.windowListener.setUserCommand(false);

        this.presenter.stop();

        this.setVisible(false);
        this.dispose();
    }

    @Override
    public void showOpenImageDialog() {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileFilter fileFilter = new MainView.FileFilterImage();
        fileChooser.setFileFilter(fileFilter);

        int state = fileChooser.showOpenDialog(this);

        if (JFileChooser.APPROVE_OPTION == state) {
            File file = fileChooser.getSelectedFile();

            this.presenter.openImage(file);
        }
    }

    @Override
    public void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "java-wfc-image by GameplayJDK", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void moveOffset(Velocity velocity) {
        this.offset.combine(velocity);
    }

    public static class WindowListener extends WindowAdapter {

        private boolean userCommand;

        private final MainContractInterface.View view;

        public WindowListener(MainContractInterface.View view) {
            super();

            this.userCommand = true;

            this.view = view;
        }

        @Override
        public void windowClosing(WindowEvent event) {
            super.windowClosing(event);

            if (this.isUserCommand()) {
                this.view.closeView();
            }
        }

        public boolean isUserCommand() {
            return this.userCommand;
        }

        public void setUserCommand(boolean userCommand) {
            this.userCommand = userCommand;
        }
    }

    public static class KeyListener extends KeyAdapter {

        private final Velocity velocity;

        private final MainContractInterface.View view;

        public KeyListener(MainContractInterface.View view) {
            super();

            this.velocity = new Velocity();

            this.view = view;
        }

        @Override
        public void keyPressed(KeyEvent event) {
            super.keyPressed(event);

            this.velocity.reset();

            switch (event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    this.velocity.setX(+1.0D);

                    //System.out.println("VK_LEFT");

                    break;
                case KeyEvent.VK_DOWN:
                    this.velocity.setY(-1.0D);

                    //System.out.println("VK_DOWN");

                    break;
                case KeyEvent.VK_RIGHT:
                    this.velocity.setX(-1.0D);

                    //System.out.println("VK_RIGHT");

                    break;
                case KeyEvent.VK_UP:
                    this.velocity.setY(+1.0D);

                    //System.out.println("VK_UP");

                    break;
            }

            this.view.moveOffset(this.velocity);
        }
    }

    public static class FileFilterImage extends FileFilter {

        //        private static final String EXTENSION_JPEG = "jpeg";
//        private static final String EXTENSION_JPG = "jpg";
//        private static final String EXTENSION_GIF = "gif";
//        private static final String EXTENSION_TIFF = "tiff";
//        private static final String EXTENSION_TIF = "tif";
        private static final String EXTENSION_PNG = "png";

        public FileFilterImage() {
        }

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }

            String extension = this.getExtension(file);

            if (null != extension) {
                return extension.equals(MainView.FileFilterImage.EXTENSION_PNG);
            }

            return false;
        }

        @Override
        public String getDescription() {
            return "." + MainView.FileFilterImage.EXTENSION_PNG;
        }

        private String getExtension(File file) {
            String extension = null;
            String fileName = file.getName();

            int i = fileName.lastIndexOf('.');

            if (i > 0 && i < fileName.length() - 1) {
                extension = fileName.substring(i + 1)
                        .toLowerCase();
            }

            return extension;
        }
    }
}
