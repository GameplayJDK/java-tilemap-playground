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

package de.gameplayjdk.jwfcimage.view;

import de.gameplayjdk.jwfcimage.data.entity.EntityTileMap;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMapGenerator;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMapHandler;

import java.util.List;

public class MenuData {

    private List<EntityTileMapHandler> listHandler;
    private List<EntityTileMapGenerator> listGenerator;
    private List<EntityTileMap> listMap;
    private int mapId;

    public List<EntityTileMapHandler> getListHandler() {
        return this.listHandler;
    }

    public void setListHandler(List<EntityTileMapHandler> listHandler) {
        this.listHandler = listHandler;
    }

    public List<EntityTileMapGenerator> getListGenerator() {
        return this.listGenerator;
    }

    public void setListGenerator(List<EntityTileMapGenerator> listGenerator) {
        this.listGenerator = listGenerator;
    }

    public List<EntityTileMap> getListMap() {
        return this.listMap;
    }

    public void setListMap(List<EntityTileMap> listMap) {
        this.listMap = listMap;
    }

    public int getMapId() {
        return this.mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
