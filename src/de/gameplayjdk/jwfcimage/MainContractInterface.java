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
import de.gameplayjdk.jwfcimage.mvp.PresenterInterface;
import de.gameplayjdk.jwfcimage.mvp.ViewInterface;
import de.gameplayjdk.jwfcimage.utility.Vector;

import java.io.File;

public interface MainContractInterface {

    public static interface View extends ViewInterface<MainContractInterface.Presenter> {

        @Override
        public void setPresenter(Presenter presenter);

        public void openView();

        public void closeView();

        public void showLoadFileDialog(int handlerId);

        public void showSaveFileDialog(int handlerId);

        public void showAboutDialog();

        public void showMessage(String message);

        public void moveOffset(Vector vector);

        public void moveVector(Vector vector);
    }

    public static interface Presenter extends PresenterInterface {

        @Override
        public void start();

        public void stop();

        public void setImageData(ImageDataInterface imageData);

        public void loadFile(File file, int handlerId);

        public void saveFile(File file, int handlerId);

        public void moveOffset(Vector vector);

        public void moveVector(Vector vector);
    }
}
