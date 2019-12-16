package de.gameplayjdk.jwfcimage.usecase;

import de.gameplayjdk.jwfcimage.data.RepositoryTileMap;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMap;
import de.gameplayjdk.jwfcimage.engine.data.TileMapAbstract;
import de.gameplayjdk.jwfcimage.extension.simple.TileMapGeneratorSimple;
import de.gameplayjdk.jwfcimage.mvp.clean.UseCaseAbstract;

public class UseCaseInitializeTileMap extends UseCaseAbstract<UseCaseInitializeTileMap.RequestValue, UseCaseInitializeTileMap.ResponseValue, UseCaseInitializeTileMap.ErrorResponseValue> {

    public static UseCaseInitializeTileMap newInstance() {
        return new UseCaseInitializeTileMap(RepositoryTileMap.getInstance(), TileMapGeneratorSimple.getInstance());
    }

    private final RepositoryTileMap repository;

    private final TileMapGeneratorSimple tileMapGeneratorSimple;

    public UseCaseInitializeTileMap(RepositoryTileMap repository, TileMapGeneratorSimple tileMapGeneratorSimple) {
        this.repository = repository;

        this.tileMapGeneratorSimple = tileMapGeneratorSimple;
    }

    @Override
    protected void executeUseCase(RequestValue requestValue) {
        EntityTileMap entity = this.repository.create();

        TileMapAbstract tileMap = this.tileMapGeneratorSimple
                .getTileMapDefault();

        entity.setTileMap(tileMap);

        if (this.repository.add(entity)) {
            this.repository.setEntity(entity);

            this.callOnSuccessCallback(entity);

            return;
        }

        this.callOnErrorCallback(ErrorResponseValue.Type.REPOSITORY_ADD_FAILED);
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
    }

    public static class ResponseValue implements UseCaseAbstract.ResponseValueInterface {

        private EntityTileMap entity;

        public ResponseValue(EntityTileMap entity) {
            this.entity = entity;
        }

        public EntityTileMap getEntity() {
            return this.entity;
        }

        public void setEntity(EntityTileMap entity) {
            this.entity = entity;
        }
    }

    public static class ErrorResponseValue implements UseCaseAbstract.ErrorResponseValueInterface {

        public enum Type {
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
