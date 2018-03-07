package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContacts(){
        List<Object[]>  list = new ArrayList<Object[]>();
        list.add(new Object[]{new ContactData().withFirstName("firstname1").withLastName("lastName1").withAddress("address1").withEmail("mail@1").withHomePhone("111").withGroup("test1")});
        list.add(new Object[]{new ContactData().withFirstName("firstname2").withLastName("lastName2").withAddress("address2").withEmail("mail@2").withHomePhone("222").withGroup("test1")});
        list.add(new Object[]{new ContactData().withFirstName("firstname3").withLastName("lastName3").withAddress("address3").withEmail("mail@3").withHomePhone("333").withGroup("test1")});
        return list.iterator();
    }

    @Test(dataProvider = "validContacts")
     public void testContactCreation(ContactData contact){
        app.goTO().homePage();
        Contacts before = app.contact().all();
        File photo = new File("src/test/resources/photo.png");
//        ContactData contact = new ContactData().withFirstName("Anna").withLastName("Kor").withNickName("AK").withAddress("Ukraine")
//                .withEmail("test@test").withHomePhone("777-77-777").withMobilePhone("222").withWorkPhone("333").withGroup("test1").withPhoto(photo);
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

    @Test (enabled = false)
    public void testCurrentDir(){
        File currentDir = new File (".");
        System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/resources/photo.png");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }
}



