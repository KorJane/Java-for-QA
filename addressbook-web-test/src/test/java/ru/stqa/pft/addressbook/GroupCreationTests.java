package ru.stqa.pft.addressbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.After;
import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by a.a.kornilov on 2/9/2018.
 */
public class GroupCreationTests {
//    private WebDriver wd;
    FirefoxDriver wd;
//    private WebDriverWait wait;


    @BeforeMethod
    public void setUp () throws Exception{
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//        wait = new WebDriverWait(wd,10);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("unexpectedAlertBehavior","dismiss ");

        wd.get("http://localhost/addressbook/group.php");
        wd.findElement(By.name("user")).click();
        wd.findElement(By.name("user")).clear();
        wd.findElement(By.name("user")).sendKeys("admin");
        wd.findElement(By.name("pass")).click();
        wd.findElement(By.name("pass")).clear();
        wd.findElement(By.name("pass")).sendKeys("secret");
        wd.findElement(By.xpath("//form[@id='LoginForm']/input [3]")).click();

    }

    @Test
    public void testGroupCreation() {
        wd.findElement(By.linkText("groups")).click();
        wd.findElement(By.name("new")).click();
        wd.findElement(By.name("group_name")).click();
        wd.findElement(By.name("group_name")).clear();
        wd.findElement(By.name("group_name")).sendKeys("test1");
        wd.findElement(By.name("group_header")).click();
        wd.findElement(By.name("group_header")).clear();
        wd.findElement(By.name("group_header")).sendKeys("test2");
        wd.findElement(By.name("group_footer")).click();
        wd.findElement(By.name("group_footer")).clear();
        wd.findElement(By.name("group_footer")).sendKeys("test3");
        wd.findElement(By.name("submit")).click();
        wd.findElement(By.linkText("group page")).click();
    }

    @AfterMethod
    public void stop(){
        wd.quit();
        wd = null;
    }
}
