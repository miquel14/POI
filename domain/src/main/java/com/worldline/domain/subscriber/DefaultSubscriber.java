package com.worldline.domain.subscriber;

/**
 * Default subscriber base class to be used whenever you want default error handling.
 */
public class DefaultSubscriber <T> extends rx.Subscriber<T> {
    @Override
    public void onCompleted() {
        this.unsubscribe();
    }

    @Override
    public void onError(Throwable e) {
        this.unsubscribe();
    }

    @Override
    public void onNext(T t) {

    }
}