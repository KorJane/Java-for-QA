package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by a.a.kornilov on 2/6/2018.
 */
public class PointTests {
    @Test
    public void testDistance (){
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 2);
        Assert.assertEquals(p1.distance(p2), 1.0);

    }
}
