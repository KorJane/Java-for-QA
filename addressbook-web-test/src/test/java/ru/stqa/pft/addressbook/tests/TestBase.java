package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);

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

    @BeforeMethod
    public void logTestStart(Method m, Object[] p){
        logger.info("Start test " + m.getName() + Arrays.asList(p));
    }

    @AfterMethod
    public  void logTestStop(Method m){
        logger.info("Stop test " + m.getName());
    }

}
