import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        URL fxmlLocation = getClass().getResource("view.fxml");
        System.out.println("FXML Location: " + fxmlLocation); 

        if (fxmlLocation == null) {
            System.err.println("Ko thấy fxml.");
            return; 
        }
        FXMLLoader loader = new FXMLLoader(fxmlLocation); 
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Date and Time Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}