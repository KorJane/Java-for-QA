package ru.stqa.pft.addressbook;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class ContactCreationTests extends TestBase {

    @Test
     public void testContactCreation(){
        goToNewContactPage();
        fillContactForm(new ContactData("Anna", "Kor", "AK", "Ukraine", "test@test", "777-777-777"));
        submitContactForm();
        returnToHomePage();

    }


}
