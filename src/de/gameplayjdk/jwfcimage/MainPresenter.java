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

import de.gameplayjdk.jwfcimage.image.ImageDataInterface;
import de.gameplayjdk.jwfcimage.image.ImageLogic;
import de.gameplayjdk.jwfcimage.loop.Loop;
import de.gameplayjdk.jwfcimage.loop.LoopCallbackAdapter;
import de.gameplayjdk.jwfcimage.mvp.clean.UseCase;
import de.gameplayjdk.jwfcimage.mvp.clean.UseCaseHandler;
import de.gameplayjdk.jwfcimage.usecase.UseCaseAttachAvailableExtension;
import de.gameplayjdk.jwfcimage.usecase.UseCaseLoadTileMap;
import de.gameplayjdk.jwfcimage.utility.Vector;

import java.io.File;

public class MainPresenter implements MainContractInterface.Presenter {

    private final MainContractInterface.View view;

    private final LoopCallbackAdapter callbackAdapter;

    private final Loop loop;

    private final UseCaseHandler useCaseHandler;

    private final UseCaseLoadTileMap useCaseLoadTileMap;
    private final UseCaseAttachAvailableExtension useCaseAttachAvailableExtension;

    private ImageLogic imageLogic;

    public MainPresenter(MainContractInterface.View view) {
        this.view = view;
        this.view.setPresenter(this);

        this.callbackAdapter = new LoopCallbackAdapter();

        this.loop = new Loop(this.callbackAdapter);

        this.imageLogic = null;

        this.useCaseHandler = UseCaseHandler.getInstance();

        this.useCaseLoadTileMap = UseCaseLoadTileMap.newInstance();
        this.useCaseAttachAvailableExtension = UseCaseAttachAvailableExtension.newInstance();
    }

    @Override
    public void start() {
        this.loop.setActive(true);
        this.loop.start();

        this.useCaseHandler.execute(this.useCaseAttachAvailableExtension, new UseCaseAttachAvailableExtension.RequestValue(), new UseCase.UseCaseCallback<UseCaseAttachAvailableExtension.ResponseValue, UseCaseAttachAvailableExtension.ErrorResponseValue>() {
            @Override
            public void onSuccess(UseCaseAttachAvailableExtension.ResponseValue response) {
                // TODO: Set observable.
            }

            @Override
            public void onError(UseCaseAttachAvailableExtension.ErrorResponseValue errorResponse) {
                // TODO: Show error message.
            }
        });
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

        this.callbackAdapter.setCallback(imageLogic);
    }

    @Override
    public void loadFile(File file, int handlerId) {
        // TODO: Get tile map handler using id inside the use case.
        UseCaseLoadTileMap.RequestValue requestValue = new UseCaseLoadTileMap.RequestValue(file, handlerId);

        this.useCaseHandler.execute(this.useCaseLoadTileMap, requestValue, new UseCase.UseCaseCallback<UseCaseLoadTileMap.ResponseValue, UseCaseLoadTileMap.ErrorResponseValue>() {
            @Override
            public void onSuccess(UseCaseLoadTileMap.ResponseValue response) {
                System.out.println("Not yet supported.");
            }

            @Override
            public void onError(UseCaseLoadTileMap.ErrorResponseValue errorResponse) {
                System.out.println("Error loading file.");
            }
        });
    }

    @Override
    public void saveFile(File file, int handlerId) {
        // TODO: Implement.
    }

    @Override
    public void moveOffset(Vector vector) {
        this.imageLogic.moveOffset(vector);
    }

    @Override
    public void moveVector(Vector vector) {
        this.imageLogic.moveVector(vector);
    }
}
