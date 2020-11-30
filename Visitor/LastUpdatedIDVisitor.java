package twitterdriver.Visitor;

import twitterdriver.UserGroupVisitor;
import twitterdriver.UserLeaf;
import twitterdriver.GroupContainer;

public class LastUpdatedIDVisitor implements UserGroupVisitor {
    String lastUpdatedUser = "No one";
    long recentUpdatedTime = 0;

    @Override
    public void visitUser(UserLeaf user) {
        //If user updated was more recent then previous, get the ID of user
        //and store in getLastUpdatedUser
        if (user.getLastUpdatedTime() > recentUpdatedTime){
            recentUpdatedTime = user.getLastUpdatedTime();
            lastUpdatedUser = user.getID();
        }
    }

    @Override
    public void visitGroup(GroupContainer group) {

    }
    //return ID of last updated user
    public String getLastUpdateUser() {
        return lastUpdatedUser;
    }
}