
package twitterdriver.Visitor;

import twitterdriver.UserGroupVisitor;
import twitterdriver.UserLeaf;
import twitterdriver.GroupContainer;
//Visitor pattern to find how many message was tweeted in total from all users
public class TotalMessageVisitor implements UserGroupVisitor{
    
    private int TotalMessage=0;
    
    @Override
    public void visitUser(UserLeaf user) {
        //get size of how many user tweets they are plus total message and add to 
        //message total method to later return
        setMessageTotal(getMessageTotal() + user.getMyTweets().size());
    }

    @Override
    public void visitGroup(GroupContainer group) {
       
    }

    public int getMessageTotal() {
        return TotalMessage;
    }

    public void setMessageTotal(int totalMessage) {
        TotalMessage = totalMessage;
    }
}