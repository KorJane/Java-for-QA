package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class NavigationHelper {
    private FirefoxDriver wd;

    public NavigationHelper(FirefoxDriver wd) {
        this.wd = wd;
    }

    public void gotoGroupPage() {
       wd.findElement(By.linkText("groups")).click();
    }

    public void goToNewContactPage() {
        wd.findElement(By.linkText("add new")).click();
    }
}
