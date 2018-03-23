package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by a.a.kornilov on 3/20/2018.
 */
public class AddContactToGroupTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {

        Groups groupListBefore = app.db().groups();
        if(groupListBefore.size()== 0){
            app.goTO().GroupPage();
            app.group().create(new GroupData().withName("test1"));
        }

//        Contacts allContacts = app.db().contacts();
//        Groups groupListAfter = app.db().groups();
//        GroupData next = groupListAfter.iterator().next();
//
        if(app.db().contacts().size() == 0){
            app.contact().create(new ContactData().withFirstName("Anna").withLastName("Kor").withNickName("AK").withAddress("Ukraine")
                            .withEmail("test@test").withHomePhone("777-77-777").withMobilePhone("222").withWorkPhone("333"), true);
            app.goTO().homePage();
        }

    }

    @Test
    public void testAddContactToGroup(){

        Groups allGroups = app.db().groups();
        Contacts allContacts = app.db().contacts();
        ContactData selectedContact = allContacts.iterator().next();
        if(allGroups.size() == selectedContact.getGroups().size()){
            app.goTO().GroupPage();
            app.group().create(new GroupData().withName("additionalGroup"));
            GroupData newGroup = app.db().groups().iterator().next().withName("additionalGroup");
            app.goTO().homePage();
            app.contact().addToGroup(selectedContact,newGroup.getName());
        }



//        Contacts before = app.db().contactsWithGroups("test1");
//        ContactData addedToGroupContact;
//        if(before.size() == 0){
//            Contacts c = app.db().contacts();
//            addedToGroupContact = c.iterator().next();
//        }else{
//            addedToGroupContact = before.iterator().next();
//
//        }
//        app.goTO().homePage();
//        app.contact().addToGroup(addedToGroupContact);
//        Contacts after = app.db().contactsWithGroups("test1");
//        assertThat(after, equalTo(before.withAdded(addedToGroupContact)));
    }

}
