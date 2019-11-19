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

import de.gameplayjdk.jwfcimage.loop.LoopCallbackInterface;

public class ImageLogic implements LoopCallbackInterface {

    private final ImageDataInterface imageData;

    private final ImageScreen screen;

    // TODO: Implement real logic.
    private double n;

    public ImageLogic(ImageDataInterface imageData) {
        this.imageData = imageData;

        this.screen = new ImageScreen(this.imageData.getWidth(), this.imageData.getHeight());

        // TODO: Implement real logic.
        this.n = 0.0D;
    }

    @Override
    public void update(double deltaTime) {
        // TODO: Implement real logic.
        this.n += 0.0001D;
    }

    @Override
    public void render() {
        this.screen.clear();

        // TODO: Implement real logic.
        this.screen.drawPixel(10, 10, 0xFFFFFF);
        this.screen.drawRectangle((int) this.n, 50, 100, 20, 0xFF00FF);
        this.screen.drawRectangleOutline((int) this.n, 100, 100, 75, 10, 0x00FFFF);

        // Copy screen content to the data returned by getData().
        this.screen.show(
                this.imageData.getData()
        );
    }
}
