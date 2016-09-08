package com.worldline.domain.executor;

import com.worldline.domain.interactor.Interactor;

import java.util.concurrent.Executor;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous execution, but every implementation
 * will execute the TODO modify link {@link Interactor} out of the UI thread.
 */
public interface ThreadExecutor extends Executor {
}