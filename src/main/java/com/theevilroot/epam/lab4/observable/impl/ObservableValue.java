package com.theevilroot.epam.lab4.observable.impl;

import com.theevilroot.epam.lab4.observable.MutableObservable;
import com.theevilroot.epam.lab4.observable.Observer;

import java.util.ArrayList;

public class ObservableValue<T> implements MutableObservable<T> {

    private ArrayList<Observer<T>> observers = new ArrayList<>();
    private T value;

    public ObservableValue(T initialValue) {
        setValue(initialValue);
    }

    public ObservableValue() {
        setValue(null);
    }

    @Override
    public void subscribe(Observer<T> observer) {
        if (observers.contains(observer))
            return;
        observers.add(observer);
        observer.notify(this.value);
    }

    @Override
    public void unsubscribe(Observer<T> observer) {
        observers.remove(observer);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
        notifyObservers();
    }

    private void notifyObservers() {
        observers.forEach(o -> o.notify(this.value));
    }
}
