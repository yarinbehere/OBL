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
import entity.Librarian;
import entity.Subscriber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**view database in table of Subscriber,Librarian and Manager
 * @author omri braymok
 *
 */


public class ViewDatabaseController implements Initializable{
	@FXML private TextField searchUserTextField;
	
	@FXML private CheckBox managerCheckBox;
	@FXML private CheckBox librarianCheckBox;
	@FXML private CheckBox subscriberCheckBox;
	
	@FXML private TableView<Subscriber> subscribersTable;
	@FXML private TableColumn<Subscriber, String> tableColumnSubscribersID;
	@FXML private TableColumn<Subscriber, String> tableColumnSubscribersUserName;
	@FXML private TableColumn<Subscriber, String> tableColumnSubscribersFirstName;
	@FXML private TableColumn<Subscriber, String> tableColumnSubscribersLastName;
	@FXML private TableColumn<Subscriber, String> tableColumnSubscribersPhone;
	@FXML private TableColumn<Subscriber, String> tableColumnSubscribersEmail;
	@FXML private TableColumn<Subscriber, String> tableColumnSubscribersStatus;
	
	@FXML private TableView<Librarian> librariansTable;
	@FXML private TableColumn<Librarian, String> tableColumnLibrariansNumber;
	@FXML private TableColumn<Librarian, String> tableColumnLibrariansUserName;
	@FXML private TableColumn<Librarian, String> tableColumnLibrariansFirstName;
	@FXML private TableColumn<Librarian, String> tableColumnLibrariansLastName;
	@FXML private TableColumn<Librarian, String> tableColumnLibrariansPhone;
	@FXML private TableColumn<Librarian, String> tableColumnLibrariansEmail;
	
	@FXML private TableView<Librarian> managersTable;
	@FXML private TableColumn<Librarian, String> tableColumnManagersNumber;
	@FXML private TableColumn<Librarian, String> tableColumnManagersUserName;
	@FXML private TableColumn<Librarian, String> tableColumnManagersFirstName;
	@FXML private TableColumn<Librarian, String> tableColumnManagersLastName;
	@FXML private TableColumn<Librarian, String> tableColumnManagersPhone;
	@FXML private TableColumn<Librarian, String> tableColumnManagersEmail;
	
	
	public static ArrayList<Subscriber> subscriberResult;
	public static ArrayList<Librarian> librarianResult;
	public static ArrayList<Librarian> managersResult = new  ArrayList<Librarian>();
	
	private ObservableList<Subscriber> listOfSubscribers;
	private ObservableList<Librarian> listOfLibrarians;
	private ObservableList<Librarian> listOfManagers;
	
	//initialize the TableView
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	//send message to server to get details
		MessageCS message = new MessageCS(MessageType.SEARCH_ALL_FOR_VIEW_DATABASE);
    	MainClient.client.accept(message);
    	try {
			Thread.sleep(400);
			//Separates managers and librarians
			for(int i =0; i<ViewDatabaseController.librarianResult.size();i++)
			{
				if(librarianResult.get(i).getRole().equals("Library Director")) 
				{
					managersResult.add(ViewDatabaseController.librarianResult.get(i));
					ViewDatabaseController.librarianResult.remove(i);
				}
			}
		
			//initialize the TableView of Subscribers
			tableColumnSubscribersID.setCellValueFactory(new PropertyValueFactory<>("subscriberID"));
			tableColumnSubscribersUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
			tableColumnSubscribersFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
			tableColumnSubscribersLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
			tableColumnSubscribersPhone.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
			tableColumnSubscribersEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
			tableColumnSubscribersStatus.setCellValueFactory(new PropertyValueFactory<>("subscriberStatus"));
			listOfSubscribers = FXCollections.observableArrayList(subscriberResult);//insert those items first in the collection
			subscribersTable.setItems(listOfSubscribers);
			subscribersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
			
			//initialize the TableView of Librarians
			tableColumnLibrariansNumber.setCellValueFactory(new PropertyValueFactory<>("workerNumber"));
			tableColumnLibrariansUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
			tableColumnLibrariansFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
			tableColumnLibrariansLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
			tableColumnLibrariansPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
			tableColumnLibrariansEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
			listOfLibrarians = FXCollections.observableArrayList(librarianResult);//insert those items first in the collection
			librariansTable.setItems(listOfLibrarians);
			librariansTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			//initialize the TableView of Managers
			tableColumnManagersNumber.setCellValueFactory(new PropertyValueFactory<>("workerNumber"));
			tableColumnManagersUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
			tableColumnManagersFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
			tableColumnManagersLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
			tableColumnManagersPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
			tableColumnManagersEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
			listOfManagers = FXCollections.observableArrayList(managersResult);//insert those items first in the collection
			managersTable.setItems(listOfManagers);
			managersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
