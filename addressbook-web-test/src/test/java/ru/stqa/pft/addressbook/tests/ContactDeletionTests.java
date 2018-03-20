package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if(app.db().contacts().size() == 0){
            app.contact().create(new ContactData().withFirstName("Anna").withLastName("Kor").withNickName("AK").withAddress("Ukraine")
                    .withEmail("test@test").withHomePhone("777-77-777").withMobilePhone("222").withWorkPhone("333").withGroup("test1"), true);
            app.goTO().homePage();
        }
    }

    @Test
    public void testContactDeletion (){
        Contacts before = app.db().contacts();
        ContactData deleteContact = before.iterator().next();
        app.goTO().homePage();
        app.contact().delete(deleteContact);
        Assert.assertEquals(app.contact().count(), before.size()-1);
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.withOut(deleteContact)));
        verifyContactListInUI();
    }
}
