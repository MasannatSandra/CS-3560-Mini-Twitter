package twitterdriver.Visitor;

import twitterdriver.UserLeaf;
import twitterdriver.UserGroupVisitor;
import twitterdriver.GroupContainer;

//Visitor pattern to find total groups in total
public class TotalGroupVisitor implements UserGroupVisitor{
    //Variable to count
    private int totalGroup=0;
    
    public void visitUser(UserLeaf user){
        //return empty bc it doesn't affect group count
    }
    @Override
    //Add 1 to counter everytime a group is visit
    public void visitGroup(GroupContainer group) {
        setGroupTotal(getGroupTotal() + 1);
    }

    public int getGroupTotal() {
        return totalGroup;
    }

    public void setGroupTotal(int totalUser) {
        this.totalGroup = totalUser;
    }
}
    

