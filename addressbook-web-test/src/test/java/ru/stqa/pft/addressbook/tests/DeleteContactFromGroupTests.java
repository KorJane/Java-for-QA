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
public class DeleteContactFromGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {

        Groups groupListBefore = app.db().groups();
        if(groupListBefore.size()== 0){
            app.goTO().GroupPage();
            app.group().create(new GroupData().withName("test1"));
        }

        Contacts allContacts = app.db().contacts();
        Groups groupListAfter = app.db().groups();
        GroupData next = groupListAfter.iterator().next();

        if (allContacts.size() != 0) {
            for (ContactData contact : allContacts) {
                Groups groups = contact.getGroups();
                if (groups.size() != 0) {
                    return;
                }
            }
        }

        if(app.db().contactsWithGroups(next.getName()).size() == 0){
            app.contact().create(new ContactData().withFirstName("Anna").withLastName("Kor").withNickName("AK").withAddress("Ukraine")
            .withEmail("test@test").withHomePhone("777-77-777").withMobilePhone("222").withWorkPhone("333").inGroup(next), true);
        }
    }

    @Test
    public void testDeleteContactFromGroup(){

        Contacts allContacts = app.db().contacts();
        for (ContactData contact : allContacts){
            Groups groups = contact.getGroups();
            if(groups.size()!= 0){
                GroupData selectedGroup = groups.iterator().next();
                    Contacts before = app.db().contactsWithGroups(selectedGroup.getName());
                    app.goTO().homePage();
                    app.contact().selectGroups(selectedGroup.getName());
                    app.contact().DeleteFromGroup(contact);
                    Contacts after = app.db().contactsWithGroups(selectedGroup.getName());
                    assertThat(after, equalTo(before.withOut(contact)));
                    return;
            }
        }
    }
}
