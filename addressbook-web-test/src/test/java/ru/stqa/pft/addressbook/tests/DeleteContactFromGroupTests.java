package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by a.a.kornilov on 3/20/2018.
 */
public class DeleteContactFromGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if(app.db().contactsWithGroups("test1").size() == 0){
            app.contact().create(new ContactData().withFirstName("Anna").withLastName("Kor").withNickName("AK").withAddress("Ukraine")
                            .withEmail("test@test").withHomePhone("777-77-777").withMobilePhone("222").withWorkPhone("333")
//                    .withGroup("test1")
                    , true);
            app.goTO().homePage();
        }
    }

    @Test
    public void testDeleteContactFromGroup(){
        Contacts before = app.db().contactsWithGroups("test1");
        ContactData contactForDeletionFromGroup = before.iterator().next();
        app.goTO().homePage();
        app.contact().selectGroups();
        app.contact().DeleteFromGroup(contactForDeletionFromGroup);
        Contacts after = app.db().contactsWithGroups("test1");
        assertThat(after, equalTo(before.withOut(contactForDeletionFromGroup)));
    }

}
