package GUI.controllers;

import Domain.Virus.*;
import GUI.VirusSimulationAppFXML;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

/**
 * Controller for Virus Selection screen (FXML version)
 */
public class VirusSelectionController {
    
    @FXML private Label titleLabel;
    @FXML private Label descLabel;
    @FXML private VBox virusListContainer;
    @FXML private Button backBtn;
    
    private VirusSimulationAppFXML app;
    private boolean isEnveloped;
    private VirusRepository repository;
    
    public void setApp(VirusSimulationAppFXML app) {
        this.app = app;
    }
    
    public void setVirusType(boolean isEnveloped) {
        this.isEnveloped = isEnveloped;
        this.repository = VirusRepository.getInstance();
        updateUI();
    }
    
    @FXML
    public void initialize() {
        setupButtonEffects();
    }
    
    private void updateUI() {
        String virusType = isEnveloped ? "Enveloped Viruses" : "Non-Enveloped Viruses";
        
        titleLabel.setText(virusType);
        
        if (isEnveloped) {
            descLabel.setText("These viruses have a lipid envelope with glycoproteins. They use a lock-key mechanism " +
                             "where glycoproteins (keys) bind to specific receptors (locks) on the host cell membrane.");
        } else {
            descLabel.setText("These viruses lack a lipid envelope. They attach directly to the host cell and dissolve " +
                             "their capsid to release nucleic acid directly into the cell.");
        }
        
        loadVirusList();
    }
    
    private void loadVirusList() {
        virusListContainer.getChildren().clear();
        
        List<Virus> viruses = isEnveloped ? 
            repository.getEnvelopedViruses() : 
            repository.getNonEnvelopedViruses();
        
        for (Virus virus : viruses) {
            Button virusBtn = createVirusButton(virus);
            virusListContainer.getChildren().add(virusBtn);
        }
    }
    
    private Button createVirusButton(Virus virus) {
        Button button = new Button();
        
        VBox content = new VBox(5);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(10));
        
        // Virus name
        Label nameLabel = new Label(virus.getName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameLabel.setTextFill(Color.web("#2196F3"));
        
        // Virus details
        Label detailsLabel = new Label(getVirusDetails(virus));
        detailsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
        detailsLabel.setTextFill(Color.web("#666666"));
        detailsLabel.setWrapText(true);
        
        content.getChildren().addAll(nameLabel, detailsLabel);
        button.setGraphic(content);
        
        button.setPrefWidth(500);
        button.setPrefHeight(70);
        button.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; " +
                       "-fx-border-width: 1; -fx-cursor: hand;");
        
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: white; -fx-border-color: #2196F3; " +
                           "-fx-border-width: 2; -fx-cursor: hand;");
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; " +
                           "-fx-border-width: 1; -fx-cursor: hand;");
        });
        
        button.setOnAction(e -> app.showInfectionSimulation(virus.getName()));
        
        return button;
    }
    
    @FXML
    private void handleBack() {
        app.showMainMenu();
    }
    
    private void setupButtonEffects() {
        backBtn.setOnMouseEntered(e -> 
            backBtn.setStyle("-fx-background-color: white; -fx-border-color: #2196F3; " +
                           "-fx-border-width: 2; -fx-cursor: hand;"));
        backBtn.setOnMouseExited(e -> 
            backBtn.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; " +
                           "-fx-border-width: 1;"));
    }
    
    private String getVirusDetails(Virus virus) {
        String details = "";
        details += "Nucleic Acid: " + virus.getNucleicAcid().getType();
        details += " | Capsid: " + virus.getCapsid().getShape();
        
        if (virus instanceof EnvelopedVirus) {
            EnvelopedVirus envVirus = (EnvelopedVirus) virus;
            details += " | Glycoproteins: ";
            List<Glycoprotein> gps = envVirus.getLipidEnvelop().getGlycoproteins();
            for (int i = 0; i < gps.size(); i++) {
                details += gps.get(i).getName();
                if (i < gps.size() - 1) details += ", ";
            }
        }
        
        return details;
    }
}
