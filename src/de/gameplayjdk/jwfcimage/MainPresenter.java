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

import de.gameplayjdk.jwfcimage.image.ImageDataInterface;
import de.gameplayjdk.jwfcimage.image.ImageLogic;
import de.gameplayjdk.jwfcimage.loop.Loop;
import de.gameplayjdk.jwfcimage.loop.LoopCallbackAdapter;
import de.gameplayjdk.jwfcimage.utility.Vector;

import java.io.File;

public class MainPresenter implements MainContractInterface.Presenter {

    private final MainContractInterface.View view;

    private final LoopCallbackAdapter callbackAdapter;

    private final Loop loop;

    private ImageLogic imageLogic;

    public MainPresenter(MainContractInterface.View view) {
        this.view = view;
        this.view.setPresenter(this);

        this.callbackAdapter = new LoopCallbackAdapter();

        this.loop = new Loop(this.callbackAdapter);

        this.imageLogic = null;
    }

    @Override
    public void start() {
        this.loop.setActive(true);
        this.loop.start();
    }

    @Override
    public void stop() {
        this.loop.setActive(false);
        if (this.loop.isAlive()) {
            try {
                this.loop.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void setImageData(ImageDataInterface imageData) {
        this.imageLogic = new ImageLogic(imageData);

        this.callbackAdapter.setCallback(imageLogic);
    }

    @Override
    public void openImage(File file) {
        // TODO: Implement.

        System.out.println("Not yet supported.");
    }

    @Override
    public void moveOffset(Vector vector) {
        this.imageLogic.moveOffset(vector);
    }

    @Override
    public void moveVector(Vector vector) {
        this.imageLogic.moveVector(vector);
    }
}
