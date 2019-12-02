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

public class Loop extends Thread {

    private static final int TARGET_TPS = 60;
    private static final int SKIP_LIMIT = 5;

    private boolean active;

    private final LoopCallbackInterface callback;

    public Loop(LoopCallbackInterface callback) {
        super("Loop");

        this.active = false;

        this.callback = callback;
    }

    @Override
    public void run() {
        long timeNow = 0L;
        long timeLast = System.currentTimeMillis();

        final double time = (1.0D / Loop.TARGET_TPS) * 1000;
        double delta = 0.0D;

        int skip = 0;

        while (this.active) {
            timeNow = System.currentTimeMillis();

            delta += (timeNow - timeLast) / time;
            timeLast = timeNow;

            skip = 0;
            while (delta >= 1.0D && skip < Loop.SKIP_LIMIT) {
                this.callback.update(delta);

                delta--;

                skip++;
                System.out.println(skip);
            }
            System.out.println();

            this.callback.render();
        }
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
