package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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

        if(app.db().contacts().size() == 0){
            Groups groupListBefore = app.db().groups();
            if(groupListBefore.size() == 0){
                app.goTO().GroupPage();
                app.group().create(new GroupData().withName("test1").withHeader("header1").withFooter("footer1"));
            }

            Groups groupListAfter = app.db().groups();
            app.goTO().homePage();
            app.contact().create(new ContactData().withFirstName("Anna").withLastName("Kor").withNickName("AK").withAddress("Ukraine")
                    .withEmail("test@test").withHomePhone("777777").withMobilePhone("222").withWorkPhone("333")
                    .inGroup(groupListAfter.iterator().next()), true);
        }
    }

    @Test
    public void testContactModification(){
        Contacts before = app.db().contacts();
        app.goTO().homePage();
        ContactData modifyContact = before.iterator().next();
        ContactData contact = new ContactData().withFirstName("AnnaMD").withLastName("KorMD").withNickName("AKMD").withAddress("Ukraine")
                .withEmail("test@test").withHomePhone("777777").withMobilePhone("222").withWorkPhone("333").withId(modifyContact.getId());
        app.contact().modify(contact);
        Assert.assertEquals(app.contact().count(), before.size());
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.withOut(modifyContact).withAdded(contact)));
        verifyContactListInUI();
    }
}

