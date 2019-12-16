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

package de.gameplayjdk.jwfcimage.extension.simple;

import de.gameplayjdk.jwfcimage.data.handler.TileMapHandlerInterface;
import de.gameplayjdk.jwfcimage.engine.data.TileAbstract;
import de.gameplayjdk.jwfcimage.engine.data.TileMapAbstract;
import de.gameplayjdk.jwfcimage.extension.simple.data.TileCacheSimple;
import de.gameplayjdk.jwfcimage.extension.simple.data.TileMapSimple;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

public class TileMapHandlerSimple implements TileMapHandlerInterface {

    private static TileMapHandlerSimple instance;

    public static TileMapHandlerSimple getInstance() {
        if (null == TileMapHandlerSimple.instance) {
            TileMapHandlerSimple.instance = new TileMapHandlerSimple();
        }

        return TileMapHandlerSimple.instance;
    }

    private final TileCacheSimple tileMapCache;

    private TileMapHandlerSimple() {
        this.tileMapCache = TileCacheSimple.getInstance();
    }

    @Override
    public TileMapAbstract load(File file) {
        try {
            BufferedImage realImage = ImageIO.read(file);

            if (null == realImage) {
                return null;
            }

            BufferedImage image = this.createImage(realImage.getWidth(), realImage.getHeight(), realImage);
            int[] imageData = this.getImageData(image);

            return this.construct(realImage.getWidth(), realImage.getHeight(), imageData);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private TileMapAbstract construct(int width, int height, int[] imageData) {
        TileMapAbstract tileMap = new TileMapSimple(width, height, TileMapSimple.TILE_SIZE);
        TileAbstract[] map = tileMap.getMap();

        for (int index = 0; index < imageData.length; index++) {
            if (index == map.length) {
                break;
            }

            int color = imageData[index];
            map[index] = this.tileMapCache.fetch(color);
        }

        return tileMap;
    }

    @Override
    public boolean save(File file, TileMapAbstract tileMap) {
        BufferedImage realImage = this.createImage(tileMap.getWidth(), tileMap.getHeight());
        int[] imageData = this.getImageData(realImage);

        int[] data = this.deconstruct(tileMap);
        System.arraycopy(data, 0, imageData, 0, data.length);

        try {
            return ImageIO.write(realImage, "PNG", file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    private int[] deconstruct(TileMapAbstract tileMap) {
        TileAbstract[] map = tileMap.getMap();
        int[] imageData = new int[tileMap.getWidth() * tileMap.getHeight()];

        for (int index = 0; index < imageData.length; index++) {
            if (index == map.length) {
                break;
            }

            int color = map[index].getId();
            imageData[index] = color;
        }

        return imageData;
    }

    private BufferedImage createImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    private BufferedImage createImage(int width, int height, BufferedImage realImage) {
        BufferedImage image = this.createImage(width, height);

        Graphics graphics = image.createGraphics();
        graphics.drawImage(realImage, 0, 0, null);
        graphics.dispose();

        return image;
    }

    private int[] getImageData(BufferedImage image) {
        // TODO: This could use common code from an utility class.

        return (
                (DataBufferInt) image.getRaster()
                        .getDataBuffer()
        ).getData();
    }

    public String getName() {
        return "Simple (built-in)";
    }

    public boolean isSupportLoad() {
        return true;
    }

    public boolean isSupportSave() {
        return true;
    }
}
