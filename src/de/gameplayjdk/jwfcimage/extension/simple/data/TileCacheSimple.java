package de.gameplayjdk.jwfcimage.extension.simple.data;

import de.gameplayjdk.jwfcimage.engine.Sprite;
import de.gameplayjdk.jwfcimage.engine.SpriteColor;
import de.gameplayjdk.jwfcimage.engine.data.TileAbstract;

import java.util.HashMap;
import java.util.Map;

public class TileCacheSimple {

    private static TileCacheSimple instance;

    public static TileCacheSimple getInstance() {
        if (null == TileCacheSimple.instance) {
            TileCacheSimple.instance = new TileCacheSimple();
        }

        return TileCacheSimple.instance;
    }

    private final Map<Integer, TileSimple> cacheMap;

    private TileCacheSimple() {
        this.cacheMap = new HashMap<Integer, TileSimple>();
    }

    public TileAbstract fetch(int color) {
        if (!this.cacheMap.containsKey(color)) {
            Sprite sprite = new SpriteColor(TileMapSimple.TILE_SIZE, color);

            this.cacheMap.put(color, new TileSimple(color, sprite));
        }

        return this.cacheMap.get(color);
    }
}
