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

import de.gameplayjdk.jwfcimage.image.ImageDataInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class JCanvas extends JPanel implements ImageDataInterface {

    private final Image image;

    public JCanvas(int width, int height) {
        super();

        Dimension dimension = new Dimension(width, height);

        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setMinimumSize(dimension);
        this.setMaximumSize(dimension);

//        this.setBorder(
//                BorderFactory.createEmptyBorder()
//        );

        this.image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);

        g.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), this);

        // Might not be the best idea, but currently this works.
        this.repaint();
    }

    @Override
    public int[] getData() {
        return (
                (DataBufferInt) ((BufferedImage) this.image).getRaster()
                        .getDataBuffer()
        ).getData();
    }
}
