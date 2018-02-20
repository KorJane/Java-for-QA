package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.ArrayList;
import java.util.List;

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

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();

    }

    public void deleteSelectedContact() {
        click(By.cssSelector("[value='Delete']"));
    }

    public void confirmContactDeletion() {
        wd.switchTo().alert().accept();
    }

    public void editContact(int index) {
//        click(By.xpath("//tr[.//input[@name = 'selected[]']]//a[contains(@href,'edit')]"));
//        click(By.xpath("//tr//a[contains(@href,'edit')]"));
        wd.findElements(By.xpath("//tr//a[contains(@href,'edit')]")).get(index).click();

    }

    public void submitContactFormModification() {
        click(By.name("update"));
    }

    public void create(ContactData contactData, boolean b) {
//        TestBase.app.goTO().goToNewContactPage();
        click(By.linkText("add new"));
        fillContactForm(contactData, b);
        submitContactForm();
        returnToHomePage();
    }

    public void modify(ContactData contact, int index) {
        editContact(index);
        fillContactForm(contact, false);
        submitContactFormModification();
        returnToHomePage();
    }

    public void delete(int index) {
        selectContact(index);
        deleteSelectedContact();
        confirmContactDeletion();
    }


    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.cssSelector("tr[name = 'entry']"));
        for (WebElement element : elements){
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            List<WebElement> fields = element.findElements(By.tagName("td"));
            String firstName = fields.get(2).getText();
            String lastName = fields.get(1).getText();
            ContactData contact = new ContactData(id, firstName, lastName,"AK","Ukraine","test@test","777-777-777","test1");
            contacts.add(contact);
        }
        return contacts;
    }
}
