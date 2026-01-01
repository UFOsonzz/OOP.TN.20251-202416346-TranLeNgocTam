package GUI.controllers;

import GUI.VirusSimulationAppFXML;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 * Controller for Main Menu screen (FXML version)
 */
public class MainMenuController {
    
    @FXML private Button envelopedBtn;
    @FXML private Button nonEnvelopedBtn;
    @FXML private Button helpBtn;
    @FXML private Button quitBtn;
    
    private VirusSimulationAppFXML app;
    
    public void setApp(VirusSimulationAppFXML app) {
        this.app = app;
    }
    
    @FXML
    public void initialize() {
        // Initialization complete
    }
    
    @FXML
    private void handleEnvelopedViruses() {
        app.showVirusSelection(true);
    }
    
    @FXML
    private void handleNonEnvelopedViruses() {
        app.showVirusSelection(false);
    }
    
    @FXML
    private void handleHelp() {
        app.showHelp();
    }
    
    @FXML
    private void handleQuit() {
        app.handleQuit();
    }
    

}
