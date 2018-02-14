package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class GroupDeletionTests extends TestBase {
    @Test
    public void testGroupDeletion(){
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().deleteSelectedGroups();
        app.getGroupHelper().returnToGroupPage();
    }


}
;