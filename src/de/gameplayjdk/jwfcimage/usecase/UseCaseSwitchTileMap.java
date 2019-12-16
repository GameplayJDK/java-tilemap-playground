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

package de.gameplayjdk.jwfcimage.usecase;

import de.gameplayjdk.jwfcimage.data.RepositoryTileMap;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMap;
import de.gameplayjdk.jwfcimage.engine.data.TileMapAbstract;
import de.gameplayjdk.jwfcimage.mvp.clean.UseCaseAbstract;

public class UseCaseSwitchTileMap extends UseCaseAbstract<UseCaseSwitchTileMap.RequestValue, UseCaseSwitchTileMap.ResponseValue, UseCaseSwitchTileMap.ErrorResponseValue> {

    public static UseCaseSwitchTileMap newInstance() {
        return new UseCaseSwitchTileMap(RepositoryTileMap.getInstance());
    }

    private RepositoryTileMap repository;

    public UseCaseSwitchTileMap(RepositoryTileMap repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValue requestValue) {
        int mapId = requestValue.getMapId();

        EntityTileMap entity = this.repository.getById(mapId);

        if (null == entity) {
            this.callOnErrorCallback(ErrorResponseValue.Type.ENTITY_NOT_SET);

            return;
        }

        TileMapAbstract tileMap = entity.getTileMap();

        if (null == tileMap) {
            this.callOnErrorCallback(ErrorResponseValue.Type.TILEMAP_NOT_SET);

            return;
        }

        this.repository.setEntity(entity);

        this.callOnSuccessCallback(entity);
    }

    private void callOnSuccessCallback(EntityTileMap entity) {
        ResponseValue responseValue = new ResponseValue(entity);

        this.getUseCaseCallback()
                .onSuccess(responseValue);
    }

    private void callOnErrorCallback(ErrorResponseValue.Type type) {
        ErrorResponseValue errorResponseValue = new ErrorResponseValue(type);

        this.getUseCaseCallback()
                .onError(errorResponseValue);
    }

    public static class RequestValue implements UseCaseAbstract.RequestValueInterface {

        private final int mapId;

        public RequestValue(int mapId) {
            this.mapId = mapId;
        }

        public int getMapId() {
            return this.mapId;
        }
    }

    public static class ResponseValue implements UseCaseAbstract.ResponseValueInterface {

        private final EntityTileMap entity;

        public ResponseValue(EntityTileMap entity) {
            this.entity = entity;
        }

        public EntityTileMap getEntity() {
            return this.entity;
        }
    }

    public static class ErrorResponseValue implements UseCaseAbstract.ErrorResponseValueInterface {

        public static enum Type {
            ENTITY_NOT_SET,
            TILEMAP_NOT_SET,
        }

        private final Type type;

        public ErrorResponseValue(Type type) {
            this.type = type;
        }

        public Type getType() {
            return this.type;
        }
    }
}
