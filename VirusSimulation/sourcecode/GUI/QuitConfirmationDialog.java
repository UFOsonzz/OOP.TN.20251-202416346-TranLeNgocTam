package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class QuitConfirmationDialog {
    private final Stage dialogStage;
    private boolean confirmed = false;

    public QuitConfirmationDialog(Stage owner) {
        dialogStage = new Stage();
        dialogStage.initOwner(owner);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Confirm Quit");

        Label label = new Label("Are you sure you want to quit?");
        label.setStyle("-fx-font-size: 14px;");

        Button yesBtn = new Button("Yes");
        yesBtn.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-font-size: 12px;");
        yesBtn.setPrefWidth(80);
        yesBtn.setPrefHeight(28);
        yesBtn.setOnAction(e -> { confirmed = true; dialogStage.close(); });

        Button noBtn = new Button("No");
        noBtn.setStyle("-fx-background-color: white; -fx-border-color: #2196F3; -fx-border-width: 1; -fx-font-size: 12px;");
        noBtn.setPrefWidth(80);
        noBtn.setPrefHeight(28);
        noBtn.setOnAction(e -> { confirmed = false; dialogStage.close(); });

        HBox buttonBox = new HBox(10, yesBtn, noBtn);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);

        VBox root = new VBox(15, label, buttonBox);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");
        root.setMinWidth(300);
        root.setAlignment(javafx.geometry.Pos.CENTER);

        dialogStage.setScene(new Scene(root));
    }

    public boolean showAndWait() {
        dialogStage.showAndWait();
        return confirmed;
    }
}
