package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class ContactCreationTests extends TestBase {

    @Test
     public void testContactCreation(){
        app.getNavigationHelper().goToHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();
        ContactData contact = new ContactData("Anna2", "Kor", "AK", "Ukraine", "test@test", "777-777-777", "test1" );
        app.getContactHelper().createContact(contact, true);
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() +1);

//        int max = 0;
//        for(ContactData c :after){
//            if(c.getId() > max){
//              max =  c.getId();
//            }
//        }

        int max1 = after.stream().max((o1,o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId();
        contact.setId(max1);
        before.add(contact);
        Assert.assertEquals(new HashSet<Object>(after), new HashSet<Object> (before));

        }
    }



