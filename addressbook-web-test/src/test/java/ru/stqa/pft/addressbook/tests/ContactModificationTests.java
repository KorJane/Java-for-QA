package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification(){
//        app.getContactHelper().selectContact();
        app.getContactHelper().editContact();
        app.getContactHelper().fillContactForm(new ContactData("AnnaMD", "KorMD", "AK", "Ukraine", "test@test", "777-777-777"));
        app.getContactHelper().submitContactFormModification();
        app.getContactHelper().returnToHomePage();

    }
}

