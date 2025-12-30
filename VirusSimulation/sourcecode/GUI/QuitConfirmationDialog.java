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
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: #d32f2f;");

        Button yesBtn = new Button("Yes");
        yesBtn.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8;");
        yesBtn.setOnAction(e -> { confirmed = true; dialogStage.close(); });

        Button noBtn = new Button("No");
        noBtn.setStyle("-fx-background-color: #1976d2; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8;");
        noBtn.setOnAction(e -> { confirmed = false; dialogStage.close(); });

        HBox buttonBox = new HBox(20, yesBtn, noBtn);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);

        VBox root = new VBox(20, label, buttonBox);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #fff3e0; -fx-border-radius: 10; -fx-background-radius: 10;");
        root.setMinWidth(320);
        root.setAlignment(javafx.geometry.Pos.CENTER);

        dialogStage.setScene(new Scene(root));
    }

    public boolean showAndWait() {
        dialogStage.showAndWait();
        return confirmed;
    }
}
