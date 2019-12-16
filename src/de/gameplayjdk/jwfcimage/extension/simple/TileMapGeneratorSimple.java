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
import de.gameplayjdk.jwfcimage.extension.simple.data.TileCacheSimple;
import de.gameplayjdk.jwfcimage.extension.simple.data.TileMapSimple;

import java.util.Arrays;
import java.util.Random;

public class TileMapGeneratorSimple implements TileMapGeneratorInterface {

    private static TileMapGeneratorSimple instance;

    public static TileMapGeneratorSimple getInstance() {
        if (null == TileMapGeneratorSimple.instance) {
            TileMapGeneratorSimple.instance = new TileMapGeneratorSimple();
        }

        return TileMapGeneratorSimple.instance;
    }

    private final Random random;
    private final int[] colorArray;

    private final TileCacheSimple tileMapCache;

    private TileMapGeneratorSimple() {
        this.random = new Random();
        this.colorArray = new int[]{
                0xFFFFFF,
                0xFFFF00,
                0xFF00FF,
                0xFF0000,
                0x00FFFF,
                0x00FF00,
                0x0000FF,
                0x000000,
        };

        this.tileMapCache = TileCacheSimple.getInstance();
    }

    @Override
    public TileMapAbstract generate(TileMapAbstract tileMap) {
        tileMap = this.getTileMap(tileMap);
        TileAbstract[] map = tileMap.getMap();

        for (int i = 0; i < map.length; i++) {
            map[i] = this.getTileRandom();
        }

        return tileMap;
    }

    private TileMapAbstract getTileMap(TileMapAbstract tileMap) {
        if (null == tileMap) {
            return this.getTileMapDefault();
        }

        TileAbstract[] map = tileMap.getMap();

        TileMapAbstract tileMapNew = new TileMapSimple(tileMap.getWidth(), tileMap.getHeight(), tileMap.getTileSize());
        TileAbstract[] mapNew = tileMapNew.getMap();

        System.arraycopy(map, 0, mapNew, 0, mapNew.length);

        return tileMapNew;
    }

    public TileMapAbstract getTileMapDefault() {
        TileMapAbstract tileMap = new TileMapSimple(16, 16, TileMapSimple.TILE_SIZE);
        Arrays.fill(tileMap.getMap(), this.tileMapCache.fetch(0x000000));

        return tileMap;
    }

    private TileAbstract getTileRandom() {
        int index = this.random.nextInt(this.colorArray.length);
        int color = this.colorArray[index];

        return this.tileMapCache.fetch(color);
    }

    public String getName() {
        return "Simple (built-in)";
    }
}
