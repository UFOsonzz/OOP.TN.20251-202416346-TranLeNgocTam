package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpDialog {
    private final Stage dialogStage;

    public HelpDialog(Stage owner) {
        dialogStage = new Stage();
        dialogStage.initOwner(owner);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Help - Virus Infection Simulation");

        Label titleLabel = new Label("Help & Information");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

        Label infoLabel = new Label(
            "INSTRUCTIONS:\n" +
            "- Select the virus type (Enveloped or Non-enveloped) from the main menu.\n" +
            "- Choose a specific virus to view its structure and infection process.\n" +
            "- Click 'Start' to see a step-by-step simulation.\n" +
            "- Use the Back button to return to the main menu.\n\n" +
            "GENERAL BACKGROUND:\n" +
            "Viruses are microscopic pathogens that cause diseases.\n" +
            "All viruses have nucleic acid (DNA or RNA) and a capsid (protein shell).\n\n" +
            "NON-ENVELOPED VIRUSES:\n" +
            "- Structure: Nucleic acid + capsid (no lipid envelope).\n" +
            "- Infection: Attach to host cell, dissolve capsid, and release nucleic acid.\n" +
            "- Examples: Adenovirus, Poliovirus.\n\n" +
            "ENVELOPED VIRUSES:\n" +
            "- Structure: Nucleic acid + capsid + lipid envelope with glycoproteins.\n" +
            "- Infection: Use glycoproteins to attach to specific receptors (lock-key mechanism).\n" +
            "- Examples: HIV, COVID-19 (SARS-CoV-2)."
        );
        infoLabel.setWrapText(true);
        infoLabel.setStyle("-fx-font-size: 12px;");

        Button closeBtn = new Button("Close");
        closeBtn.setStyle("-fx-background-color: white; -fx-border-color: #2196F3; -fx-border-width: 1; -fx-font-size: 12px;");
        closeBtn.setPrefWidth(80);
        closeBtn.setPrefHeight(28);
        closeBtn.setOnAction(e -> dialogStage.close());

        VBox root = new VBox(15, titleLabel, infoLabel, closeBtn);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");
        root.setMinWidth(400);
        root.setAlignment(javafx.geometry.Pos.CENTER);

        dialogStage.setScene(new Scene(root));
    }

    public void show() {
        dialogStage.showAndWait();
    }
}
