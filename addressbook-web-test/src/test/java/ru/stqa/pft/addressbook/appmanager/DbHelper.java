package ru.stqa.pft.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by a.a.kornilov on 3/15/2018.
 */
public class DbHelper {

    private final SessionFactory sessionFactory;

    public DbHelper(){

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }

    public Groups groups(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> result = session.createQuery( "from GroupData" ).list();
        session.getTransaction().commit();
        session.close();
        return new Groups(result);
    }


    public Contacts contacts(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result = session.createQuery( "from ContactData where deprecated = '0000-00-00'" ).list();
        session.getTransaction().commit();
        session.close();
        return new Contacts(result);
    }


    public Contacts contactsWithGroups(String groupName, int groupId){ //, int groupId){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result = session.createQuery( "from ContactData where deprecated = '0000-00-00'").list();
        List<GroupData> resultFromGroups = session.createQuery( "from GroupData where group_name = '" + groupName + "' and group_id = '" + groupId + "'" ).list();

       List<ContactData> updatedResult = new ArrayList<ContactData>();

        for ( ContactData contact : result ) {
            Groups groups = contact.getGroups();
            for( GroupData group : groups){
                if(resultFromGroups.size()!=0){
                    if(group.getId() == resultFromGroups.get(0).getId()){
                        updatedResult.add(contact);
                    }
                }
            }
        }
        session.getTransaction().commit();
        session.close();
        return new Contacts(updatedResult);

    }


    public Groups allAddedGroups(String groupName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> result = session.createQuery( "from GroupData where group_name = '" + groupName + "'").list();
        session.getTransaction().commit();
        session.close();
        return new Groups(result);
    }

    public GroupData groupWithMaxId(String groupName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> result = session.createQuery( "from GroupData where group_name = '" + groupName + "'").list();

        List<Integer> listID = new ArrayList<Integer>();
        for (GroupData group : result ){
            listID.add(group.getId());
        }
        GroupData maxGroup = new GroupData();
        for (GroupData group : result ){
            if(group.getId() == Collections.max(listID)){
               maxGroup = group;
            }
        }
        session.getTransaction().commit();
        session.close();
        return maxGroup;
    }



}
