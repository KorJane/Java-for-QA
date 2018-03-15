package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

/**
 * Created by a.a.kornilov on 3/14/2018.
 */
public class DbConnectionContactsTest {

    @Test
    public void testDbConnectionContacts(){
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook?user=root&password=");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select id, firstname, lastname, address, home, email from addressbook");
            Contacts contacts = new Contacts();
            while(rs.next()){
                contacts.add(new ContactData().withId(rs.getInt("id")).withFirstName(rs.getString("firstname"))
                        .withLastName(rs.getString("lastname")).withAddress(rs.getString("address"))
                        .withHomePhone(rs.getString("home")).withEmail(rs.getString("email")));
            }
            rs.close();
            st.close();
            conn.close();
            System.out.println(contacts);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }

}

