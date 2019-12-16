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

import de.gameplayjdk.jwfcimage.extension.access.Application;
import de.gameplayjdk.jwfcimage.extension.simple.ExtensionSimple;
import de.gameplayjdk.jwfcimage.mvp.clean.UseCaseAbstract;

public class UseCaseAttachAvailableExtension extends UseCaseAbstract<UseCaseAttachAvailableExtension.RequestValue, UseCaseAttachAvailableExtension.ResponseValue, UseCaseAttachAvailableExtension.ErrorResponseValue> {

    public static UseCaseAttachAvailableExtension newInstance() {
        return new UseCaseAttachAvailableExtension(Application.getInstance());
    }

    private final Application application;

    private final ExtensionSimple defaultExtension;

    public UseCaseAttachAvailableExtension(Application application) {
        this.application = application;

        this.defaultExtension = new ExtensionSimple();
    }

    @Override
    protected void executeUseCase(RequestValue requestValue) {
        this.application.attachAvailableExtension(this.defaultExtension);

        if (this.application.attachAvailableExtension()) {
            this.callOnSuccessCallback();

            return;
        }

        this.callOnErrorCallback();
    }

    private void callOnSuccessCallback() {
        ResponseValue responseValue = new ResponseValue();

        this.getUseCaseCallback()
                .onSuccess(responseValue);
    }

    private void callOnErrorCallback() {
        ErrorResponseValue errorResponseValue = new ErrorResponseValue();

        this.getUseCaseCallback()
                .onError(errorResponseValue);
    }

    public static class RequestValue implements UseCaseAbstract.RequestValueInterface {
    }

    public static class ResponseValue implements UseCaseAbstract.ResponseValueInterface {
    }

    public static class ErrorResponseValue implements UseCaseAbstract.ErrorResponseValueInterface {
    }
}
