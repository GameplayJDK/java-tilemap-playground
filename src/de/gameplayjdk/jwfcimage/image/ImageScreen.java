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

package de.gameplayjdk.jwfcimage.image;

import de.gameplayjdk.jwfcimage.engine.Sprite;
import de.gameplayjdk.jwfcimage.extension.simple.data.TileSimple;

import java.util.Arrays;

public class ImageScreen implements ImageDataInterface {

    private final int width;
    private final int height;

    private final int[] data;

    public ImageScreen(int width, int height) {
        this.width = width;
        this.height = height;

        this.data = new int[this.width * this.height];
    }

    /**
     * Clear the screen to black. This is a shortcut to the {@link #clear(int)} method.
     */
    public void clear() {
        this.clear(0x000000);
    }

    /**
     * Clear the screen to the specified color.
     *
     * @param color
     */
    public void clear(int color) {
        Arrays.fill(this.data, color);
    }

    /**
     * Draw a single pixel at the given position to the screen.
     *
     * @param x
     * @param y
     * @param color
     */
    public void drawPixel(int x, int y, int color) {
        if (!this.isVisible(x, y)) {
            return;
        }

        this.data[(y * this.width) + x] = color;
    }

    /**
     * Draw a rectangle at the given position and with the given bounds to the screen.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param color
     */
    public void drawRectangle(int x, int y, int width, int height, int color) {
        if (!this.isVisible(x, y) && !this.isVisible(x + width, y + height)
                && !this.isVisible(x + width, y) && !this.isVisible(x, y + height)) {
            return;
        }

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                if (!this.isVisible(x + w, y + h)) {
                    continue;
                }

                this.data[((y + h) * this.width) + (x + w)] = color;
            }
        }
    }

    public void drawRectangleOutline(int x, int y, int width, int height, int size, int color) {
        if (!this.isVisible(x, y) && !this.isVisible(x + width, y + height)
                && !this.isVisible(x + width, y) && !this.isVisible(x, y + height)) {
            return;
        }

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                if ((w >= size && w < (width - size)) && (h >= size && h < (height - size))) {
                    continue;
                }

                if (!this.isVisible(x + w, y + h)) {
                    continue;
                }

                this.data[((y + h) * this.width) + (x + w)] = color;
            }
        }
    }

    /**
     * @deprecated Prone to null pointer exception. Use {@link #drawSprite(int, int, Sprite)} instead in conjunction
     * with null check.
     */
    public void drawTile(int x, int y, TileSimple tile) {
        // TODO: Add null check.

        this.drawSprite(x, y, tile.getSprite());
    }

    public void drawSprite(int x, int y, Sprite sprite) {
        this.drawData(x, y, sprite);
    }

    public void drawData(int x, int y, ImageDataInterface imageData) {
        this.drawData(x, y, imageData.getWidth(), imageData.getHeight(), imageData.getData());
    }

    public void drawData(int x, int y, int width, int height, int[] data) {
        if (!this.isVisible(x, y) && !this.isVisible(x + width, y + height)
                && !this.isVisible(x + width, y) && !this.isVisible(x, y + height)) {
            return;
        }

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                if (!this.isVisible(x + w, y + h)) {
                    continue;
                }

                this.data[((y + h) * this.width) + (x + w)] = data[(h * width) + w];
            }
        }
    }

    public void show(ImageDataInterface imageData) {
        this.show(
                imageData.getData()
        );
    }

    public void show(int[] data) {
        if (data.length != this.data.length) {
            throw new IllegalArgumentException("Dimension mismatch! " + data.length + " != " + this.data.length);
        }

        System.arraycopy(this.data, 0, data, 0, this.data.length);
    }

    private boolean isVisible(int x, int y) {
        return ((x >= 0 && x < this.width) && (y >= 0 && y < this.height));
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int[] getData() {
//        return Arrays.copyOf(this.data, this.data.length);
        return this.data;
    }
}
