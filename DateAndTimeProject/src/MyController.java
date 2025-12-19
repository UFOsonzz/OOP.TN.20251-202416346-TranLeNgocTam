import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class MyController implements Initializable {
    private DateTimeModel dateTimeModel;

    @FXML
    private Button myButton;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;
    @FXML
    private TextField myTextField;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myButton.setOnAction(this::currentDate);
        prevButton.setOnAction(this::previousDay);
        nextButton.setOnAction(this::nextDay);
    }

    public MyController() {
        dateTimeModel = new DateTimeModel();
    }
    
    public void currentDate(ActionEvent event) {
        dateTimeModel = new DateTimeModel();
        myTextField.setText(dateTimeModel.getCurrentDateString());
    }
    

    public void previousDay(ActionEvent event) {
        dateTimeModel.DecreaseOneDay();
        myTextField.setText(dateTimeModel.getCurrentDateString());
    }
    
    public void nextDay(ActionEvent event) {
        dateTimeModel.IncreaseOneDay();
        myTextField.setText(dateTimeModel.getCurrentDateString());
    }
    
}