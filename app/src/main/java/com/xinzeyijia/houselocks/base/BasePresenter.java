package com.xinzeyijia.houselocks.base;


import io.reactivex.disposables.CompositeDisposable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BasePresenter<V> {
    protected CompositeDisposable mc = new CompositeDisposable();
    protected V v;

    protected void attachV(V v) {
        this.v = v;
        this.onAttached();
    }

    public abstract void onAttached();

    public void onDetached() {
        mc.dispose();
        mc = null;
    }

}
