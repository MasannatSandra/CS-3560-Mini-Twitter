package twitterdriver;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import twitterdriver.Visitor.LastUpdatedIDVisitor;
import twitterdriver.Visitor.PositiveVisitor;
import twitterdriver.Visitor.TotalGroupVisitor;
import twitterdriver.Visitor.TotalMessageVisitor;
import twitterdriver.Visitor.TotalUserVisitor;

//GUI of admin panel UI view here
////Admin Panel use the Singeleton Pattern
public class AdminPanel{
    
    private static AdminPanel control;

    public static AdminPanel getInstance(){
        if(control == null){
            control=new AdminPanel();
                }
        
        return control;
    }
    private HBox menuBox;
    
    private AdminPanel(){
        final Image rootPic= new Image("twitterdriver/FolderIcon.png",20, 20, false, false);
        GroupContainer rootGroup=new GroupContainer("Root");
        
        Alert informationAlert=new Alert(Alert.AlertType.INFORMATION);
        
        //Add Treeview for users and group, along with folder icon
        TreeItem<CompositeTree> root=new TreeItem<> (rootGroup, new ImageView(rootPic));
        root.setExpanded(true);
        TreeView<CompositeTree> treeView=new TreeView<>(root);
        
        //Create textfiel and button to add new user
        Button adduser = new Button();
        adduser.setText("Add User");
        TextField UserIDText = new TextField();
        UserIDText.setPromptText("Enter User ID");
        
        //functions for when adduser button is click
        adduser.setOnAction((ActionEvent event) -> {
            TreeItem<CompositeTree> selectedUser = treeView.getSelectionModel().getSelectedItem();
            String newUserInput= UserIDText.getText();
            //Can't add same user to different groups
            if(rootGroup.containsUser(newUserInput)){
                informationAlert.setContentText("This user already belong to a group");
                informationAlert.showAndWait();
            }
            else{
                //set value of when User ID was created
                UserLeaf temp = new UserLeaf(newUserInput);
                //temp.setCreationTime();
                ((GroupContainer) selectedUser.getValue()).addGroupUsers(temp);
                selectedUser.getChildren().add(new TreeItem<>(temp));
            }
            UserIDText.clear();
        });
        //Textfield and button for groups 
        Button addgroup = new Button();
        addgroup.setText("Add Group");
        TextField GroupIDText = new TextField();
        GroupIDText.setPromptText("Enter Group ID");
        
        //addgroup button functions
        addgroup.setOnAction((ActionEvent event) -> {
            String newGroup= GroupIDText.getText();
            GroupContainer temp=new GroupContainer(newGroup);
            TreeItem<CompositeTree> selectedGroup = treeView.getSelectionModel().getSelectedItem();
            //doesn't allow two of the same group
            if(rootGroup.containsGroup(newGroup)){
                informationAlert.setContentText("Can't have two of the same group");
                informationAlert.showAndWait();
            }
            else{
                //set value of when this group ID was created
                temp.setCreationTime();
                ((GroupContainer) selectedGroup.getValue()).addGroupUsers(temp);
                selectedGroup.getChildren().add(new TreeItem<>(temp, new ImageView(rootPic)));
            }
            GroupIDText.clear();
        });
        //Validate IDs
        Button validateIDs=new Button();
        validateIDs.setText("Validate IDs");
        //tell specific reasons for a invalid ID
        validateIDs.setOnAction((ActionEvent event) -> {
            String newGroup= GroupIDText.getText();
            
            String newUser= UserIDText.getText();
            
            //doesn't allow two of the same ID, invalid
            if(rootGroup.containsGroup(newGroup) || rootGroup.containsUser(newUser)){
                informationAlert.setContentText("Invalid, can't have two of the same ID!");
                informationAlert.showAndWait();
            }
            //doesn't allow spaces in IDs, invalid
            else if(newGroup.contains(" ") || newUser.contains(" ")){
                informationAlert.setContentText("Invalid, ID can not contain spaces!");
                informationAlert.showAndWait();
            }
            //if IDs aren't a duplicate, it is valid
            else if(!rootGroup.containsGroup(newGroup) || !rootGroup.containsUser(newUser)){
                informationAlert.setContentText("Valid, the ID has not been used!");
                informationAlert.showAndWait();
            }
        });
        //Display ID of the last updated user
        Button LastUpdatedUser=new Button("Last Updated User's ID");
        LastUpdatedUser.setOnAction((ActionEvent event) -> {
            LastUpdatedIDVisitor updatedIDVisitor=new LastUpdatedIDVisitor();
            rootGroup.accept(updatedIDVisitor);
            informationAlert.setContentText("User's last updated: " 
                    + updatedIDVisitor.getLastUpdateUser()); 
            informationAlert.showAndWait();
        });

        //Open User View button
        Button userView = new Button();
        userView.setText("Open User View");
        
        //Open the selected user's view when clicked
        userView.setOnAction((ActionEvent event) -> {
            TreeItem<CompositeTree> selectedUser = treeView.getSelectionModel().getSelectedItem();
            if (selectedUser.getValue() instanceof UserLeaf){
                UserLeaf userViewUser = (UserLeaf) selectedUser.getValue();
                UserViewUI.openUserUI(userViewUser, rootGroup);
            }
        });
        //Total user button
        Button usertotal = new Button();
        usertotal.setText("Show User Total");
        
        //Call usertotal function when button is clicked and a alert popup
        usertotal.setOnAction((ActionEvent event) -> {
            TotalUserVisitor userTotalVisitor=new TotalUserVisitor();
            rootGroup.accept(userTotalVisitor);
            informationAlert.setContentText("There are " + userTotalVisitor.getUserTotal()
                    +" users total");
            informationAlert.showAndWait();
        });
        //Button for group total
        Button grouptotal = new Button();
        grouptotal.setText("Show Group Total"); 
        
        //Call group total function when clicked to display
        grouptotal.setOnAction((ActionEvent event) -> {
            TotalGroupVisitor groupTotalVisitor=new TotalGroupVisitor();
            rootGroup.accept(groupTotalVisitor);
            informationAlert.setContentText("There are " + groupTotalVisitor.getGroupTotal()
                    + " groups total");
            informationAlert.showAndWait();
            
        });
        //Button for total messages aka tweets
        Button messagetotal = new Button();
        messagetotal.setText("Show Message Total");
        
        //Call message total function from visitors class to display total
        messagetotal.setOnAction((ActionEvent event) -> {
            TotalMessageVisitor messageTotalVisitor=new TotalMessageVisitor();
            rootGroup.accept(messageTotalVisitor);
            informationAlert.setContentText("There are " + messageTotalVisitor.getMessageTotal()
                    + " tweets total");
            informationAlert.showAndWait();
        });
        //Positive Percentage button
        Button positive = new Button();
        positive.setText("Show Positive Percentage");
        //Call positive percentages of tweet function then display
        positive.setOnAction((ActionEvent event)-> {
            PositiveVisitor positiveVisitor = new PositiveVisitor();
            rootGroup.accept(positiveVisitor);
            informationAlert.setContentText(String.format("There are %.2f percent"
                    + " of positive messages total" , positiveVisitor.getPositivePercentage()));
            
            informationAlert.showAndWait();
        });
        //Organize buttons, textfield, and treeview into a UI display for Admin Panel
        VBox treeBox=new VBox(treeView);
        HBox userBox = new HBox(10, UserIDText, adduser);
        HBox groupBox = new HBox(10, GroupIDText, addgroup);
        HBox UserGroupBox = new HBox(10, usertotal, grouptotal);
        HBox MessagePositiveBox = new HBox(10, messagetotal, positive);
        
        VBox UserButtons=new VBox(10, validateIDs, LastUpdatedUser, userView);
        UserButtons.setAlignment(Pos.CENTER);
        
        VBox topButtons = new VBox(10, userBox, groupBox, UserButtons, UserGroupBox, 
                MessagePositiveBox);
        VBox bottomButtons=new VBox(10, UserGroupBox, MessagePositiveBox);
        VBox allButtons=new VBox(10, topButtons, bottomButtons);
        
        allButtons.setSpacing(170);
        menuBox = new HBox(10, treeBox, allButtons);
        menuBox.setPadding(new Insets(10));

    }
    //Display the above functions when called
    public HBox getAdminPanel() {
            return menuBox;
    }
}
