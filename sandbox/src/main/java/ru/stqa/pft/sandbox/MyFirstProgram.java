package ru.stqa.pft.sandbox;

/**
 * Created by a.a.kornilov on 2/2/2018.
 */
public class MyFirstProgram {
    public static void main(String[] args) {

        hello("Alex");
        double l = 5;
        double m = 3;
        System.out.println(area(l));
        System.out.println(area(l,m));
    }
    public static void hello(String s){

        System.out.println("Hello " + s + " !");
    }

    public static double area (double len){
        double s = len * len;
//        System.out.println(s);
        return s;
    }

    public static double area (double a, double b){
        return a * b;
    }
}


