package com.theevilroot.epam.lab4.math;

import com.theevilroot.epam.lab4.math.Function;
import com.theevilroot.epam.lab4.math.FunctionEvaluator;

public enum FunctionType {

    SIN_X(Math::sin, "Sin(x)"),
    SIN_SQ_X(x -> Math.pow(Math.sin(x), 2), "Squared sin(x)"),
    COS_X(Math::cos, "Cos(x)"),
    SQRT_X(Math::sqrt, "Sqrt(x)");

    public final FunctionEvaluator evaluator;
    public final String label;

    FunctionType(FunctionEvaluator evaluator, String label) {
        this.evaluator = evaluator;
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
