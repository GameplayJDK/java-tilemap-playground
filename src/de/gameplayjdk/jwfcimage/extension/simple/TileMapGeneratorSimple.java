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

package de.gameplayjdk.jwfcimage.extension.simple;

import de.gameplayjdk.jwfcimage.data.handler.TileMapGeneratorInterface;
import de.gameplayjdk.jwfcimage.engine.data.Tile;
import de.gameplayjdk.jwfcimage.engine.data.TileAbstract;
import de.gameplayjdk.jwfcimage.engine.data.TileMap;
import de.gameplayjdk.jwfcimage.engine.data.TileMapAbstract;

import java.util.Random;

public class TileMapGeneratorSimple implements TileMapGeneratorInterface {

    // TODO: Implement properly.

    private final Random random;

    public TileMapGeneratorSimple() {
        this.random = new Random();
    }

    @Override
    public TileMapAbstract generate(TileMapAbstract tileMap) {
        TileAbstract[] map = tileMap.getMap();

        int index = this.random.nextInt(map.length);

        TileAbstract tile = map[index];

        if (null == tile) {
            return tileMap;
        }

        if (tile.getId() == Tile.empty.getId()) {
            map[index] = Tile.water;
        } else if (tile.getId() == Tile.water.getId()) {
            map[index] = Tile.ground;
        } else if (tile.getId() == Tile.ground.getId()) {
            map[index] = Tile.empty;
        }

        return tileMap;
    }
}
