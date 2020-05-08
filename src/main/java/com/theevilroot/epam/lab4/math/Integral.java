package com.theevilroot.epam.lab4.math;

import com.theevilroot.epam.lab4.observable.MutableObservable;
import com.theevilroot.epam.lab4.observable.Observable;
import com.theevilroot.epam.lab4.observable.impl.ObservableValue;

import java.util.concurrent.*;

public class Integral {

    private Function function;
    private double dx;

    private MutableObservable<Double> result = new ObservableValue<>(0.0);

    private ExecutorService calculatorExecutor = new ThreadPoolExecutor(0,
            4,
            0,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    private CountDownLatch latch;

    public Integral(Function function, double dx) {
        this.function = function;
        this.dx = dx;
    }

    public void startCalculation() {
        int count = Math.toIntExact(Math.round(Math.ceil((function.getToX() - function.getFromX()) / dx)));
        latch = new CountDownLatch(count);

        for (double ax = function.getFromX(); ax < function.getToX(); ax += dx) {
            calculatorExecutor.submit(new Trapeze(ax, ax + dx, dx, this));
        }
    }

    public ExecutorService getCalculatorExecutor() {
        return calculatorExecutor;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public Function getFunction() {
        return function;
    }

    public double getDelta() {
        return dx;
    }

    public synchronized Observable<Double> getResult() {
        return result;
    }

    public synchronized void addResult(double val) {
        synchronized (this) {
            result.setValue(result.getValue() + val);
        }
    }
}
