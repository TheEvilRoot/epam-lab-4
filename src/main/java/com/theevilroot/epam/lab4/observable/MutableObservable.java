package com.theevilroot.epam.lab4.observable;

import com.theevilroot.epam.lab4.observable.Observable;

public interface MutableObservable<T> extends Observable<T> {

    void setValue(T value);

    T getValue();

}
