package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion (){
        app.getNavigationHelper().goToHomePage();
        if(!app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("Anna", "Kor", "AK", "Ukraine", "test@test", "777-777-777", "test1" ), true);
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().confirmContactDeletion();
    }

}
