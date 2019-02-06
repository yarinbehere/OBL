package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.activity.ActivityRequiredException;
import javax.naming.spi.InitialContextFactory;

import boundary.LoadGUI;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import entity.FileTransfer;
import entity.GenerateReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.control.TableView;

public class GenerateReportController implements Initializable{
	/* Menu Bar */
    @FXML private MenuItem mainmenuMenuBar;
    @FXML private MenuItem logoutMenuBar;
    @FXML private MenuItem searchbookMenuBar;
    @FXML private MenuItem borrowbookMenuBar;
    @FXML private MenuItem returnbookMenuBar;
    @FXML private MenuItem addnewbookMenuBar;
    @FXML private MenuItem updatebookMenuBar;
    @FXML private MenuItem deletebookMenuBar;
    @FXML private MenuItem createsubscriberMenuBar;
    @FXML private MenuItem reviewsubscriberMenuBar;
    @FXML private MenuItem generatereportMenuBar;
    @FXML private MenuItem viewdatabaseMenuBar;
    @FXML private MenuItem aboutoblMenuBar;
   
    @FXML private Button returnButton;
    @FXML private Text pathLabel;
    /* Activity Report */
    @FXML private TableView<GenerateReport> activityTableView;
    @FXML private TableColumn<GenerateReport, LocalDate> tableColumnDate;
    @FXML private TableColumn<GenerateReport, Integer> tableColumnActive;
    @FXML private TableColumn<GenerateReport, Integer> tableColumnFrozen;
    @FXML private TableColumn<GenerateReport, Integer> tableColumnLocked;
    @FXML private TableColumn<GenerateReport, Integer> tableColumnBooks;
    @FXML private TableColumn<GenerateReport, Integer> tableColumnLates;
    /* Borrow Report */
    @FXML private BarChart<String, String> borrowsBarChart;
    @FXML private TextField averageNormalTextField;
    @FXML private TextField wantedNormalTextField;
    @FXML private TextField medianNormalTextField;
    /* Late Returns Report */
    @FXML private BarChart<String, String> lateBarChart;
    @FXML private TextField medianLateTextField;
    @FXML private TextField averageLateTextField;
    
    //public static ArrayList<Book> dateResults;
	public static GenerateReport reportActivityResult;
	public static GenerateReport reportBorrowResult;
    private ObservableList<GenerateReport> listOfReports;
    private ArrayList<GenerateReport> tempReports = new ArrayList<>();
    
    //initialize the ChoiceBox and TableView
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		//initialize the TableView
		tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		tableColumnActive.setCellValueFactory(new PropertyValueFactory<>("Active"));
		tableColumnFrozen.setCellValueFactory(new PropertyValueFactory<>("Frozen"));
		tableColumnLocked.setCellValueFactory(new PropertyValueFactory<>("Locked"));
		tableColumnBooks.setCellValueFactory(new PropertyValueFactory<>("Books"));
		tableColumnLates.setCellValueFactory(new PropertyValueFactory<>("Lates"));
		GenerateReport activityReport = null;
		GenerateReport borrowReport=null;
		MessageCS message = new MessageCS(MessageType.ACTIVITY_REPORT, activityReport,borrowReport);
		MainClient.client.accept(message);
		try {
			Thread.sleep(1500);
			tempReports.add(reportActivityResult);
			listOfReports = FXCollections.observableArrayList(tempReports);//insert those items first in the collection
			activityTableView.setItems(listOfReports);
			
		//	averageNormalTextField.setText(message.getBorrowReport().getNormalAvg() + "");
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
    
    /**
	 * Return to Manager Menu
	 * @param event
	 * @throws IOException
	 */
	 @FXML
	 public void returnAction(ActionEvent event) throws IOException {

	    		LoadGUI.loadFXML("ManagerMenu.fxml",returnButton);
	 }
    
}
