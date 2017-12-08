package com.vladi.restaurant.common.beans;

public class USD implements Comparable<USD> {

    private final int value;

    public USD(int value) {
        this.value = value;
    }

    public USD(double value){
        this.value = (int) (value*100);
    }

    public USD(int rubles, int coins){
        this(rubles*100 + coins);
    }

    public USD(USD usd){
        this(usd.value);
    }

    public int getRubs(){
        return value/100;
    }

    public int getCoins(){
        return value%100;
    }

    public USD add(USD usd){
        return new USD(value+usd.value);
    }

    public USD sub(USD usd){
        return new USD(value-usd.value);
    }

    public USD mul(int k) {
        return new USD(value*k);
    }

    public USD mul(double k, Rounding rounding, int d){
        return round(value * k, rounding, d);
    }

    public USD mul(double k, Rounding rounding){
        return mul(k, rounding, 0);
    }

    public USD mul(double k){
        return mul(k, Rounding.MATH_ROUND, 0);
    }

    public USD round(Rounding rounding, int d){
        return round(value, rounding, d);
    }

    /**
     * Default rounding
     * @return result of rounding.
     */
    public USD round(){
        return round(value, Rounding.MATH_ROUND, 0);
    }

    private USD round(final double valueToRound, final Rounding rounding, final int d){
        return new USD(rounding.round(valueToRound, d));
    }

    public USD applyPercentDiscount(double discountPercent){
        return this.mul(1-(discountPercent/100));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof USD)) return false;
        USD byn = (USD) o;
        return value == byn.value;
    }

    @Override
    public int hashCode() {
        return 31*value;
    }

    @Override
    public String toString() {
        return value/100 + "." + (value%100)/10 + value%10;
    }

    @Override
    public int compareTo(USD o) {
        return value-o.value;
    }

    public int compareDownTo(USD o){
        return o.value-value;
    }
}