package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class TestBase {

    protected final ApplicationManager app = new ApplicationManager();

    @BeforeMethod
    public void setUp () throws Exception{
        app.init();
    }

    @AfterMethod
    public void stop(){
        app.stopApp();
    }

}