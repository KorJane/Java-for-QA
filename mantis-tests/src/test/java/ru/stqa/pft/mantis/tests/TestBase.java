package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class TestBase {


    protected static final ApplicationManager app
//            =  new ApplicationManager(BrowserType.CHROME);
            =  new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));


    @BeforeSuite
    public void setUp () throws Exception{
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void stop(){
        app.stopApp();
    }

}
