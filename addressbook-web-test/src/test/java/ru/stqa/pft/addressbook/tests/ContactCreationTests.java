package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class ContactCreationTests extends TestBase {

    @Test
     public void testContactCreation(){
        app.goTO().homePage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstName("Anna").withLastName("Kor").withNickName("AK").withAddress("Ukraine")
                .withEmail("test@test").withHomePhone("777-77-777").withMobilePhone("222").withWorkPhone("333").withGroup("test1");
        app.contact().create(contact, true);
        assertEquals(app.contact().count(), before.size() +1);
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

    }

    @Test(enabled = false)
    public void testBadContactCreation(){
        app.goTO().homePage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstName("Anna'").withLastName("Kor").withNickName("AK").withAddress("Ukraine").withEmail("test@test").withHomePhone("111").withMobilePhone("222").withWorkPhone("333").withGroup("test1");
        app.contact().create(contact, true);
        assertEquals(app.contact().count(), before.size());
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before));

    }

}



