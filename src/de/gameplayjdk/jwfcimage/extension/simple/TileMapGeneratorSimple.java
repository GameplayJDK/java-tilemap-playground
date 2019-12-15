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
import de.gameplayjdk.jwfcimage.engine.data.TileAbstract;
import de.gameplayjdk.jwfcimage.engine.data.TileMapAbstract;
import de.gameplayjdk.jwfcimage.extension.simple.data.TileMapSimple;
import de.gameplayjdk.jwfcimage.extension.simple.data.TileSimple;

import java.util.Random;

public class TileMapGeneratorSimple implements TileMapGeneratorInterface {

    private final Random random;

    private final TileAbstract[] tileArray;

    public TileMapGeneratorSimple() {
        this.random = new Random();

        this.tileArray = new TileSimple[] {
                TileSimple.empty,
                TileSimple.water,
                TileSimple.ground,
        };
    }

    @Override
    public TileMapAbstract generate(TileMapAbstract tileMap) {
        tileMap = this.getTileMap(tileMap);
        TileAbstract[] map = tileMap.getMap();

        for (int i = 0; i < map.length; i++) {
            TileAbstract tile = map[i];

            // This check is not needed, when it is done inside get tile.
            //if (null == tile) {
            //    continue;
            //}

            map[i] = this.getTile(tile);
        }

        return tileMap;
    }

    private TileMapAbstract getTileMap(TileMapAbstract tileMap) {
        TileAbstract[] map = tileMap.getMap();

        TileMapAbstract tileMapNew = new TileMapSimple(tileMap.getWidth(), tileMap.getHeight(), tileMap.getTileSize());
        TileAbstract[] mapNew = tileMapNew.getMap();

        System.arraycopy(map, 0, mapNew, 0, mapNew.length);

        return tileMapNew;
    }

    private TileAbstract getTile(TileAbstract tile) {
        if (null != tile) {
            if (tile.getId() == TileSimple.empty.getId()) {
                return TileSimple.water;
            } else if (tile.getId() == TileSimple.water.getId()) {
                return TileSimple.ground;
            } else if (tile.getId() == TileSimple.ground.getId()) {
                return TileSimple.empty;
            }
        }

        return this.getTileRandom();
    }

    private TileAbstract getTileRandom() {
        int index = this.random.nextInt(this.tileArray.length);

        return this.tileArray[index];
    }
}
