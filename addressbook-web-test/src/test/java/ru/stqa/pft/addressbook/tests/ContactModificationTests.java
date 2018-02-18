package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification(){
        app.getNavigationHelper().goToHomePage();
        int before = app.getContactHelper().getContactCount();

        if(!app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("Anna", "Kor", "AK", "Ukraine", "test@test", "777-777-777", "test1" ), true);
            before ++;
        }
        app.getContactHelper().editContact();
        app.getContactHelper().fillContactForm(new ContactData("AnnaMD", "KorMD", "AK", "Ukraine", "test@test", "777-777-777", null), false);
        app.getContactHelper().submitContactFormModification();
        app.getContactHelper().returnToHomePage();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before);
    }
}

