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

package de.gameplayjdk.jwfcimage.loop;

public class LoopCallbackAdapter implements LoopCallbackInterface {

    private LoopCallbackInterface callback;

    public LoopCallbackAdapter() {
        this(null);
    }

    public LoopCallbackAdapter(LoopCallbackInterface callback) {
        this.callback = callback;
    }

    @Override
    public void update(double deltaTime) {
        if (!this.hasCallback()) {
            return;
        }

        this.callback.update(deltaTime);
    }

    @Override
    public void render() {
        if (!this.hasCallback()) {
            return;
        }

        this.callback.render();
    }

    public LoopCallbackInterface getCallback() {
        return this.callback;
    }

    public void setCallback(LoopCallbackInterface callback) {
        this.callback = callback;
    }

    public boolean hasCallback() {
        return null != this.callback;
    }
}
