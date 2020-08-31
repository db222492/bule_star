package com.xinzeyijia.houselocks.model.http.net_request;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public   class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ExceptionHandle.ResponseException) {
            onError((ExceptionHandle.ResponseException) e);
        } else {
            onError(new ExceptionHandle.ResponseException(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {

    }

    public   void onError(ExceptionHandle.ResponseException exception){};

}