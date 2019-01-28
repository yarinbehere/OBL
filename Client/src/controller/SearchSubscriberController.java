/**
 * 
 */
package controller;



import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Subscriber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * @author omri braymok
 *
 */
public class SearchSubscriberController implements Initializable{
	@FXML private TextField usernameTextField;
	@FXML private TextField idTextField;
	@FXML private TextField firstnameTextField;
	@FXML private TextField emailTextField;
	@FXML private TextField phoneTextField;
	@FXML private TextField lastnameTextField;
	
	@FXML private ChoiceBox<String> phoneChoiceBox;
	
	@FXML private Button selectSubscriberButton;
	
	@FXML private ListView<String> resultsListView;
	
	public static ArrayList<Subscriber> arrSubscriber=null;
	
    //initialize the choicebox with genre options
	@Override
	public void initialize(URL location, ResourceBundle resources) {
    	ObservableList<String> subjectList = FXCollections.observableArrayList
				("50","52","53","54","55","58");
    	phoneChoiceBox.setValue("50");//make the first value chosen as "Action"
    	phoneChoiceBox.setItems(subjectList);
		//initialize the TableView
    }

	@FXML
	public void searchButtonAction() throws InterruptedException {
		String userName = usernameTextField.getText();
		String ID = idTextField.getText();
		String firstName = firstnameTextField.getText();
		String email = emailTextField.getText();
		String pre_phone = phoneChoiceBox.getValue();
		String phone = phoneTextField.getText();
		String lastName = lastnameTextField.getText();
		
		String userPhone = pre_phone+phone;

		Subscriber subscriber = new Subscriber(userName, ID, firstName, email, userPhone, lastName, null);
		MessageCS message = new MessageCS(MessageType.SEARCH_SUBSCRIBER,subscriber);
    	MainClient.client.accept(message);
    //	new ThreadTest.ThreadTest2(message).run();
    	Thread.sleep(100);
    	
    	
    	
    	if (arrSubscriber.isEmpty())  
    			resultsListView.getItems().add("The subscriber is not found");	
    	else
    		{
    		int i=0;
    		while(i<arrSubscriber.size())
    			{
    			 String result=arrSubscriber.get(i).getUsername()+" "+arrSubscriber.get(i).getId()+" "+arrSubscriber.get(i).getFirstname()+
    					" "+arrSubscriber.get(i).getLastname()+" "+arrSubscriber.get(i).getPhone()+
    					" "+arrSubscriber.get(i).getEmail()+" "+arrSubscriber.get(i).getStatus()+"\n";
    			resultsListView.getItems().add(result);
    			i++;
    			}//end while
    		}//end else
    	
    	resultsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	

    	


	}

	/**
	Set selectSusbcriberButton disable to be false if subscriber was selected
	*/
	@FXML
	void selectSubscriberAction(MouseEvent e) {
		ObservableList<String> result;
		result=resultsListView.getSelectionModel().getSelectedItems();
		String msg="The subscriber is not found";
		String resultMsg="";
		for(String s:result) {
			resultMsg +=s;
		}
		if(!resultMsg.equals(msg))
		selectSubscriberButton.setDisable(false);
	}
	
	/**
	Temporary print the select subscriber
	*/
	@FXML
	public void selectSubscriberButtonAction() {
		String msg="";
		ObservableList<String> subscribers;
		subscribers=resultsListView.getSelectionModel().getSelectedItems();
		for(String s:subscribers) {
			msg +=s+"\n";
		}
		System.out.println(msg);
	}
	
	/**
	Clears all TextFields and turns the selectSusbcriberButton to disable
	 */
	@FXML
	public void clearButtonAction() {
		usernameTextField.setText("");
		idTextField.setText("");
		firstnameTextField.setText("");
		emailTextField.setText("");
		phoneChoiceBox.getItems().clear();
		phoneTextField.setText("");
		lastnameTextField.setText("");
		resultsListView.getItems().clear();
		selectSubscriberButton.setDisable(true);
		
	}

}
