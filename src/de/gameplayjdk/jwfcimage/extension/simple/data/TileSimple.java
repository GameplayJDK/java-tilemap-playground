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

package de.gameplayjdk.jwfcimage.extension.simple.data;

import de.gameplayjdk.jwfcimage.engine.Color;
import de.gameplayjdk.jwfcimage.engine.Sprite;
import de.gameplayjdk.jwfcimage.engine.data.TileAbstract;
import de.gameplayjdk.jwfcimage.extension.simple.data.tile.TileSimpleEmpty;
import de.gameplayjdk.jwfcimage.extension.simple.data.tile.TileSimpleGround;
import de.gameplayjdk.jwfcimage.extension.simple.data.tile.TileSimpleWater;
import de.gameplayjdk.jwfcimage.image.ImageScreen;

public class TileSimple extends TileAbstract {

    public static TileSimple empty;
    public static TileSimple water;
    public static TileSimple ground;

    private static boolean initializeReady = false;

    public static void initialize() {
        if (!TileSimple.initializeReady) {
            TileSimple.empty = new TileSimpleEmpty();
            TileSimple.water = new TileSimpleWater();
            TileSimple.ground = new TileSimpleGround();

            TileSimple.initializeReady = true;
        }
    }

    public TileSimple(int id, Sprite sprite) {
        super(id, sprite);
    }

    public void update(double deltaTime) {
    }

    public void render(ImageScreen screen, int x, int y) {
        if (null == this.sprite) {
            return;
        }

        screen.drawSprite(x, y, this.sprite);
        screen.drawRectangleOutline(x, y, this.sprite.getWidth(), this.sprite.getHeight(), 1, Color.COLOR_SPRITE_BORDER);
    }
}
