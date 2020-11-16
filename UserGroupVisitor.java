
package twitterdriver;
//Inferface for all the 4 children visitor classes in visitor patterns
public interface UserGroupVisitor {
    public void visitUser(UserLeaf user);
    
    public void visitGroup(GroupContainer group);
    
}
