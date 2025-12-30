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
        this.repository = VirusFactory.createVirusRepository();
        updateUI();
    }
    
    @FXML
    public void initialize() {
        setupButtonEffects();
    }
    
    private void updateUI() {
        String virusType = isEnveloped ? "ENVELOPED VIRUSES" : "NON-ENVELOPED VIRUSES";
        String emoji = isEnveloped ? "🔴" : "🔵";
        
        titleLabel.setText(emoji + " " + virusType);
        titleLabel.setTextFill(isEnveloped ? Color.web("#2e7d32") : Color.web("#1565c0"));
        
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
        
        VBox content = new VBox(8);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(15));
        
        // Virus name
        Label nameLabel = new Label(getVirusEmoji(virus.getName()) + " " + virus.getName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        nameLabel.setTextFill(Color.WHITE);
        
        // Virus details
        Label detailsLabel = new Label(getVirusDetails(virus));
        detailsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        detailsLabel.setTextFill(Color.web("#e0e0e0"));
        detailsLabel.setWrapText(true);
        
        content.getChildren().addAll(nameLabel, detailsLabel);
        button.setGraphic(content);
        
        String color = getVirusColor(virus.getName());
        button.setPrefWidth(600);
        button.setPrefHeight(100);
        button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 12; " +
                       "-fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 8, 0, 0, 4);");
        
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 12; " +
                           "-fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 12, 0, 0, 6); " +
                           "-fx-scale-x: 1.03; -fx-scale-y: 1.03;");
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 12; " +
                           "-fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 8, 0, 0, 4);");
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
            backBtn.setStyle("-fx-background-color: #455a64; -fx-text-fill: white; " +
                           "-fx-background-radius: 10; -fx-cursor: hand;"));
        backBtn.setOnMouseExited(e -> 
            backBtn.setStyle("-fx-background-color: #607d8b; -fx-text-fill: white; " +
                           "-fx-background-radius: 10; -fx-cursor: hand;"));
    }
    
    private String getVirusEmoji(String virusName) {
        switch (virusName) {
            case "HIV": return "🔴";
            case "SARS-CoV-2": return "🦠";
            case "AdenoVirus": return "🔷";
            case "PolioVirus": return "🔵";
            default: return "🦠";
        }
    }
    
    private String getVirusColor(String virusName) {
        switch (virusName) {
            case "HIV": return "#c62828";
            case "SARS-CoV-2": return "#6a1b9a";
            case "AdenoVirus": return "#1565c0";
            case "PolioVirus": return "#2e7d32";
            default: return "#424242";
        }
    }
    
    private String getVirusDetails(Virus virus) {
        StringBuilder details = new StringBuilder();
        details.append("Nucleic Acid: ").append(virus.getNucleicAcid().getType());
        details.append(" | Capsid: ").append(virus.getCapsid().getShape());
        
        if (virus instanceof EnvelopedVirus) {
            EnvelopedVirus envVirus = (EnvelopedVirus) virus;
            details.append(" | Glycoproteins: ");
            List<Glycoprotein> gps = envVirus.getLipidEnvelop().getGlycoproteins();
            for (int i = 0; i < gps.size(); i++) {
                details.append(gps.get(i).getName());
                if (i < gps.size() - 1) details.append(", ");
            }
        }
        
        return details.toString();
    }
}
