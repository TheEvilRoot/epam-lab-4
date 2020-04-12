package com.theevilroot.epam.lab4.math;

import java.util.Random;

public class Trapeze implements Runnable{

    private Integral integral;
    private double fromX;
    private double toX;
    private double h;

    public Trapeze(double fromX, double toX, double h, Integral integral) {
        this.fromX = fromX;
        this.toX = toX;
        this.h = h;
        this.integral = integral;
    }

    public Integral getIntegral() {
        return integral;
    }

    public double getFrom() {
        return fromX;
    }

    public double getTo() {
        return toX;
    }

    public double calculate() {
        double ay = integral.getFunction().getEvaluator().evaluate(fromX);
        double by = integral.getFunction().getEvaluator().evaluate(toX);
        return h * ((ay + by) / 2);
    }

    @Override
    public void run() {
        double result = 0;
        try {
            result = calculate();
            Thread.sleep(new Random().nextInt(400) + 100);
        } catch (InterruptedException ignored) {  }
        integral.getLatch().countDown();
        integral.addResult(result);
    }

}
