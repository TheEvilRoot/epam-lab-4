package com.theevilroot.epam.lab4.observable;

import com.theevilroot.epam.lab4.observable.Observer;

public interface Observable<T> {

    void subscribe(Observer<T> observer);

    void unsubscribe(Observer<T> observer);

}
