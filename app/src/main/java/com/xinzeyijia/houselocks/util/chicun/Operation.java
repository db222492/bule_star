package com.xinzeyijia.houselocks.util.chicun;


public class Operation {

    //除法
    public static final Double division(double a, double b) {
        double div =0;
        if (b != 0) {
            div = a / b;
        } else {
            div = 0;
        }
        return div;
    }



}
