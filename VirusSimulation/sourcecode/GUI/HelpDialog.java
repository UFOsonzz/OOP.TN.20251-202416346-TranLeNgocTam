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

        Label titleLabel = new Label("📖 HELP & INFORMATION");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1565c0;");

        Label infoLabel = new Label(
            "INSTRUCTIONS:\n" +
            "- Select the virus type (Enveloped or Non-enveloped) from the main menu.\n" +
            "- Choose a specific virus to view its structure and infection process.\n" +
            "- Click 'Start Infection' to see a step-by-step simulation.\n" +
            "- Use the Back button to return to the main menu.\n" +
            "- Use Quit to exit the application (confirmation required).\n\n" +
            "GENERAL BACKGROUND:\n" +
            "Viruses are microscopic pathogens that cause diseases in humans, animals, and plants.\n" +
            "All viruses have two essential components: nucleic acid (DNA or RNA) and a capsid (protein shell).\n" +
            "Viruses are divided into two main categories based on their structure and infection mechanism:\n\n" +
            "NON-ENVELOPED VIRUSES:\n" +
            "- Structure: Nucleic acid + capsid (no lipid envelope).\n" +
            "- Infection: Attach to host cell, dissolve capsid, and release nucleic acid directly into the cell.\n" +
            "- Examples: Adenovirus, Poliovirus.\n\n" +
            "ENVELOPED VIRUSES:\n" +
            "- Structure: Nucleic acid + capsid + lipid envelope with glycoproteins.\n" +
            "- Infection: Use glycoproteins to attach to specific receptors on the host cell (lock–key mechanism), then inject nucleic acid.\n" +
            "- Examples: HIV, COVID-19 (SARS-CoV-2).\n\n" +
            "PROJECT FOCUS:\n" +
            "- Demonstrates OOP concepts: encapsulation, inheritance, polymorphism.\n" +
            "- Animations illustrate the unique infection mechanisms of each virus type.\n\n" +
            "Authors: Group 1\n2025"
        );
        infoLabel.setWrapText(true);
        infoLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #333;");

        Button closeBtn = new Button("Close");
        closeBtn.setStyle("-fx-background-color: #1976d2; -fx-text-fill: white; -fx-font-size: 15px; -fx-background-radius: 8;");
        closeBtn.setOnAction(e -> dialogStage.close());

        VBox root = new VBox(20, titleLabel, infoLabel, closeBtn);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #e3f2fd; -fx-border-radius: 10; -fx-background-radius: 10;");
        root.setMinWidth(400);
        root.setMinHeight(300);
        root.setSpacing(20);
        root.setAlignment(javafx.geometry.Pos.CENTER);

        dialogStage.setScene(new Scene(root));
    }

    public void show() {
        dialogStage.showAndWait();
    }
}
