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

        if(app.db().contacts().size() == 0){
            app.contact().create(new ContactData().withFirstName("Anna").withLastName("Kor").withNickName("AK").withAddress("Ukraine")
                            .withEmail("test@test").withHomePhone("777-77-777").withMobilePhone("222").withWorkPhone("333"), true);
            app.goTO().homePage();
        }
    }

    @Test
    public void testAddContactToGroup(){

        Groups allGroups = app.db().groups(); // собираем все существующие группы и записываем их имена в список
        Contacts allContacts = app.db().contacts(); // собираем все группы под контаком и записываем их имена в список
        ContactData selectedContact = allContacts.iterator().next();
        Groups groupsNameFromContact = selectedContact.getGroups(); // все группы под первым контактом

        if (allGroups.size() != groupsNameFromContact.size()){  // если количество групп под контактов равно общему кол-ву групп
            for (GroupData groupFromList : allGroups){ // для каждой группы в общем списке проверяем,  входит ли она в список групп под контактом
                if(!groupsNameFromContact.contains(groupFromList)){ // если группы из общего списка нет в группе контакта  - добавляем эту группу в список групп контакта
                    Contacts before = app.db().contactsWithGroups(groupFromList.getName(),groupFromList.getId());
                    app.goTO().homePage();
                    app.contact().addToGroup(selectedContact,  groupFromList.getId());
                    Contacts after = app.db().contactsWithGroups(groupFromList.getName(),groupFromList.getId());
                    ContactData updatedContact = app.db().contacts().iterator().next();// основленный контакт с базы с добавленной новой группой
                    assertThat(after, equalTo(before.withAdded(updatedContact)));
                    return;
                }
            }
        }
        else{ // если в контакте уже есть все группы с общего списка,  создаем новую группу и добавляем её под контакт
            app.goTO().GroupPage();
            GroupData additionalGroup = new GroupData().withName("additionalGroup");
            app.group().create(additionalGroup);
            GroupData addedGroupWithMaxId = app.db().groupWithMaxId(additionalGroup.getName());
            Contacts before = app.db().contactsWithGroups(addedGroupWithMaxId.getName(),addedGroupWithMaxId.getId());
            app.goTO().homePage();
            app.contact().addToGroup(selectedContact,addedGroupWithMaxId.getId());
            ContactData updatedContact = app.db().contacts().iterator().next();// основленный контакт с базы с добавленной новой группой
            Contacts after = app.db().contactsWithGroups(addedGroupWithMaxId.getName(), addedGroupWithMaxId.getId());//,newGroup.getId());
            assertThat(after, equalTo(before.withAdded(updatedContact)));
        }
    }
}
