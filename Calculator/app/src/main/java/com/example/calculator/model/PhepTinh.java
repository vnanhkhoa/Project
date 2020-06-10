package com.example.calculator.model;

public class PhepTinh {
    private Float a;
    private Float b;

    public PhepTinh() {
    }

    public PhepTinh(Float a, Float b) {
        this.a = a;
        this.b = b;
    }

    public Float getA() {
        return a;
    }

    public void setA(Float a) {
        this.a = a;
    }

    public Float getB() {
        return b;
    }

    public void setB(Float b) {
        this.b = b;
    }

    public Float Cong(){
        return this.a+this.b;
    }
    public Float Tru(){
        return this.a-this.b;
    }
    public Float Nhan(){
        return this.a*this.b;
    }
    public Float Chia(){
        return this.a/this.b;
    }
}
