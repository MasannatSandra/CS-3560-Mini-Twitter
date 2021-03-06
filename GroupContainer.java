package twitterdriver;

import java.util.ArrayList;
import java.util.List;

//Groups are the container from the composite pattern, accept visitor from visitor patterns
public class GroupContainer implements CompositeTree{
    
    private List<CompositeTree> groupUsers= new ArrayList<>();
    private String groupID;
    private Long createdGroup;
    
    //verify ID haven't already been used
    //Set group ID and the time of created Group each time a new one is made
    public GroupContainer(String newID) {
        this.groupID = newID;
        this.createdGroup=System.currentTimeMillis();
    }
    
    @Override
    public String getID() {
        return this.groupID;
    }
    public List<CompositeTree> getGroupUsers(){
        return groupUsers;
    }

    @Override
    public String toString() {
        return groupID;
    }
    //getter and setter methods for creationtime for group
    public long getCreationTime(){
        return createdGroup;
    }
    public void setCreationTime(){
        createdGroup=System.currentTimeMillis();
    }
    
    @Override
    public void accept(UserGroupVisitor visitor) {
        visitor.visitGroup(this);
        for(CompositeTree members : groupUsers) {
            if (members instanceof UserLeaf) {
                members.accept(visitor);
            } else if (members instanceof GroupContainer) {
                members.accept(visitor);
            }
        }
    }
    //Everytime a group is added, display in the Tree
    public void addGroupUsers(CompositeTree newGroup){
        this.groupUsers.add(newGroup);
    }
    //Verify a group contain a user ID
    public Boolean containsUser(String UserID){
        for (CompositeTree members : groupUsers) {
            if (members instanceof UserLeaf) {
                if (members.getID().equals(UserID)) {
                    return true;
                }
            }
            else if (members instanceof GroupContainer) {
                if (((GroupContainer) members).containsUser(UserID)) {
                    return true;
                }
            }
        }
        return false;
    }
    //Check if group already contain a group ID/Name
    public Boolean containsGroup(String memberID){
        for (CompositeTree members : groupUsers) {
            if (members instanceof UserLeaf) {
                continue;
            }
            // check for a group in this.groupMembers
            else if (members instanceof GroupContainer) {
                if (members.getID().equals(memberID)){
                    return true;
                }
                //check ID of groups within groups
                else {
                    if(((GroupContainer) members).containsGroup(memberID)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //return user in a group of a group within a group
    public UserLeaf getUser(String userID){
        for (CompositeTree members : groupUsers) {
            if (members instanceof UserLeaf) {
                if (members.getID().equals(userID)){
                    return (UserLeaf) members;
                }
            }
            else if (members instanceof GroupContainer) {
                // iterate through all groups in group and check if the 
                //user id exist there
                if (((GroupContainer) members).containsUser(userID)) {
                    return ((GroupContainer) members).getUser(userID);
                }
            }
        }
        return null;
    } 
}