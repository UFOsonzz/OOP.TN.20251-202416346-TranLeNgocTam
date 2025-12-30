package GUI.controllers;

import GUI.VirusSimulationAppFXML;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
        setupButtonEffects();
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
    
    private void setupButtonEffects() {
        // Enveloped button hover effects
        envelopedBtn.setOnMouseEntered(e -> 
            envelopedBtn.setStyle("-fx-background-color: #4caf50; -fx-background-radius: 15; " +
                                 "-fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 15, 0, 0, 8); " +
                                 "-fx-scale-x: 1.05; -fx-scale-y: 1.05;"));
        envelopedBtn.setOnMouseExited(e -> 
            envelopedBtn.setStyle("-fx-background-color: #4caf50; -fx-background-radius: 15; " +
                                 "-fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);"));
        
        // Non-enveloped button hover effects
        nonEnvelopedBtn.setOnMouseEntered(e -> 
            nonEnvelopedBtn.setStyle("-fx-background-color: #2196f3; -fx-background-radius: 15; " +
                                    "-fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 15, 0, 0, 8); " +
                                    "-fx-scale-x: 1.05; -fx-scale-y: 1.05;"));
        nonEnvelopedBtn.setOnMouseExited(e -> 
            nonEnvelopedBtn.setStyle("-fx-background-color: #2196f3; -fx-background-radius: 15; " +
                                    "-fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);"));
        
        // Help button hover effects
        helpBtn.setOnMouseEntered(e -> 
            helpBtn.setStyle("-fx-background-color: #f57c00; -fx-text-fill: white; " +
                            "-fx-background-radius: 10; -fx-cursor: hand; -fx-scale-x: 1.05; -fx-scale-y: 1.05;"));
        helpBtn.setOnMouseExited(e -> 
            helpBtn.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white; " +
                            "-fx-background-radius: 10; -fx-cursor: hand;"));
        
        // Quit button hover effects
        quitBtn.setOnMouseEntered(e -> 
            quitBtn.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; " +
                            "-fx-background-radius: 10; -fx-cursor: hand; -fx-scale-x: 1.05; -fx-scale-y: 1.05;"));
        quitBtn.setOnMouseExited(e -> 
            quitBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; " +
                            "-fx-background-radius: 10; -fx-cursor: hand;"));
    }
}
