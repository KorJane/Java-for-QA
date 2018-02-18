package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification(){
        app.getNavigationHelper().gotoGroupPage();
        int before = app.getGroupHelper().getGroupCounter();
        if (! app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(new GroupData("test1", null, null));
            before ++;
        }
        app.getGroupHelper().selectGroup(before - 1);
        app.getGroupHelper().modifySelectedGroups();
        app.getGroupHelper().fillGroupForm(new GroupData("test1", "test22", "test33"));
        app.getGroupHelper().submitGroupFormModification();
        app.getGroupHelper().returnToGroupPage();
        int after = app.getGroupHelper().getGroupCounter();
        Assert.assertEquals(after, before);

    }

}
