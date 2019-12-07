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

// TODO: Refactor.
package de.gameplayjdk.jwfcimage.image;

import de.gameplayjdk.jwfcimage.engine.data.TileMap;
import de.gameplayjdk.jwfcimage.loop.LoopCallbackInterface;
import de.gameplayjdk.jwfcimage.utility.Vector;

public class ImageLogic implements LoopCallbackInterface {

    private final ImageDataInterface imageData;
    private final Vector vector;
    private final Vector offset;

    private final ImageScreen screen;

    private TileMap tileMap;

    private double accumulator;

    public ImageLogic(ImageDataInterface imageData) {
        this.imageData = imageData;
        this.vector = new Vector();
        this.offset = new Vector();

        this.screen = new ImageScreen(this.imageData.getWidth(), this.imageData.getHeight());

        this.tileMap = new TileMap(16, 16, TileMap.TILE_SIZE);

        this.accumulator = 0.0D;
    }

    @Override
    public void update(double deltaTime) {
        this.tileMap.update(deltaTime);

        this.accumulator += deltaTime;
        if (this.accumulator >= 60.0D) {
            this.tileMap.generate();

            this.accumulator = 0.0D;
        }

        this.offset.combine(this.vector);
    }

    @Override
    public void render() {
        this.screen.clear();

        this.tileMap.render(this.screen, (int) this.offset.getX(), (int) this.offset.getY());

        // Copy screen content to the data returned by getData().
        this.screen.show(
                this.imageData
        );
    }

    public void moveOffset(Vector vector) {
        this.offset.combine(vector);
    }

    public void moveVector(Vector vector) {
        vector.normalize();

        this.vector.set(vector);
    }

    public TileMap getTileMap() {
        return this.tileMap;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }
}
