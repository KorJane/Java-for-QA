package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

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
        type(By.name("home"),contactData.getHomePhone());
        type(By.name("mobile"),contactData.getMobilePhone());
        type(By.name("work"),contactData.getWorkPhone());

        if(creation){
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }


    public void deleteSelectedContact() {
        click(By.cssSelector("[value='Delete']"));
    }

    public void confirmContactDeletion() {
        wd.switchTo().alert().accept();
    }

    public void editContact(int id) {
//        wd.findElement(By.xpath("//tr//a[contains(@href, 'edit.php?id=" + id  +"')]")).click();
//        wd.findElement(By.xpath(String.format("//tr//a[contains(@href, 'edit.php?id=%s')]", id))).click();
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();


    }

    public void submitContactFormModification() {
        click(By.name("update"));
    }

    public void create(ContactData contactData, boolean b) {
//        TestBase.app.goTO().goToNewContactPage();
        click(By.linkText("add new"));
        fillContactForm(contactData, b);
        submitContactForm();
        contactCache = null;
        returnToHomePage();
    }

    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        editContact(contact.getId());
        fillContactForm(contact, false);
        submitContactFormModification();
        contactCache = null;
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        contactCache = null;
        confirmContactDeletion();
    }

    private void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value = '" + id + "']")).click();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.cssSelector("tr[name = 'entry']"));
        for (WebElement element : elements){
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            List<WebElement> fields = element.findElements(By.tagName("td"));
            String firstName = fields.get(2).getText();
            String lastName = fields.get(1).getText();
            ContactData contact = new ContactData().withId(id). withFirstName(firstName).withLastName(lastName);
            contacts.add(contact);
        }
        return contacts;
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null){
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.cssSelector("tr[name = 'entry']"));
        for (WebElement element : elements){
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            List<WebElement> fields = element.findElements(By.tagName("td"));
            String firstName = fields.get(2).getText();
            String lastName = fields.get(1).getText();
            String allPhones = fields.get(5).getText();
//            String[] phones = allPhones.split("\n");
            ContactData contact = new ContactData().withId(id). withFirstName(firstName).withLastName(lastName).withAddress("Ukraine").withEmail("test@test")
                    .withGroup("test1").withAllPhones(allPhones);
//                            withHomePhone(phones[0]).withMobilePhone(phones[1]).withWorkPhone(phones[2]);
            contactCache.add(contact);
        }
        return contactCache;
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public ContactData infoFromEditForm(ContactData contact) {
        editContact(contact.getId());
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName).withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
    }

}
