package com.theevilroot.epam.lab4.observable;

public interface Observer<T> {

    void notify(T neValue);

}
