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

package de.gameplayjdk.jwfcimage;

import de.gameplayjdk.jwfcimage.data.RepositoryTileMap;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMap;
import de.gameplayjdk.jwfcimage.image.ImageDataInterface;
import de.gameplayjdk.jwfcimage.image.ImageLogic;
import de.gameplayjdk.jwfcimage.loop.Loop;
import de.gameplayjdk.jwfcimage.loop.LoopCallbackAdapter;
import de.gameplayjdk.jwfcimage.mvp.clean.UseCaseAbstract;
import de.gameplayjdk.jwfcimage.mvp.clean.UseCaseHandler;
import de.gameplayjdk.jwfcimage.usecase.*;
import de.gameplayjdk.jwfcimage.utility.Vector;
import de.gameplayjdk.jwfcimage.view.MenuData;

import java.io.File;

public class MainPresenter implements MainContractInterface.Presenter {

    private final MainContractInterface.View view;

    private final LoopCallbackAdapter callbackAdapter;

    private final Loop loop;

    private final UseCaseHandler useCaseHandler;

    private final UseCaseLoadTileMap useCaseLoadTileMap;
    private final UseCaseSaveTileMap useCaseSaveTileMap;
    private final UseCaseAttachAvailableExtension useCaseAttachAvailableExtension;
    private final UseCaseFetchMenuData useCaseFetchMenuData;
    private final UseCaseGenerateTileMap useCaseGenerateTileMap;

    private ImageLogic imageLogic;

    public MainPresenter(MainContractInterface.View view) {
        this.view = view;
        this.view.setPresenter(this);

        this.callbackAdapter = new LoopCallbackAdapter();

        this.loop = new Loop(this.callbackAdapter);

        this.imageLogic = null;

        this.useCaseHandler = UseCaseHandler.getInstance();

        this.useCaseLoadTileMap = UseCaseLoadTileMap.newInstance();
        this.useCaseSaveTileMap = UseCaseSaveTileMap.newInstance();
        this.useCaseAttachAvailableExtension = UseCaseAttachAvailableExtension.newInstance();
        this.useCaseFetchMenuData = UseCaseFetchMenuData.newInstance();
        this.useCaseGenerateTileMap = UseCaseGenerateTileMap.newInstance();
    }

    @Override
    public void start() {
        this.loop.setActive(true);
        this.loop.start();
    }

    @Override
    public void stop() {
        this.useCaseHandler.shutdown();

        this.loop.setActive(false);
        if (this.loop.isAlive()) {
            try {
                this.loop.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void setImageData(ImageDataInterface imageData) {
        this.imageLogic = new ImageLogic(imageData);

        {
            // TODO: Extract this into an use case.

            RepositoryTileMap repository = RepositoryTileMap.getInstance();
            EntityTileMap entity = repository.create();
            entity.setTileMap(this.imageLogic.getTileMap());
            if (repository.add(entity)) {
                repository.setEntity(entity);
            }
        }

        this.callbackAdapter.setCallback(this.imageLogic);
    }

    @Override
    public void loadFile(File file, int handlerId) {
        UseCaseLoadTileMap.RequestValue requestValue = new UseCaseLoadTileMap.RequestValue(file, handlerId);

        this.useCaseHandler.execute(this.useCaseLoadTileMap, requestValue, new UseCaseAbstract.UseCaseCallback<UseCaseLoadTileMap.ResponseValue, UseCaseLoadTileMap.ErrorResponseValue>() {
            @Override
            public void onSuccess(UseCaseLoadTileMap.ResponseValue response) {
                EntityTileMap entity = response.getEntity();

                MainPresenter.this.imageLogic.setTileMap(entity.getTileMap());

                MainPresenter.this.view.showStatusMessage("Loaded successfully.");
            }

            @Override
            public void onError(UseCaseLoadTileMap.ErrorResponseValue errorResponse) {
                UseCaseLoadTileMap.ErrorResponseValue.Type type = errorResponse.getType();

                MainPresenter.this.view.showStatusMessage("Error: " + type.name());
            }
        });
    }

    @Override
    public void saveFile(File file, int handlerId) {
        UseCaseSaveTileMap.RequestValue requestValue = new UseCaseSaveTileMap.RequestValue(file, handlerId);

        this.useCaseHandler.execute(this.useCaseSaveTileMap, requestValue, new UseCaseAbstract.UseCaseCallback<UseCaseSaveTileMap.ResponseValue, UseCaseSaveTileMap.ErrorResponseValue>() {
            @Override
            public void onSuccess(UseCaseSaveTileMap.ResponseValue response) {
                // TODO: This might not be necessary.
                EntityTileMap entity = response.getEntity();

                // TODO: This might not be necessary.
                MainPresenter.this.imageLogic.setTileMap(entity.getTileMap());

                MainPresenter.this.view.showStatusMessage("Saved successfully.");
            }

            @Override
            public void onError(UseCaseSaveTileMap.ErrorResponseValue errorResponse) {
                UseCaseSaveTileMap.ErrorResponseValue.Type type = errorResponse.getType();

                MainPresenter.this.view.showStatusMessage("Error: " + type.name());
            }
        });
    }

    @Override
    public void moveOffset(Vector vector) {
        this.imageLogic.moveOffset(vector);
    }

    @Override
    public void moveVector(Vector vector) {
        this.imageLogic.moveVector(vector);
    }

    @Override
    public void attachAvailableExtension() {
        this.useCaseHandler.execute(this.useCaseAttachAvailableExtension, new UseCaseAttachAvailableExtension.RequestValue(), new UseCaseAbstract.UseCaseCallback<UseCaseAttachAvailableExtension.ResponseValue, UseCaseAttachAvailableExtension.ErrorResponseValue>() {
            @Override
            public void onSuccess(UseCaseAttachAvailableExtension.ResponseValue response) {
                MainPresenter.this.view.showStatusMessage("Successfully attached extension.");

                MainPresenter.this.updateMenu();
            }

            @Override
            public void onError(UseCaseAttachAvailableExtension.ErrorResponseValue errorResponse) {
                MainPresenter.this.view.showStatusMessage("No extension could be attached!");
            }
        });
    }

    @Override
    public void updateMenu() {
        this.useCaseHandler.execute(this.useCaseFetchMenuData, new UseCaseFetchMenuData.RequestValue(), new UseCaseAbstract.UseCaseCallback<UseCaseFetchMenuData.ResponseValue, UseCaseFetchMenuData.ErrorResponseValue>() {
            @Override
            public void onSuccess(UseCaseFetchMenuData.ResponseValue response) {
                MenuData menuData = new MenuData();
                menuData.setListHandler(response.getListHandler());
                menuData.setListGenerator(response.getListGenerator());
                menuData.setListMap(response.getListMap());

                MainPresenter.this.view.updateMenu(menuData);

                MainPresenter.this.view.showStatusMessage("Successfully fetched menu data.");
            }

            @Override
            public void onError(UseCaseFetchMenuData.ErrorResponseValue errorResponse) {
                MainPresenter.this.view.showStatusMessage("Error!");
            }
        });
    }

    @Override
    public void generateMap(int generatorId) {
        UseCaseGenerateTileMap.RequestValue requestValue = new UseCaseGenerateTileMap.RequestValue(generatorId);

        this.useCaseHandler.execute(this.useCaseGenerateTileMap, requestValue, new UseCaseAbstract.UseCaseCallback<UseCaseGenerateTileMap.ResponseValue, UseCaseGenerateTileMap.ErrorResponseValue>() {
            @Override
            public void onSuccess(UseCaseGenerateTileMap.ResponseValue response) {
                EntityTileMap entity = response.getEntity();

                MainPresenter.this.imageLogic.setTileMap(entity.getTileMap());

                MainPresenter.this.updateMenu();

                MainPresenter.this.view.showStatusMessage("Successfully generated map.");
            }

            @Override
            public void onError(UseCaseGenerateTileMap.ErrorResponseValue errorResponse) {
                UseCaseGenerateTileMap.ErrorResponseValue.Type type = errorResponse.getType();

                MainPresenter.this.view.showStatusMessage("Error: " + type.name());
            }
        });
    }

    @Override
    public void switchMap(int mapId) {
        // TODO: Implement.

        System.out.println("Not supported yet.");
    }
}
