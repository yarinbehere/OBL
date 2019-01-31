package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.ActivityLog;
import entity.Subscriber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ActivityLogController implements Initializable
{
	 
	  
	  @FXML private TableView<ActivityLog> activityLogTable;
	  @FXML private TableColumn<ActivityLog, String> columnDate;
	  @FXML private TableColumn<ActivityLog, String> columnAction;
	 
	  public static ArrayList<ActivityLog> finalSubscriberActivity;
	  
	  private ObservableList<ActivityLog> listOfActivities;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
    	Subscriber subscriberA = new Subscriber(LoginController.subscriberResult.getSubscriberDetails());
    	MessageCS message = new MessageCS(MessageType.ACTIVITY_LOG,subscriberA);
    	MainClient.client.accept(message);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	for(int i = 0; i < finalSubscriberActivity.size(); i++)
    	{   
    	    System.out.print(finalSubscriberActivity.get(i).getDate());
    	    System.out.println(" ");
    	    System.out.print(finalSubscriberActivity.get(i).getActivity());
    	    System.out.println(" ");
    	}
    	/*
		//columnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		//columnAction.setCellValueFactory(new PropertyValueFactory<>("Action"));
		 
		 */

		//ArrayList<ActivityLog> activities = new ArrayList<>();
		//activities.add(new ActivityLog("123","456"));
		//listOfActivities = FXCollections.observableArrayList(activities);
		//activityLogTable.setItems(listOfActivities);
	}
	
}
