package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class ContactCreationTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {

        Groups groupListBefore = app.db().groups();
        if (groupListBefore.size() == 0) {
            app.goTO().GroupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }


        @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(new File ("src/test/resources/contacts.xml")))){
            String xml = "";
            String line = reader.readLine();
            while (line != null){
                xml += line;
                line = reader.readLine();
            }
            XStream xStream = new XStream();
            xStream.processAnnotations(ContactData.class);
            List<ContactData> contacts = (List<ContactData>)xStream.fromXML(xml);
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(new File ("src/test/resources/contacts.json")))){
            String json = "";
            String line = reader.readLine();
            while (line != null){
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }


    @Test(dataProvider = "validContactsFromJson")
     public void testContactCreation(ContactData contact){
        Groups groups= app.db().groups();
        app.goTO().homePage();
        Contacts before = app.db().contacts();
        File photo = new File("src/test/resources/photo.png");
        app.contact().create(contact, true);
        assertEquals(app.contact().count(), before.size() +1);
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
        verifyContactListInUI();
    }



    @Test(enabled = false)
    public void testBadContactCreation(){
        app.goTO().homePage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstName("Anna'").withLastName("Kor").withNickName("AK").withAddress("Ukraine").withEmail("test@test").withHomePhone("111").withMobilePhone("222").withWorkPhone("333");
//                .withGroup("test1");
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



