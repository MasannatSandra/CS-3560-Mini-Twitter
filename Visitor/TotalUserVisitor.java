package twitterdriver.Visitor;

import twitterdriver.UserGroupVisitor;
import twitterdriver.UserLeaf;
import twitterdriver.GroupContainer;

//Visitor pattern from finding total user from group and group within a group
public class TotalUserVisitor implements UserGroupVisitor{
    
    private int totalUser=0;
    
    @Override
    //everytime a user is visited, add one to total
    public void visitUser(UserLeaf user) {
        setUserTotal(getUserTotal() + 1);
    }

    @Override
    public void visitGroup(GroupContainer group) {
        //return nothing as this doesn't affect our count
    }
    //return total user number for us to call later to display
    public int getUserTotal() {
        return totalUser;
    }

    public void setUserTotal(int totalUser) {
        this.totalUser = totalUser;
    }
}