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

import de.gameplayjdk.jwfcimage.data.entity.EntityTileMap;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMapGenerator;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMapHandler;
import de.gameplayjdk.jwfcimage.swing.JCanvas;
import de.gameplayjdk.jwfcimage.swing.JStatusBar;
import de.gameplayjdk.jwfcimage.utility.Extension;
import de.gameplayjdk.jwfcimage.utility.Vector;
import de.gameplayjdk.jwfcimage.view.MenuData;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class MainView extends JFrame implements MainContractInterface.View {

    private MainView.WindowListener windowListener;
    private MainView.KeyListener keyListener;
    private MainView.MouseListener mouseListener;

    private MainContractInterface.Presenter presenter;

    private JCanvas canvas;
    private JStatusBar statusBar;

    private JMenu menuLoadFile;
    private JMenu menuSaveFile;
    private JMenu menuGenerate;
    private JMenu menuSwitch;

    public MainView() throws HeadlessException {
        super();

        this.windowListener = new MainView.WindowListener(this);
        this.keyListener = new MainView.KeyListener(this);
        this.mouseListener = new MainView.MouseListener(this);

        this.initialize();
    }

    private void initialize() {
        // Layout is BorderLayout by default.

        this.setTitle(Main.WINDOW_TITLE);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JMenuBar menuBar = this.createMenuBar();
        this.setJMenuBar(menuBar);

        JCanvas canvas = new JCanvas(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        this.add(canvas, BorderLayout.CENTER);

        this.canvas = canvas;

        JStatusBar statusBar = new JStatusBar(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT_BAR);
        this.add(statusBar, BorderLayout.SOUTH);

        this.statusBar = statusBar;

        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);

        this.addWindowListener(this.windowListener);

        this.canvas.addKeyListener(this.keyListener);

        this.canvas.addMouseListener(this.mouseListener);
        this.canvas.addMouseMotionListener(this.mouseListener);
        this.canvas.addMouseWheelListener(this.mouseListener);

        this.canvas.setFocusable(true);
        this.canvas.setFocusTraversalKeysEnabled(false);
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
                JMenu menuSub = new JMenu("Load file...");
                menuSub.setMnemonic(KeyEvent.VK_I);

                this.menuLoadFile = menuSub;

                menu.add(menuSub);
            }

            {
                JMenu menuSub = new JMenu("Save file...");
                menuSub.setMnemonic(KeyEvent.VK_E);

                this.menuSaveFile = menuSub;

                menu.add(menuSub);
            }

            menu.addSeparator();

            {
                JMenu menuSub = new JMenu("Generate...");
                menuSub.setMnemonic(KeyEvent.VK_G);

                this.menuGenerate = menuSub;

                menu.add(menuSub);
            }

            menu.addSeparator();

            {
                JMenu menuSub = new JMenu("Switch...");
                menuSub.setMnemonic(KeyEvent.VK_S);

                this.menuSwitch = menuSub;

                menu.add(menuSub);
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

            // TODO: Add website/github link.

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

        this.presenter.setImageData(this.canvas);
        this.presenter.start();

        this.setVisible(true);

        this.canvas.requestFocus();

        this.presenter.attachAvailableExtension();
    }

    @Override
    public void closeView() {
        this.windowListener.setUserCommand(false);

        this.presenter.stop();

        this.setVisible(false);
        this.dispose();
    }

    @Override
    public void showLoadFileDialog(int handlerId) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileFilter fileFilter = new MainView.FileFilterImage();
        fileChooser.setFileFilter(fileFilter);

        int state = fileChooser.showOpenDialog(this);

        if (JFileChooser.APPROVE_OPTION == state) {
            File file = fileChooser.getSelectedFile();

            this.presenter.loadFile(file, handlerId);
        }
    }

    private int i = 0;

    @Override
    public void showSaveFileDialog(int handlerId) {
        // TODO: Implement.
    }

    @Override
    public void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "java-wfc-image by GameplayJDK", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showStatusMessage(String message) {
        this.statusBar.addMessage(message);
    }

    @Override
    public void moveOffset(Vector vector) {
        this.presenter.moveOffset(vector);
    }

    @Override
    public void moveVector(Vector vector) {
        this.presenter.moveVector(vector);
    }

    @Override
    public void updateMenu(MenuData menuData) {
        this.updateMenuHandler(menuData.getListHandler());
        this.updateMenuGenerator(menuData.getListGenerator());
        this.updateMenuSwitch(menuData.getListMap());
    }

    private void updateMenuHandler(List<EntityTileMapHandler> listHandler) {
        this.menuLoadFile.removeAll();
        this.menuSaveFile.removeAll();

        for (final EntityTileMapHandler entity : listHandler) {
            if (entity.isSupportLoad()) {
                JMenuItem menuItem = new JMenuItem(entity.getName());
                menuItem.addActionListener(event -> this.showLoadFileDialog(entity.getId()));

                this.menuLoadFile.add(menuItem);
            }

            if (entity.isSupportSave()) {
                JMenuItem menuItem = new JMenuItem(entity.getName());
                menuItem.addActionListener(event -> this.showLoadFileDialog(entity.getId()));

                this.menuSaveFile.add(menuItem);
            }
        }

        this.menuLoadFile.revalidate();
        this.menuLoadFile.repaint();

        this.menuSaveFile.revalidate();
        this.menuSaveFile.repaint();
    }

    private void updateMenuGenerator(List<EntityTileMapGenerator> listGenerator) {
        this.menuGenerate.removeAll();

        for (final EntityTileMapGenerator entity : listGenerator) {
            JMenuItem menuItem = new JMenuItem(entity.getName());
            menuItem.addActionListener(event -> this.presenter.generateMap(entity.getId()));

            this.menuGenerate.add(menuItem);
        }

        this.menuGenerate.revalidate();
        this.menuGenerate.repaint();
    }

    private void updateMenuSwitch(List<EntityTileMap> listMap) {
        this.menuSwitch.removeAll();

        for (final EntityTileMap entity : listMap) {
            JMenuItem menuItem = new JMenuItem("Map #" + entity.getId());
            menuItem.addActionListener(event -> this.presenter.switchMap(entity.getId()));

            this.menuSwitch.add(menuItem);
        }

        this.menuSwitch.revalidate();
        this.menuSwitch.repaint();
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

        private final Vector vector;

        private final MainContractInterface.View view;

        public KeyListener(MainContractInterface.View view) {
            super();

            this.vector = new Vector();

            this.view = view;
        }

        @Override
        public void keyPressed(KeyEvent event) {
            super.keyPressed(event);

            switch (event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    this.vector.setX(+1.0D);

                    //System.out.println("VK_LEFT");

                    break;
                case KeyEvent.VK_DOWN:
                    this.vector.setY(-1.0D);

                    //System.out.println("VK_DOWN");

                    break;
                case KeyEvent.VK_RIGHT:
                    this.vector.setX(-1.0D);

                    //System.out.println("VK_RIGHT");

                    break;
                case KeyEvent.VK_UP:
                    this.vector.setY(+1.0D);

                    //System.out.println("VK_UP");

                    break;
            }

            this.view.moveVector(this.vector);
        }

        @Override
        public void keyReleased(KeyEvent event) {
            super.keyReleased(event);

            switch (event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                    this.vector.setX(0.0D);

                    //System.out.println("VK_LEFT");
                    //System.out.println("VK_RIGHT");

                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_UP:
                    this.vector.setY(0.0D);

                    //System.out.println("VK_DOWN");
                    //System.out.println("VK_UP");

                    break;
            }

            this.view.moveVector(this.vector);
        }
    }

    public static class MouseListener extends MouseAdapter {

        private final Vector vector;
        private final Vector origin;

        private final MainContractInterface.View view;

        public MouseListener(MainContractInterface.View view) {
            super();

            this.vector = new Vector();
            this.origin = new Vector();

            this.view = view;
        }

        @Override
        public void mousePressed(MouseEvent event) {
            super.mousePressed(event);

            this.changeCursor(event.getSource(), true);

            this.origin.set(event.getX(), event.getY());
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            super.mouseReleased(event);

            this.changeCursor(event.getSource(), false);

            this.origin.reset();
        }

        private void changeCursor(Object source, boolean moveActive) {
            int type = moveActive
                    ? Cursor.MOVE_CURSOR
                    : Cursor.DEFAULT_CURSOR;

            // TODO: This could be changed to check for instance of "javax.swing.Component".
            if (source instanceof JPanel) {
                ((JPanel) source).setCursor(Cursor.getPredefinedCursor(type));
            }
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            super.mouseDragged(event);

            if ((event.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                this.vector.set(event.getX(), event.getY());
                this.vector.combine(-1 * this.origin.getX(), -1 * this.origin.getY());

                this.view.moveOffset(this.vector);

                this.origin.combine(this.vector);
                this.vector.reset();
            }
        }
    }

    public static class FileFilterImage extends FileFilter {

        // Extension:
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

            String extension = Extension.getExtension(file);

            if (null != extension) {
                return extension.equals(MainView.FileFilterImage.EXTENSION_PNG);
            }

            return false;
        }

        @Override
        public String getDescription() {
            return "." + MainView.FileFilterImage.EXTENSION_PNG;
        }
    }
}
