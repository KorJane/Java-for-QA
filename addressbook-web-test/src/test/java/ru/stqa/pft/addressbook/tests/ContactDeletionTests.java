package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion (){
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().confirmContactDeletion();
    }

}
