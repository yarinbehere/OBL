package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import boundary.LoadGUI;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.ActivityLog;
import entity.Subscriber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ActivityLogController implements Initializable
{
	@FXML private Button returnButton;
	@FXML private TableView<ActivityLog> activityLogTable;
	@FXML private TableColumn<ActivityLog, String> columnDate;
	@FXML private TableColumn<ActivityLog, String> columnAction;
	public static ArrayList<ActivityLog> finalSubscriberActivity;
	private ObservableList<ActivityLog> listOfActivities;
	
	 /**
     * When the subscriber wants to see his activity log.
     * Accessible subscriber the librarian menu.
     * (action made by subscriber)
     * @author Hai
     * 
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		
		Subscriber subscriberA = new Subscriber(LoginController.subscriberResult.getSubscriberDetails());
		MessageCS message = new MessageCS(MessageType.ACTIVITY_LOG,subscriberA);
		MainClient.client.accept(message);
		try {
			// TODO Auto-generated catch block
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//if the subscriber has no activities at all
		if(finalSubscriberActivity.size()==0)
		{
			Alert alert1=new Alert(Alert.AlertType.INFORMATION);
			alert1.setTitle("Activities");
			alert1.setContentText("You dont have any activities");
			alert1.showAndWait();
			return;
		}
		//if the subscriber has activities
		else 
		{
			columnDate.setCellValueFactory(new PropertyValueFactory<ActivityLog,String>("Date"));
			columnAction.setCellValueFactory(new PropertyValueFactory<ActivityLog,String>("Activity"));
			listOfActivities = FXCollections.observableArrayList(finalSubscriberActivity);
			activityLogTable.setItems(listOfActivities);
		}
	}
	
	/**
	 * Return to Subscriber Menu
	 * @param event
	 * @throws IOException
	 */
	 @FXML
	 public void returnAction(ActionEvent event) throws IOException {

				LoadGUI.loadFXML("SubscriberMenu.fxml",returnButton);
	 }

}
