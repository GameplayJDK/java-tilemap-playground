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
import de.gameplayjdk.jwfcimage.mvp.clean.UseCase;

public class UseCaseAttachAvailableExtension extends UseCase<UseCaseAttachAvailableExtension.RequestValue, UseCaseAttachAvailableExtension.ResponseValue, UseCaseAttachAvailableExtension.ErrorResponseValue> {

    private final Application application;

    public static UseCaseAttachAvailableExtension newInstance() {
        return new UseCaseAttachAvailableExtension(Application.getInstance());
    }

    public UseCaseAttachAvailableExtension(Application application) {
        this.application = application;
    }

    @Override
    protected void executeUseCase(RequestValue requestValue) {
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

    public static class RequestValue implements UseCase.RequestValueInterface {
    }

    public static class ResponseValue implements UseCase.ResponseValueInterface {
    }

    public static class ErrorResponseValue implements UseCase.ErrorResponseValueInterface {
    }
}
