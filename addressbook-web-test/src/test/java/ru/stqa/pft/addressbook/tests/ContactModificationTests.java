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
public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTO().homePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Anna").withLastName("Kor").withNickName("AK").withAddress("Ukraine")
                    .withEmail("test@test").withHomePhone("777-77-777").withMobilePhone("222").withWorkPhone("333").withGroup("test1"), true);
        }
    }

    @Test
    public void testContactModification(){
        app.goTO().homePage();
        if(app.contact().all().size() ==0){
            app.contact().create(new ContactData().withFirstName("Anna").withLastName("Kor").withNickName("AK").withAddress("Ukraine")
                    .withEmail("test@test").withHomePhone("777-77-777").withMobilePhone("222").withWorkPhone("333").withGroup("test1"), true);
        }
        Contacts before = app.contact().all();
        ContactData modifyContact = before.iterator().next();
        ContactData contact = new ContactData().withFirstName("AnnaMD").withLastName("KorMD").withNickName("AKMD").withAddress("Ukraine")
                .withEmail("test@test").withHomePhone("777-77-777").withMobilePhone("222").withWorkPhone("333").withGroup("test1").withId(modifyContact.getId());
        app.contact().modify(contact);
        Assert.assertEquals(app.contact().count(), before.size());
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.withOut(modifyContact).withAdded(contact)));
    }

}

