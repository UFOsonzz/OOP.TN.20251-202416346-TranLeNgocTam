package GUI.controllers;

import Domain.Host.HostCell;
import Domain.Host.Receptor;
import Domain.Virus.*;
import GUI.VirusSimulationAppFXML;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Controller for Infection Simulation screen (FXML version)
 */
public class InfectionSimulationController {
    
    @FXML private Label titleLabel;
    @FXML private Label infoLabel;
    @FXML private Pane animationPane;
    @FXML private TextArea logArea;
    @FXML private Button startBtn;
    @FXML private Button resetBtn;
    @FXML private Button backBtn;
    
    private VirusSimulationAppFXML app;
    private String virusName;
    private Virus virus;
    private HostCell hostCell;
    
    private VBox virusShape;
    private Circle hostCellShape;
    private boolean animationRunning = false;
    private SequentialTransition mainAnimation;
    
    public void setApp(VirusSimulationAppFXML app) {
        this.app = app;
    }
    
    public void setVirusName(String virusName) {
        this.virusName = virusName;
        
        // Get virus from repository
        VirusRepository repository = VirusFactory.createVirusRepository();
        this.virus = repository.findVirusByName(virusName);
        
        // Create host cell with appropriate receptor
        String receptorType = getReceptorForVirus(virusName);
        this.hostCell = new HostCell(new Receptor(receptorType));
        
        updateUI();
        setupAnimation();
    }
    
    @FXML
    public void initialize() {
        resetBtn.setDisable(true);
        logArea.setText("Click 'Start Infection' to begin simulation...\n");
        setupButtonEffects();
    }
    
    private void updateUI() {
        titleLabel.setText(getVirusEmoji() + " " + virusName + " Infection Simulation");
        infoLabel.setText(virus.getDescription().replace("\n", " | "));
    }
    
    private void setupAnimation() {
        animationPane.getChildren().clear();
        
        // Create host cell (large circle on right)
        hostCellShape = new Circle(400, 175, 80);
        hostCellShape.setFill(Color.web("#81c784"));
        hostCellShape.setStroke(Color.web("#388e3c"));
        hostCellShape.setStrokeWidth(3);
        
        // Add receptor markers on cell
        for (int i = 0; i < 8; i++) {
            double angle = i * Math.PI / 4;
            double x = 400 + 85 * Math.cos(angle);
            double y = 175 + 85 * Math.sin(angle);
            Circle receptor = new Circle(x, y, 5);
            receptor.setFill(Color.web("#ffd54f"));
            receptor.setStroke(Color.web("#f57f17"));
            receptor.setStrokeWidth(2);
            animationPane.getChildren().add(receptor);
        }
        
        // Add cell label
        Text cellLabel = new Text(360, 180, "Host Cell");
        cellLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cellLabel.setFill(Color.WHITE);
        
        // Create virus shape
        virusShape = createVirusShape();
        virusShape.setLayoutX(50);
        virusShape.setLayoutY(140);
        
        animationPane.getChildren().addAll(hostCellShape, cellLabel, virusShape);
    }
    
    private VBox createVirusShape() {
        VBox virusGroup = new VBox(2);
        virusGroup.setAlignment(javafx.geometry.Pos.CENTER);
        
        if (virus.isEnveloped()) {
            // Enveloped virus - circle with spikes
            Circle envelope = new Circle(30);
            envelope.setFill(Color.web("#e57373"));
            envelope.setStroke(Color.web("#c62828"));
            envelope.setStrokeWidth(2);
            
            StackPane virusStack = new StackPane();
            
            // Add glycoprotein spikes
            Pane spikes = new Pane();
            for (int i = 0; i < 12; i++) {
                double angle = i * Math.PI / 6;
                double x1 = 30 + 25 * Math.cos(angle);
                double y1 = 30 + 25 * Math.sin(angle);
                double x2 = 30 + 35 * Math.cos(angle);
                double y2 = 30 + 35 * Math.sin(angle);
                
                Line spike = new Line(x1, y1, x2, y2);
                spike.setStroke(Color.web("#ff5252"));
                spike.setStrokeWidth(3);
                spikes.getChildren().add(spike);
                
                Circle spikeEnd = new Circle(x2, y2, 3);
                spikeEnd.setFill(Color.web("#ffeb3b"));
                spikes.getChildren().add(spikeEnd);
            }
            
            // Inner capsid
            Circle capsid = new Circle(30, 30, 20);
            capsid.setFill(Color.web("#7e57c2"));
            capsid.setStroke(Color.web("#4a148c"));
            capsid.setStrokeWidth(2);
            
            virusStack.getChildren().addAll(envelope, spikes, capsid);
            virusStack.setPrefSize(60, 60);
            virusGroup.getChildren().add(virusStack);
            
        } else {
            // Non-enveloped virus - polygon capsid
            Polygon capsid = new Polygon();
            double radius = 30;
            int sides = 6; // Hexagon for icosahedral
            
            for (int i = 0; i < sides; i++) {
                double angle = 2 * Math.PI * i / sides;
                capsid.getPoints().addAll(
                    30 + radius * Math.cos(angle),
                    30 + radius * Math.sin(angle)
                );
            }
            
            capsid.setFill(Color.web("#64b5f6"));
            capsid.setStroke(Color.web("#1976d2"));
            capsid.setStrokeWidth(3);
            
            Pane capsidPane = new Pane(capsid);
            capsidPane.setPrefSize(60, 60);
            virusGroup.getChildren().add(capsidPane);
        }
        
        // Virus label
        Label virusLabel = new Label(virusName);
        virusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        virusLabel.setTextFill(Color.web("#1a237e"));
        virusGroup.getChildren().add(virusLabel);
        
        return virusGroup;
    }
    
    @FXML
    private void handleStart() {
        if (animationRunning) return;
        
        animationRunning = true;
        startBtn.setDisable(true);
        resetBtn.setDisable(true);
        logArea.clear();
        
        mainAnimation = new SequentialTransition();
        
        // Phase 1: Attachment
        addAttachmentAnimation();
        
        // Phase 2: Entry
        addEntryAnimation();
        
        // Phase 3: Injection
        addInjectionAnimation();
        
        mainAnimation.setOnFinished(e -> {
            animationRunning = false;
            resetBtn.setDisable(false);
            appendLog("\n✅ Infection complete! Click Reset to run again.");
        });
        
        mainAnimation.play();
    }
    
    private void addAttachmentAnimation() {
        // Move virus toward cell
        TranslateTransition moveToCell = new TranslateTransition(Duration.seconds(2), virusShape);
        moveToCell.setToX(280);
        moveToCell.setInterpolator(Interpolator.EASE_BOTH);
        
        moveToCell.setOnFinished(e -> {
            appendLog("\n[PHASE 1: ATTACHMENT]\n");
            if (virus.isEnveloped()) {
                appendLog("🔴 " + virusName + " approaching host cell...\n");
                appendLog("🔑 Checking lock-key compatibility...\n");
                
                EnvelopedVirus envVirus = (EnvelopedVirus) virus;
                if (envVirus.getLipidEnvelop().hasCompatibleReceptor(hostCell)) {
                    appendLog("✅ Lock-key match found!\n");
                    appendLog("🔗 Glycoproteins binding to receptor: " + 
                             envVirus.getLipidEnvelop().getGlycoproteins().get(0).getName() + "\n");
                } else {
                    appendLog("⚠️ No compatible receptor!\n");
                }
            } else {
                appendLog("🔵 " + virusName + " attaching to cell surface...\n");
                appendLog("🔗 Capsid proteins binding to membrane receptors\n");
            }
        });
        
        mainAnimation.getChildren().add(moveToCell);
        mainAnimation.getChildren().add(new PauseTransition(Duration.seconds(1)));
    }
    
    private void addEntryAnimation() {
        // Virus enters cell
        ScaleTransition shrink = new ScaleTransition(Duration.seconds(1.5), virusShape);
        shrink.setToX(0.7);
        shrink.setToY(0.7);
        
        TranslateTransition enter = new TranslateTransition(Duration.seconds(1.5), virusShape);
        enter.setToX(320);
        
        ParallelTransition entryAnim = new ParallelTransition(shrink, enter);
        
        entryAnim.setOnFinished(e -> {
            appendLog("\n[PHASE 2: ENTRY]\n");
            if (virus.isEnveloped()) {
                appendLog("🧬 Lipid envelope fusing with cell membrane...\n");
                appendLog("📦 Capsid entering cell cytoplasm\n");
            } else {
                appendLog("💥 Capsid dissolving at cell surface...\n");
                appendLog("📦 Capsid shape: " + virus.getCapsid().getShape() + "\n");
            }
        });
        
        mainAnimation.getChildren().add(entryAnim);
        mainAnimation.getChildren().add(new PauseTransition(Duration.seconds(1)));
    }
    
    private void addInjectionAnimation() {
        // Final entry into cell
        FadeTransition fade = new FadeTransition(Duration.seconds(2), virusShape);
        fade.setToValue(0.3);
        
        TranslateTransition finalMove = new TranslateTransition(Duration.seconds(2), virusShape);
        finalMove.setToX(340);
        
        ParallelTransition injection = new ParallelTransition(fade, finalMove);
        
        injection.setOnFinished(e -> {
            appendLog("\n[PHASE 3: NUCLEIC ACID INJECTION]\n");
            appendLog("💉 Releasing viral genetic material...\n");
            appendLog("🧬 Nucleic acid type: " + virus.getNucleicAcid().getType() + "\n");
            appendLog("✨ Viral genome now inside the cell!\n");
            
            // Pulsate cell to show infection
            ScaleTransition pulse = new ScaleTransition(Duration.seconds(0.5), hostCellShape);
            pulse.setToX(1.1);
            pulse.setToY(1.1);
            pulse.setCycleCount(4);
            pulse.setAutoReverse(true);
            pulse.play();
        });
        
        mainAnimation.getChildren().add(injection);
    }
    
    @FXML
    private void handleReset() {
        if (mainAnimation != null) {
            mainAnimation.stop();
        }
        
        animationRunning = false;
        startBtn.setDisable(false);
        resetBtn.setDisable(true);
        
        // Reset virus position and properties
        virusShape.setTranslateX(0);
        virusShape.setTranslateY(0);
        virusShape.setScaleX(1);
        virusShape.setScaleY(1);
        virusShape.setOpacity(1);
        
        // Reset cell
        hostCellShape.setScaleX(1);
        hostCellShape.setScaleY(1);
        
        logArea.clear();
        logArea.setText("Click 'Start Infection' to begin simulation...\n");
    }
    
    @FXML
    private void handleBack() {
        app.showVirusSelection(virus.isEnveloped());
    }
    
    private void appendLog(String message) {
        logArea.appendText(message);
    }
    
    private void setupButtonEffects() {
        // Start button hover
        startBtn.setOnMouseEntered(e -> 
            startBtn.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white; " +
                            "-fx-background-radius: 10; -fx-cursor: hand;"));
        startBtn.setOnMouseExited(e -> 
            startBtn.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; " +
                            "-fx-background-radius: 10; -fx-cursor: hand;"));
        
        // Reset button hover
        resetBtn.setOnMouseEntered(e -> 
            resetBtn.setStyle("-fx-background-color: #f57c00; -fx-text-fill: white; " +
                            "-fx-background-radius: 10; -fx-cursor: hand;"));
        resetBtn.setOnMouseExited(e -> 
            resetBtn.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white; " +
                            "-fx-background-radius: 10; -fx-cursor: hand;"));
        
        // Back button hover
        backBtn.setOnMouseEntered(e -> 
            backBtn.setStyle("-fx-background-color: #455a64; -fx-text-fill: white; " +
                           "-fx-background-radius: 10; -fx-cursor: hand;"));
        backBtn.setOnMouseExited(e -> 
            backBtn.setStyle("-fx-background-color: #607d8b; -fx-text-fill: white; " +
                           "-fx-background-radius: 10; -fx-cursor: hand;"));
    }
    
    private String getReceptorForVirus(String virusName) {
        switch (virusName) {
            case "HIV": return "CD4";
            case "SARS-CoV-2": return "ACE2";
            case "AdenoVirus": return "CAR";
            case "PolioVirus": return "PVR";
            default: return "Generic";
        }
    }
    
    private String getVirusEmoji() {
        switch (virusName) {
            case "HIV": return "🔴";
            case "SARS-CoV-2": return "🦠";
            case "AdenoVirus": return "🔷";
            case "PolioVirus": return "🔵";
            default: return "🦠";
        }
    }
}
