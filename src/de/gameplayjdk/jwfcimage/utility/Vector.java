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

package de.gameplayjdk.jwfcimage.utility;

public class Vector {

    private double x;
    private double y;

    public Vector() {
        this(0.0D, 0.0D);
    }

    public Vector(double x, double y) {
        this.set(x, y);
    }

    public Vector(Vector vector) {
        this.set(vector);
    }

    public void set(Vector vector) {
        this.set(vector.getX(), vector.getY());
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void combine(Vector vector) {
        this.set(this.x + vector.getX(), this.y + vector.getY());
    }

    public void combine(double x, double y) {
        this.set(this.x + x, this.y + y);
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void reset() {
        this.set(0.0D, 0.0D);
    }

    public void normalize() {
        double x = Math.signum(this.x);
        double y = Math.signum(this.y);

        this.set(x, y);
    }

    @Override
    public String toString() {
        return String.format("%1$s[x = %2$.2f, y = %3$.2f]", this.getClass().getName(), this.x, this.y);
    }
}
