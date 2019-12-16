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

package de.gameplayjdk.jwfcimage.data;

import de.gameplayjdk.jwfcimage.data.entity.EntityTileMap;

public class RepositoryTileMap extends RepositoryAbstract<EntityTileMap> {

    public static final int LIMIT = 16;

    private static RepositoryTileMap instance;

    public static RepositoryTileMap getInstance() {
        if (null == RepositoryTileMap.instance) {
            RepositoryTileMap.instance = new RepositoryTileMap();
        }

        return RepositoryTileMap.instance;
    }

    private EntityTileMap entity;

    public RepositoryTileMap() {
        super();

        this.entity = null;
    }

    @Override
    public EntityTileMap create() {
        return new EntityTileMap(this.getNewId());
    }

    @Override
    public boolean add(EntityTileMap entity) {
        while (!(this.entityList.size() < RepositoryTileMap.LIMIT)) {
            this.entityList.remove(0);
        }

        return super.add(entity);
    }

    public EntityTileMap getEntity() {
        return this.entity;
    }

    public void setEntity(EntityTileMap entity) {
        this.entity = entity;
    }
}
