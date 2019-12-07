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

package de.gameplayjdk.jwfcimage.mvp.clean;

import java.util.function.Supplier;

public class UseCaseHandler {

    public static Supplier<UseCaseSchedulerInterface> useCaseSchedulerSupplier;

    private static UseCaseHandler instance;

    public static UseCaseHandler getInstance() {
        if (null == UseCaseHandler.useCaseSchedulerSupplier) {
            UseCaseHandler.useCaseSchedulerSupplier = UseCaseSchedulerThreadPool::new;
        }

        if (null == UseCaseHandler.instance) {
            UseCaseSchedulerInterface useCaseScheduler = UseCaseHandler.useCaseSchedulerSupplier.get();

            UseCaseHandler.instance = new UseCaseHandler(useCaseScheduler);
        }

        return UseCaseHandler.instance;
    }

    private final UseCaseSchedulerInterface useCaseScheduler;

    public UseCaseHandler(UseCaseSchedulerInterface useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }

    public <P extends UseCase.RequestValueInterface, Q extends UseCase.ResponseValueInterface, R extends UseCase.ErrorResponseValueInterface> void execute(final UseCase<P, Q, R> useCase, P requestValue, UseCase.UseCaseCallback<Q, R> useCaseCallback) {
        useCase.setRequestValue(requestValue);

        UiCallbackWrapper<Q, R> uiCallbackWrapper = new UiCallbackWrapper<Q, R>(useCaseCallback, this);
        useCase.setUseCaseCallback(uiCallbackWrapper);

        this.useCaseScheduler.execute(useCase::execute);
    }

    private <V extends UseCase.ResponseValueInterface, W extends UseCase.ErrorResponseValueInterface> void notifyResponse(final V responseValue, final UseCase.UseCaseCallback<V, W> useCaseCallback) {
        this.useCaseScheduler.notifyResponse(responseValue, useCaseCallback);
    }

    private <V extends UseCase.ResponseValueInterface, W extends UseCase.ErrorResponseValueInterface> void notifyError(final W errorResponseValue, final UseCase.UseCaseCallback<V, W> useCaseCallback) {
        this.useCaseScheduler.notifyError(errorResponseValue, useCaseCallback);
    }

    public void shutdown() {
        this.useCaseScheduler.shutdown();
    }

    private static class UiCallbackWrapper<V extends UseCase.ResponseValueInterface, W extends UseCase.ErrorResponseValueInterface> implements UseCase.UseCaseCallback<V, W> {

        private final UseCase.UseCaseCallback<V, W> useCaseCallback;

        private final UseCaseHandler mUseCaseHandler;

        public UiCallbackWrapper(UseCase.UseCaseCallback<V, W> useCaseCallback, UseCaseHandler useCaseHandler) {
            this.useCaseCallback = useCaseCallback;

            this.mUseCaseHandler = useCaseHandler;
        }

        @Override
        public void onSuccess(V response) {
            this.mUseCaseHandler.notifyResponse(response, this.useCaseCallback);
        }

        @Override
        public void onError(W errorResponse) {
            this.mUseCaseHandler.notifyError(errorResponse, this.useCaseCallback);
        }
    }
}
