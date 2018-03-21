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

//import static ru.stqa.pft.addressbook.tests.ContactPageTests.cuted;

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
        attach(By.name("photo"), contactData.getPhoto());

        if(creation){
//            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
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
            String lastName = fields.get(1).getText();
            String firstName = fields.get(2).getText();
            String fullName = firstName + " " + lastName;
            String address = fields.get(3).getText();
            String allEmails = fields.get(4).getText();
            String allPhones = fields.get(5).getText();
            ContactData contact = new ContactData().withId(id). withFirstName(firstName).withLastName(lastName).withFullName(fullName).withAddress(address)
//                    .withGroup("test1")
                    .withAllPhones(allPhones).withAllEmails(allEmails);
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
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName)
                .withAddress(address).withEmail(email).withEmail2(email2).withEmail3(email3)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
    }

    public ContactData infoFromViewForm(ContactData contact) {
        viewContact(contact.getId());
        String content = wd.findElement(By.id("content")).getText();
        List<String> stringList = splitted(content);
        String correctedContent = replaced(stringList.toString());
        wd.navigate().back();

        return new ContactData().withId(contact.getId()).withFullView(correctedContent);
    }


    public static List<String> splitted(String list){

        String[] strings = list.split("\n");
        List<String> finalList = new ArrayList<String>();
        for (int i = 0; i < strings.length; i ++){
            if (!strings[i].isEmpty()){
                if (strings[i].startsWith("H:")||strings[i].startsWith("M:")||strings[i].startsWith("W:")){
                    finalList.add(cleaned(strings[i]));
                }else if (!strings[i].startsWith("Member of:")){
                    finalList.add((strings[i]));
                }
            }
        }

        return finalList;
    }

    public static String cleaned(String element){
        return element.replaceAll("\\s", "").replaceAll("[-()]","").replaceAll("H:", "").replaceAll("M:", "").replaceAll("W:", "");
    }

    public static String replaced(String element){
        return element.replaceAll(", ", "\n").replaceAll("\\[", "").replaceAll("\\]","");
    }

    private void viewContact(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='view.php?id=%s']",id))).click();
    }

    public void addToGroup(ContactData contact) {
        selectContactById(contact.getId());
        new Select(wd.findElement(By.name("to_group"))).selectByVisibleText("test1"); //contactData.getGroup());
        submitAdditionToGroup();
        goToSpecialGroupPage();

    }

    private void submitAdditionToGroup() {
            click(By.name("add"));
    }

    private void goToSpecialGroupPage() {
        click(By.linkText("group page \"test1\""));
    }

    public void selectGroups() {
        new Select(wd.findElement(By.name("group"))).selectByVisibleText("test1"); //contactData.getGroup());
    }

    public void DeleteFromGroup(ContactData contact) {
        selectContactById(contact.getId());
        submitGroupDeletion();



    }

    private void submitGroupDeletion() {
        click(By.name("remove"));
        goToSpecialGroupPage();
    }

}
