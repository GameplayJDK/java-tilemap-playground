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

package de.gameplayjdk.jwfcimage.extension.access;

import de.gameplayjdk.jwfcimage.data.RepositoryTileMapGenerator;
import de.gameplayjdk.jwfcimage.data.RepositoryTileMapHandler;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMapGenerator;
import de.gameplayjdk.jwfcimage.data.entity.EntityTileMapHandler;
import de.gameplayjdk.jwfcimage.data.handler.TileMapGeneratorInterface;
import de.gameplayjdk.jwfcimage.data.handler.TileMapHandlerInterface;
import de.gameplayjdk.jwfcimage.extension.ExtensionInterface;

import java.util.function.Supplier;

public final class Application {

    public static final String VERSION = "v1.0.0";

    public static Supplier<ApplicationExtensionManagerInterface> extensionManagerSupplier;

    private static Application instance;

    public static Application getInstance() {
        if (null == Application.extensionManagerSupplier) {
            Application.extensionManagerSupplier = ApplicationExtensionManager::new;
        }

        if (null == Application.instance) {
            Application.instance = new Application(RepositoryTileMapHandler.getInstance(), RepositoryTileMapGenerator.getInstance(), Application.extensionManagerSupplier.get());
        }

        return Application.instance;
    }

    private final RepositoryTileMapHandler repositoryHandler;
    private final RepositoryTileMapGenerator repositoryGenerator;

    private final ApplicationExtensionManagerInterface extensionManager;

    private boolean active;

    public Application(RepositoryTileMapHandler repositoryHandler, RepositoryTileMapGenerator repositoryGenerator, ApplicationExtensionManagerInterface extensionManager) {
        this.repositoryHandler = repositoryHandler;
        this.repositoryGenerator = repositoryGenerator;

        this.extensionManager = extensionManager;

        this.active = false;
    }

    public boolean registerTileMapHandler(String name, boolean supportLoad, boolean supportSave, TileMapHandlerInterface handler) {
        EntityTileMapHandler entity = this.repositoryHandler.create();
        entity.setName(name);
        entity.setSupportLoad(supportLoad);
        entity.setSupportSave(supportSave);
        entity.setHandler(handler);

        return this.repositoryHandler.add(entity);
    }

    public boolean registerTileMapGenerator(String name, TileMapGeneratorInterface generator) {
        EntityTileMapGenerator entity = this.repositoryGenerator.create();
        entity.setName(name);
        entity.setGenerator(generator);

        return this.repositoryGenerator.add(entity);
    }

    /**
     * Do not call this method in the extension! It will throw an exception.
     *
     * @throws IllegalStateException
     * @return
     */
    public boolean attachAvailableExtension() {
        if (!this.active) {
            this.active = true;

            boolean result = this.extensionManager.attachAvailableExtension(this);

            this.active = false;

            return true;
        }

        throw new IllegalStateException("Method attachAvailableExtension() is already active.");
    }

    public boolean attachAvailableExtension(ExtensionInterface extension) {
        this.extensionManager.attachAvailableExtension(this, extension);

        return true;
    }
}
