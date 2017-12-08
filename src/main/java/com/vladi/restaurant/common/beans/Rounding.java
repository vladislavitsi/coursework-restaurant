package com.vladi.restaurant.common.beans;

public enum Rounding {
    CEIL {
        @Override
        protected double computeRoundedValue(double scaledValue) {
            return Math.ceil(scaledValue);
        }
    },
    FLOOR {
        @Override
        protected double computeRoundedValue(double scaledValue) {
            return Math.floor(scaledValue);
        }
    },
    MATH_ROUND {
        @Override
        protected double computeRoundedValue(double scaledValue) {
            return Math.round(scaledValue);
        }
    };
    protected abstract double computeRoundedValue(final double scaledValue);

    public int round(final double valueToRound, final int d){
        int scale = (int) Math.pow(10, d);
        double scaledValue = (valueToRound) / scale;
        double rounded = computeRoundedValue(scaledValue);
        return ((int) rounded) * scale;
    }
}