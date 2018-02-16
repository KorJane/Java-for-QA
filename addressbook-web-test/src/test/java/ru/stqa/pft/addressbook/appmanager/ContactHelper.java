package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void submitContactForm() {
       click(By.name("submit"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"),contactData.getFirstName());
        type(By.name("lastname"),contactData.getLastName());
        type(By.name("nickname"),contactData.getNickName());
        type(By.name("address"),contactData.getAddress());
        type(By.name("email"),contactData.getEmail());
        type(By.name("home"),contactData.getPhone());

        if(creation){
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void selectContact() {
        click(By.name("selected[]"));
    }

    public void deleteSelectedContact() {
        click(By.cssSelector("[value='Delete']"));
    }

    public void confirmContactDeletion() {
        wd.switchTo().alert().accept();
    }

    public void editContact() {
//        click(By.xpath("//tr[.//input[@name = 'selected[]']]//a[contains(@href,'edit')]"));
        click(By.xpath("//tr//a[contains(@href,'edit')]"));
    }

    public void submitContactFormModification() {
        click(By.name("update"));
    }

    public void createContact(ContactData contactData, boolean b) {
//        app.getNavigationHelper().goToNewContactPage();
        click(By.linkText("add new"));
        fillContactForm(contactData, b);
        submitContactForm();
        returnToHomePage();

    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }
}
