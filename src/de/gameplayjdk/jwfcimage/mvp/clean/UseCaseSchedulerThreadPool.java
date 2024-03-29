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

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseCaseSchedulerThreadPool implements UseCaseSchedulerInterface {

    public static final int POOL_SIZE = 2;
    public static final int MAX_POOL_SIZE = 4;
    public static final int TIMEOUT = 30;

    private final Handler handler;

    private ThreadPoolExecutor threadPoolExecutor;

    public UseCaseSchedulerThreadPool() {
        this.handler = new Handler();

        this.threadPoolExecutor = new ThreadPoolExecutor(
                UseCaseSchedulerThreadPool.POOL_SIZE,
                UseCaseSchedulerThreadPool.MAX_POOL_SIZE,
                UseCaseSchedulerThreadPool.TIMEOUT,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(UseCaseSchedulerThreadPool.POOL_SIZE),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
    }

    @Override
    public void execute(Runnable runnable) {
        this.threadPoolExecutor.execute(runnable);
    }

    @Override
    public <V extends UseCaseAbstract.ResponseValueInterface, W extends UseCaseAbstract.ErrorResponseValueInterface> void notifyResponse(final V responseValue, final UseCaseAbstract.UseCaseCallback<V, W> useCaseCallback) {
        this.handler.post(() -> useCaseCallback.onSuccess(responseValue));
    }

    @Override
    public <V extends UseCaseAbstract.ResponseValueInterface, W extends UseCaseAbstract.ErrorResponseValueInterface> void notifyError(final W errorResponseValue, final UseCaseAbstract.UseCaseCallback<V, W> useCaseCallback) {
        this.handler.post(() -> useCaseCallback.onError(errorResponseValue));
    }

    @Override
    public void shutdown() {
        this.threadPoolExecutor.shutdownNow();
    }
}
