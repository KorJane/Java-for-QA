package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by a.a.kornilov on 2/14/2018.
 */
public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){

        if (app.db().groups().size() == 0){
            app.goTO().GroupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testGroupDeletion(){
        Groups before = app.db().groups();
        GroupData deletedGroup = before.iterator().next();
        app.goTO().GroupPage();
        app.group().delete(deletedGroup);
        assertEquals(app.group().count(), before.size()-1);
        Groups after = app.db().groups();
        assertThat(after, equalTo(before.withOut(deletedGroup)));
        verifyGroupListInUI();
    }

}
