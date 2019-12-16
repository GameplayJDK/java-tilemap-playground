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
import de.gameplayjdk.jwfcimage.data.RepositoryTileMapGenerator;
import de.gameplayjdk.jwfcimage.data.RepositoryTileMapHandler;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMap;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMapGenerator;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMapHandler;
import de.gameplayjdk.jwfcimage.mvp.clean.UseCaseAbstract;

import java.util.List;

public class UseCaseFetchMenuData extends UseCaseAbstract<UseCaseFetchMenuData.RequestValue, UseCaseFetchMenuData.ResponseValue, UseCaseFetchMenuData.ErrorResponseValue> {

    public static UseCaseFetchMenuData newInstance() {
        return new UseCaseFetchMenuData(RepositoryTileMapHandler.getInstance(), RepositoryTileMapGenerator.getInstance(), RepositoryTileMap.getInstance());
    }

    private final RepositoryTileMapHandler repositoryHandler;
    private final RepositoryTileMapGenerator repositoryGenerator;
    private final RepositoryTileMap repository;

    public UseCaseFetchMenuData(RepositoryTileMapHandler repositoryHandler, RepositoryTileMapGenerator repositoryGenerator, RepositoryTileMap repository) {
        this.repositoryHandler = repositoryHandler;
        this.repositoryGenerator = repositoryGenerator;
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValue requestValue) {
        List<EntityTileMapHandler> listHandler = this.repositoryHandler.getAll();
        List<EntityTileMapGenerator> listGenerator = this.repositoryGenerator.getAll();
        List<EntityTileMap> listTileMap = this.repository.getAll();
        EntityTileMap entity = this.repository.getEntity();

        ResponseValue responseValue = new ResponseValue(listHandler, listGenerator, listTileMap, entity);

        this.getUseCaseCallback()
                .onSuccess(responseValue);
    }

    public static class RequestValue implements UseCaseAbstract.RequestValueInterface {
    }

    public static class ResponseValue implements UseCaseAbstract.ResponseValueInterface {

        private final List<EntityTileMapHandler> listHandler;
        private final List<EntityTileMapGenerator> listGenerator;
        private final List<EntityTileMap> listMap;
        private final EntityTileMap entity;

        public ResponseValue(List<EntityTileMapHandler> listHandler, List<EntityTileMapGenerator> listGenerator, List<EntityTileMap> listMap, EntityTileMap entity) {
            this.listHandler = listHandler;
            this.listGenerator = listGenerator;
            this.listMap = listMap;
            this.entity = entity;
        }

        public List<EntityTileMapHandler> getListHandler() {
            return this.listHandler;
        }

        public List<EntityTileMapGenerator> getListGenerator() {
            return this.listGenerator;
        }

        public List<EntityTileMap> getListMap() {
            return this.listMap;
        }

        public EntityTileMap getEntity() {
            return this.entity;
        }
    }

    public static class ErrorResponseValue implements UseCaseAbstract.ErrorResponseValueInterface {
    }
}
