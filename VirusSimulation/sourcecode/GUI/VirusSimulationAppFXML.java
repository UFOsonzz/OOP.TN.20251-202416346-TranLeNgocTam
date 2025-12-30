package GUI;

import GUI.controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main JavaFX Application using FXML
 * Demonstrates virus infection simulation with FXML-based GUI
 */
public class VirusSimulationAppFXML extends Application {
    
    private static final String APP_TITLE = "Virus Infection Simulation (FXML)";
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setResizable(false);
        
        // Show main menu
        showMainMenu();
        
        primaryStage.show();
        
        // Confirm before exit
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            handleQuit();
        });
    }
    
    /**
     * Shows the main menu screen
     */
    public void showMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MainMenu.fxml"));
            Parent root = loader.load();
            
            MainMenuController controller = loader.getController();
            controller.setApp(this);
            
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            primaryStage.setScene(scene);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading MainMenu.fxml");
        }
    }
    
    /**
     * Shows the virus selection screen
     * @param enveloped true for enveloped viruses, false for non-enveloped
     */
    public void showVirusSelection(boolean enveloped) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/VirusSelection.fxml"));
            Parent root = loader.load();
            
            VirusSelectionController controller = loader.getController();
            controller.setApp(this);
            controller.setVirusType(enveloped);
            
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            primaryStage.setScene(scene);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading VirusSelection.fxml");
        }
    }
    
    /**
     * Shows the infection simulation screen
     * @param virusName name of the virus to simulate
     */
    public void showInfectionSimulation(String virusName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/InfectionSimulation.fxml"));
            Parent root = loader.load();
            
            InfectionSimulationController controller = loader.getController();
            controller.setApp(this);
            controller.setVirusName(virusName);
            
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            primaryStage.setScene(scene);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading InfectionSimulation.fxml");
        }
    }
    
    /**
     * Shows the help dialog
     */
    public void showHelp() {
        HelpDialog helpDialog = new HelpDialog(primaryStage);
        helpDialog.show();
    }
    
    /**
     * Handles quit action with confirmation
     */
    public void handleQuit() {
        QuitConfirmationDialog quitDialog = new QuitConfirmationDialog(primaryStage);
        if (quitDialog.showAndWait()) {
            primaryStage.close();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
