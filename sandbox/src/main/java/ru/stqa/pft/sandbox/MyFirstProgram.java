package ru.stqa.pft.sandbox;

import sun.plugin.dom.css.Rect;

/**
 * Created by a.a.kornilov on 2/2/2018.
 */
public class MyFirstProgram {
    public static void main(String[] args) {

        hello("Alex");
        Square s = new Square(3);
//        s.l = 3;

        Rectangle r = new Rectangle(6, 4);
//        r.a = 6;
//        r.b = 4;
        System.out.println(s.area());
        System.out.println(r.area());
    }
    public static void hello(String s){

        System.out.println("Hello " + s + " !");
    }


}


