package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;

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
        List<String> allGroupNames = new ArrayList<String>();
        for (GroupData group : allGroups ){
            allGroupNames.add(group.getName());
        }
        System.out.println("все существующие группы" + allGroupNames);

        Contacts allContacts = app.db().contacts(); // собираем все группы под контаком и записываем их имена в список
        ContactData selectedContact = allContacts.iterator().next();
        Groups groupsNameFromContact = selectedContact.getGroups();

        List<String> allGroupsNameFromContacts = new ArrayList<String>();
        for(GroupData group : groupsNameFromContact){
            allGroupsNameFromContacts.add(group.getName());
        }

        System.out.println("все группы под контактом" + allGroupsNameFromContacts);

        if(allGroupNames.size() != allGroupsNameFromContacts.size()){   // если размер списков не равен
            for(String groupName : allGroupNames){ // для каждой группы в списке проверяем входит ли он в список группп конкретного выбранного контакта
                if(!allGroupsNameFromContacts.contains(groupName)){ // если нет,  то добавляем эту группу к контакту

                    GroupData next = allGroups.iterator().next();
                    if(next.getName() == groupName ){ // перебираем все группы по именам,  пока не находим группу с именнем равной искомой группе groupName



                        Contacts before = app.db().contactsWithGroups(groupName, next.getId());
                        app.goTO().homePage();
                        int id = next.getId();
                        System.out.println("ID которое должно быть выбранно " + id);
                        app.contact().addToGroup(selectedContact,  next.getId());//allGroups.iterator().next().withName(groupName).getName());

                        Contacts after = app.db().contactsWithGroups(groupName, next.getId());

                        assertThat(after, equalTo(before.withAdded(selectedContact)));
                    }
                }
            }

        }else{ // если в контакте уже есть все группы с общего списка,  создаем новую группу и добавляем её под контакт

            app.goTO().GroupPage();
            GroupData additionalGroup = new GroupData().withName("additionalGroup");
            app.group().create(additionalGroup);
//            GroupData newGroup = app.db().groups().iterator().next();

            Groups additionGroups = app.db().allAddedGroups("additionalGroup");
            List<Integer> listOfGroupId = new ArrayList<Integer>();
            for( GroupData additionGroup : additionGroups ){
                listOfGroupId.add(additionGroup.getId());
            }
//            Contacts before = app.db().contactsWithGroups(newGroup.getName(),newGroup.getId()); //,newGroup.getId() );
            GroupData maxIdForAddedGroup = app.db().groupWithMaxId("additionalGroup");
            Contacts before = app.db().contactsWithGroups(maxIdForAddedGroup.getName(),maxIdForAddedGroup.getId()); //,newGroup.getId() );

            app.goTO().homePage();
            app.contact().addToGroup(selectedContact,maxIdForAddedGroup.getId());// newGroup.getId());
            ContactData updatedContact = app.db().contacts().iterator().next();//.inGroup(newGroup); // основленный контакт с базы с добавленной новой группой

            Contacts after = app.db().contactsWithGroups(maxIdForAddedGroup.getName(), maxIdForAddedGroup.getId());//,newGroup.getId());
            assertThat(after, equalTo(before.withAdded(updatedContact)));
        }





//        if(allGroups.size() == selectedContact.getGroups().size()){
//            app.goTO().GroupPage();
//            app.group().create(new GroupData().withName("additionalGroup"));
//            GroupData newGroup = app.db().groups().iterator().next().withName("additionalGroup");
//            app.goTO().homePage();
//            app.contact().addToGroup(selectedContact,newGroup.getName());
//        }



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
