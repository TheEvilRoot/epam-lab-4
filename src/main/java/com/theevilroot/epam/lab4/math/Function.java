package com.theevilroot.epam.lab4.math;

public class Function {

    private double fromX;
    private double toX;

    private FunctionEvaluator evaluator;

    public Function(double fromX, double toX, FunctionEvaluator evaluator) {
        this.fromX = fromX;
        this.toX = toX;
        this.evaluator = evaluator;
    }

    public double getFromX() {
        return fromX;
    }

    public double getToX() {
        return toX;
    }

    public FunctionEvaluator getEvaluator() {
        return evaluator;
    }
}
