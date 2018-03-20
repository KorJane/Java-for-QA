package ru.stqa.pft.addressbook.tests;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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

    @AfterMethod(alwaysRun = true)
    public  void logTestStop(Method m){
        logger.info("Stop test " + m.getName());
    }

    public void verifyGroupListInUI() {

        if(Boolean.getBoolean("verifyUI")){
            Groups dbGroups = app.db().groups();
            Groups uiGroups = app.group().all();

            assertThat(uiGroups, equalTo(dbGroups.stream().map((g) -> new GroupData().withId(g.getId()). withName(g.getName()))
                    .collect(Collectors.toSet())));
        }
    }

    public void verifyContactListInUI() {
        if (Boolean.getBoolean("verifyUI")){
            Contacts dbContacts = app.db().contacts();
            Contacts UiContacts = app.contact().all();

            assertThat(UiContacts, equalTo(dbContacts.stream().map((c) -> new ContactData().withId(c.getId()). withFirstName(c.getFirstName()).withLastName(c.getLastName())
                    .withAddress(c.getAddress()).withAllEmails(c.getEmail()).withAllPhones(c.getHomePhone()+ "\n" + c.getMobilePhone() + "\n" + c.getWorkPhone()))
                    .collect((Collectors.toSet()))));
        }
    }
}
