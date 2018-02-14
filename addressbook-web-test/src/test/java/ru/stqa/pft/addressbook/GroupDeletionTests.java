package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class GroupDeletionTests extends TestBase {
    @Test
    public void testGroupDeletion(){
        gotoGroupPage();
        selectGroup();
        deleteSelectedGroups();
        returnToGroupPage();
    }


}
;