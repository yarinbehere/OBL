package controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class BookReturnController {
	 @FXML private TextField subscriberTextField;

	    @FXML private Button searchSubscriberButton;
	  
	    @FXML private Button helpButton;
	    @FXML private Button returnBook;
	    @FXML private Button searchBookButton;
	    @FXML private Text subscriberStatusLabel;
	    @FXML private Hyperlink subscriberIDLabel;
	    @FXML private DatePicker borrowDate;
	    @FXML private DatePicker returnDate;
	    @FXML private Button returnBookButton;
	    @FXML private DatePicker returnedDate;
	    @FXML private Text lateLabel;
	    
	    @FXML private Text labelPath;
	    @FXML private TextField bookTextField;
	    
	    @FXML
	    void returnBook(ActionEvent event) {
	    	LocalDate date1 = borrowDate.getValue();
	    	LocalDate date2 = returnDate.getValue(); 
	    	System.out.println(ChronoUnit.DAYS.between(date1, date2));
	    }


}
