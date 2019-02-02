package controller;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import entity.FileTransfer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;


/**
 * Generate Report: Manager get various of reports about the system.
 * Accessible through the main menu.
 * @author Yarin
 */

public class GenerateReportController {
	@FXML private Button helpButton;
	@FXML private Button returnButton;
	@FXML private Text pathLabel;
	/* Activity Report */
	@FXML private TextField activityOutputTextField;
	@FXML private Button activityOpenFolderButton;
	@FXML private Button activityGenerateButton;
	@FXML private CheckBox totalSubscribersCheckBox;
	@FXML private CheckBox activeSubscribersCheckBox;
	@FXML private CheckBox frozenSubscribersCheckBox;
	@FXML private CheckBox lockedSubscribersCheckBox;
	@FXML private CheckBox totalBooksCheckBox;
	@FXML private CheckBox borrowedBooksCheckBox;
	@FXML private CheckBox lateReturnersCheckBox;
	/* Borrows Report */
	@FXML private TextField borrowsOutputTextField;
	@FXML private Button borrowsOpenFolderButton;
	@FXML private Button borrowsGenerateButton;
	@FXML private CheckBox totalBorrowBooksCheckBox;
	@FXML private CheckBox normalBorrowBookCheckBox;
	@FXML private CheckBox wantedBorrowBookCheckBox;
	/* Returns Report */
	@FXML private TextField returnsOutputTextField;
	@FXML private Button returnsOpenFolderButton;
	@FXML private Button returnsGenerateButton;
	@FXML private CheckBox totalReturnsBooksCheckBox;
	@FXML private CheckBox lateReturnsBooksCheckBox;
	@FXML private CheckBox returnsPerBookCheckBox;
	@FXML private CheckBox lateReturnsPerBookCheckBox;
	
	public static String resultActivityReport;
	
	/*
	 * Generate Activity Report
	 */
	@FXML
	void generateActivityReport(ActionEvent event) throws InterruptedException{
		System.out.println(resultActivityReport);
		String output="";
		MessageCS message = new MessageCS(MessageType.GENERATE_ACTIVITY_REPORT,output);
		MainClient.client.accept(message);
		Thread.sleep(100);
		
		System.out.println(resultActivityReport);
	}
	
}
