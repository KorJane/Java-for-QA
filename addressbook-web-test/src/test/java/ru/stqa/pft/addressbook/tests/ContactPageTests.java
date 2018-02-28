package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by a.a.kornilov on 2/27/2018.
 */
public class ContactPageTests extends TestBase {

    @Test
    public void contactPageTest(){
        app.goTO().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromViewForm = app.contact().infoFromViewForm(contact);

        assertThat(mergeFullView(contact), equalTo(contactInfoFromViewForm.getFullView()));
    }

    private String mergeFullView(ContactData contact) {
        return Arrays.asList(contact.getFullName(), contact.getAddress(), contact.getAllPhones(), contact.getAllEmails()).stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

}
