
package twitterdriver.Visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import twitterdriver.UserGroupVisitor;
import twitterdriver.UserLeaf;
import twitterdriver.GroupContainer;

//Visitor pattern that find % of positive message posted by everyone
public class PositiveVisitor implements UserGroupVisitor{
    private double positiveCount = 0;
    private double totalMessages = 0;
    private double positivePercentage = 0;
    private List<String> positiveWords= new ArrayList<>
        (Arrays.asList("good", "lit", "awesome", "great", "excellent", "nice",
                "happy"));

    @Override
    public void visitUser(UserLeaf user) {
        for (String message : user.getMyTweets()){
            //Counter for total message
            totalMessages +=1;
            //iterative through each word to locate a positive word to then
            //add to positive count
            for (String positive : positiveWords){
                if (message.toLowerCase().contains(positive.toLowerCase())){
                    positiveCount +=1;
                    break;
                }
            }
        }
    }

    @Override
    public void visitGroup(GroupContainer group) {
        //return nothing since group doesn't contribute to the count
    }

    public double getPositivePercentage() {
        if (totalMessages ==0) {
            return positivePercentage;
        }
        //calculate the positive percentage then return to call in class
        setPositivePercentage((positiveCount/totalMessages)*100.0);
        return (positivePercentage);
    }

    public void setPositivePercentage(double positivePercentage) {
        this.positivePercentage = positivePercentage;
    }
}

