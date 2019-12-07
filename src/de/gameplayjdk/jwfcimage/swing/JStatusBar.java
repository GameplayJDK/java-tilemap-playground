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

package de.gameplayjdk.jwfcimage.swing;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class JStatusBar extends JPanel {

    // TODO: Make message disappear after some time. Use javax.swing.Timer for that.

    private JLabel label;

    public JStatusBar(int width, int height) {
        super();

        Dimension dimension = new Dimension(width, height);

        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setMinimumSize(dimension);
        this.setMaximumSize(dimension);

        this.setBorder(
                BorderFactory.createBevelBorder(BevelBorder.LOWERED)
        );

        this.initialize();
    }

    private void initialize() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        this.setLayout(layout);

        Dimension dimension = new Dimension(8, 0);
        Component component = Box.createRigidArea(dimension);
        this.add(component);

        JLabel label = new JLabel();
        this.add(label);

        this.label = label;
    }

    public void setMessage(String message) {
        this.label.setText(message);
    }
}
