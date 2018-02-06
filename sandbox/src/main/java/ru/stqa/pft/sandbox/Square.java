package ru.stqa.pft.sandbox;

/**
 * Created by a.a.kornilov on 2/6/2018.
 */
public class Square {
    public double l;

    public Square(double l){
        this.l = l;
    }

    public  double area(){
        return  this.l * this.l;
    }
}
