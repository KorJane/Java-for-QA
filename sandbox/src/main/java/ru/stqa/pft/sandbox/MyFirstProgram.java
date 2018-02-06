package ru.stqa.pft.sandbox;

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
        System.out.println("Площадь квадрата со сторой " + s.l + " равна " + s.area());
        System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " равна " + r.area());

//        Point p = new Point (1,2);
//        System.out.println("Точка  c координатами: " + p.x + ":" + p.y );
//
        Point p1 = new Point (1,-1);
        Point p2 = new Point (6,-2);

//        System.out.println("Расстояние между точками: " + distance(p1, p2));   первый метод ( вычесление через функцию)
        System.out.println("Расстояние между точками: " + p1.distance(p2));  // второй метод ( вычесление через метод в классе Point)

    }
    public static void hello(String s){

        System.out.println("Hello " + s + " !");
    }

//    public static double distance(Point p1, Point p2){
//        return Math.sqrt( Math.pow((p2.x - p1.x),2) + Math.pow((p2.y - p1.y),2));
//    }

}


