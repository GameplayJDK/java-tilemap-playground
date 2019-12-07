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

package de.gameplayjdk.jwfcimage.data.entity;

import de.gameplayjdk.jwfcimage.data.handler.TileMapHandlerInterface;

public class EntityTileMapHandler extends EntityAbstract {

    private String name;

    private boolean supportLoad;

    private boolean supportSave;

    private TileMapHandlerInterface handler;

    public EntityTileMapHandler(int id) {
        super(id);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSupportLoad() {
        return this.supportLoad;
    }

    public void setSupportLoad(boolean supportLoad) {
        this.supportLoad = supportLoad;
    }

    public boolean isSupportSave() {
        return this.supportSave;
    }

    public void setSupportSave(boolean supportSave) {
        this.supportSave = supportSave;
    }

    public TileMapHandlerInterface getHandler() {
        return this.handler;
    }

    public void setHandler(TileMapHandlerInterface handler) {
        this.handler = handler;
    }
}
