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
import de.gameplayjdk.jwfcimage.data.RepositoryTileMapHandler;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMap;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMapHandler;
import de.gameplayjdk.jwfcimage.data.handler.TileMapHandlerInterface;
import de.gameplayjdk.jwfcimage.engine.data.TileMapAbstract;
import de.gameplayjdk.jwfcimage.mvp.clean.UseCase;

import java.io.File;

public class UseCaseLoadTileMap extends UseCase<UseCaseLoadTileMap.RequestValue, UseCaseLoadTileMap.ResponseValue, UseCaseLoadTileMap.ErrorResponseValue> {

    public static UseCaseLoadTileMap newInstance() {
        return new UseCaseLoadTileMap(RepositoryTileMap.getInstance(), RepositoryTileMapHandler.getInstance());
    }

    private final RepositoryTileMap repository;
    private final RepositoryTileMapHandler repositoryHandler;

    public UseCaseLoadTileMap(RepositoryTileMap repository, RepositoryTileMapHandler repositoryHandler) {
        this.repository = repository;
        this.repositoryHandler = repositoryHandler;
    }

    @Override
    protected void executeUseCase(RequestValue requestValue) {
        File file = requestValue.getFile();

        if (null == file) {
            this.callOnErrorCallback(ErrorResponseValue.Type.FILE_NOT_SET);

            return;
        }

        TileMapHandlerInterface handler = this.getHandler(requestValue);

        if (null == handler) {
            this.callOnErrorCallback(ErrorResponseValue.Type.HANDLER_NOT_SET);

            return;
        }

        TileMapAbstract tileMap = handler.load(file);

        if (null == tileMap) {
            this.callOnErrorCallback(ErrorResponseValue.Type.HANDLER_LOAD_FAILED);

            return;
        }

        EntityTileMap entity = this.repository.create();
        entity.setTileMap(tileMap);

        if (this.repository.add(entity)) {
            this.repository.setEntity(entity);

            this.callOnSuccessCallback(entity);

            return;
        }

        this.callOnErrorCallback(ErrorResponseValue.Type.REPOSITORY_ADD_FAILED);
    }

    private TileMapHandlerInterface getHandler(RequestValue requestValue) {
        int handlerId = requestValue.getHandlerId();

        EntityTileMapHandler entity = this.repositoryHandler.getById(handlerId);

        if (null == entity) {
            return null;
        }

        return entity.getHandler();
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

    public static class RequestValue implements UseCase.RequestValueInterface {

        private final File file;

        private final int handlerId;

        public RequestValue(File file, int handlerId) {
            this.file = file;

            this.handlerId = handlerId;
        }

        public File getFile() {
            return this.file;
        }

        public int getHandlerId() {
            return this.handlerId;
        }
    }

    public static class ResponseValue implements UseCase.ResponseValueInterface {

        private final EntityTileMap entity;

        public ResponseValue(EntityTileMap entity) {
            this.entity = entity;
        }

        public EntityTileMap getEntity() {
            return this.entity;
        }
    }

    public static class ErrorResponseValue implements UseCase.ErrorResponseValueInterface {

        public static enum Type {
            FILE_NOT_SET,
            HANDLER_NOT_SET,
            HANDLER_LOAD_FAILED,
            REPOSITORY_ADD_FAILED,
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
