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

package de.gameplayjdk.jwfcimage.engine;

public class SpriteSheet extends Sprite {

    private final int spriteSize;

    private Sprite[] sprite;

    public SpriteSheet(int size, int spriteSize) {
        super(size * spriteSize);

        this.spriteSize = spriteSize;

        this.sprite = new Sprite[this.spriteSize * this.spriteSize];
    }

    public int getSpriteSize() {
        return this.spriteSize;
    }

    public int getSpriteWidth() {
        return (this.getWidth() / this.getSpriteSize());
    }

    public int getSpriteHeight() {
        return (this.getHeight() / this.getSpriteSize());
    }

    public Sprite getSprite(int spriteX, int spriteY) {
        if (!this.isSpriteVisible(spriteX, spriteY)) {
            return null;
        }

        int index = (spriteY * this.getSpriteSize()) + spriteX;

        Sprite sprite = this.sprite[index];

        // Lazy caching.
        if (null == sprite) {
            int size = this.getSize() / this.getSpriteSize();
            sprite = new Sprite(size);

            int[] data = sprite.getData();
            for (int w = 0; w < sprite.getWidth(); w++) {
                for (int h = 0; h < sprite.getHeight(); h++) {
                    data[(h * sprite.getWidth()) + w] = this.data[(((spriteY * this.getSpriteHeight()) + h) * sprite.getWidth()) + w];
                }
            }

            this.sprite[index] = sprite;
        }

        return sprite;
    }

    private boolean isSpriteVisible(int spriteX, int spriteY) {
        return ((spriteX >= 0 || spriteX < this.getSpriteWidth()) || (spriteY >= 0 || spriteY < this.getSpriteHeight()));
    }
}
