package GUI.controllers;

import Domain.Host.HostCell;
import Domain.Host.Receptor;
import Domain.Virus.*;
import GUI.ImageLoader;
import GUI.VirusSimulationAppFXML;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Simplified Controller for Infection Simulation (Refactored with FXML styling)
 * Now includes receptor selection and infection failure scenarios
 */
public class InfectionSimulationController {
    
    @FXML private Label titleLabel;
    @FXML private Label infoLabel;
    @FXML private ComboBox<String> receptorComboBox;
    @FXML private Label matchLabel;
    @FXML private Pane animationPane;
    @FXML private TextArea logArea;
    @FXML private Button startBtn;
    @FXML private Button resetBtn;
    @FXML private Button backBtn;
    
    private VirusSimulationAppFXML app;
    private String virusName;
    private Virus virus;
    private HostCell hostCell;
    
    // Animation components
    private VBox virusContainer;
    private Circle hostCellShape;
    private List<Circle> receptorMarkers;
    
    private boolean animationRunning = false;
    private SequentialTransition mainAnimation;
    
    // Available receptors
    private static final String[] RECEPTORS = {"CD4", "ACE2", "Sialic Acid", "CAR", "PVR", "Generic"};
    
    public void setApp(VirusSimulationAppFXML app) {
        this.app = app;
    }
    
    public void setVirusName(String virusName) {
        this.virusName = virusName;
        
        VirusRepository repository = VirusRepository.getInstance();
        this.virus = repository.findVirusByName(virusName);
        
        // Default to CD4 receptor
        this.hostCell = new HostCell(new Receptor("CD4"));
        
        updateUI();
        setupReceptorSelection();
        setupAnimation();
    }
    
    @FXML
    public void initialize() {
        resetBtn.setDisable(true);
        logArea.setText("Select a receptor and click 'Start Infection' to begin...\n");
    }
    
    private void updateUI() {
        titleLabel.setText(virusName + " Infection Simulation");
        infoLabel.setText(virus.getDescription().replace("\n", " | "));
    }
    
    private void setupReceptorSelection() {
        receptorComboBox.getItems().addAll(RECEPTORS);
        receptorComboBox.setValue(hostCell.getReceptor().getType());
        updateMatchLabel();
    }
    
    @FXML
    private void handleReceptorChange() {
        String selectedReceptor = receptorComboBox.getValue();
        hostCell = new HostCell(new Receptor(selectedReceptor));
        updateMatchLabel();
        setupAnimation();  // Redraw with new receptor
    }
    
    private void updateMatchLabel() {
        if (virus.isEnveloped()) {
            EnvelopedVirus envVirus = (EnvelopedVirus) virus;
            boolean compatible = envVirus.getLipidEnvelop().hasCompatibleReceptor(hostCell);
            
            if (compatible) {
                matchLabel.setText("✓ Compatible - Infection possible");
                matchLabel.setStyle("-fx-text-fill: #4caf50; -fx-background-color: #e8f5e9; -fx-font-size: 11px; -fx-padding: 5px 10px; -fx-background-radius: 5px; -fx-font-weight: bold;");
            } else {
                matchLabel.setText("✗ Incompatible - Infection will fail");
                matchLabel.setStyle("-fx-text-fill: #f44336; -fx-background-color: #ffebee; -fx-font-size: 11px; -fx-padding: 5px 10px; -fx-background-radius: 5px; -fx-font-weight: bold;");
            }
        } else {
            // Non-enveloped viruses always infect (direct entry)
            matchLabel.setText("ⓘ Non-enveloped - Direct infection");
            matchLabel.setStyle("-fx-text-fill: #2196F3; -fx-background-color: #e3f2fd; -fx-font-size: 11px; -fx-padding: 5px 10px; -fx-background-radius: 5px; -fx-font-weight: bold;");
        }
    }
    
    private void setupAnimation() {
        animationPane.getChildren().clear();
        receptorMarkers = new ArrayList<>();
        
        // Host cell with image
        setupHostCell();
        
        // Receptor markers (show for all viruses)
        setupReceptors();
        
        // Add cell label
        addCellLabels();
        
        // Create virus
        virusContainer = createVirusVisual();
        virusContainer.setLayoutX(50);
        virusContainer.setLayoutY(120);
        animationPane.getChildren().add(virusContainer);
    }
    
    private void setupHostCell() {
        Image hostCellImage = ImageLoader.getHostCell();
        
        hostCellShape = new Circle(400, 175, 80);
        hostCellShape.setFill(Color.web("#90EE90", 0.5));
        hostCellShape.setStroke(Color.web("#228B22"));
        hostCellShape.setStrokeWidth(3);
        animationPane.getChildren().add(hostCellShape);
        
        if (hostCellImage != null) {
            ImageView hostCellView = new ImageView(hostCellImage);
            hostCellView.setFitWidth(160);
            hostCellView.setFitHeight(160);
            hostCellView.setX(320);
            hostCellView.setY(95);
            animationPane.getChildren().add(hostCellView);
        }
    }
    
    private void setupReceptors() {
        Image receptorImage = ImageLoader.getReceptorByType(hostCell.getReceptor().getType());
        if (receptorImage == null) return;
        
        for (int i = 0; i < 8; i++) {
            double angle = i * Math.PI / 4;
            double x = 400 + 85 * Math.cos(angle);
            double y = 175 + 85 * Math.sin(angle);
            
            // Just place and rotate the image
            ImageView receptor = new ImageView(receptorImage);
            receptor.setFitWidth(12);
            receptor.setFitHeight(12);
            receptor.setX(x - 6);
            receptor.setY(y - 6);
            receptor.setRotate(Math.toDegrees(angle));
            animationPane.getChildren().add(receptor);
            
            // Hidden marker for animation
            Circle marker = new Circle(x, y, 6);
            marker.setFill(Color.TRANSPARENT);
            receptorMarkers.add(marker);
        }
    }
    
    private void addCellLabels() {
        Text cellLabel = new Text(350, 180, "Host Cell");
        cellLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cellLabel.setFill(Color.WHITE);
        animationPane.getChildren().add(cellLabel);
        
        Text receptorLabel = new Text(345, 195, "Receptor: " + hostCell.getReceptor().getType());
        receptorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        receptorLabel.setFill(Color.web("#ffeb3b"));
        animationPane.getChildren().add(receptorLabel);
    }
    
    private VBox createVirusVisual() {
        VBox virusGroup = new VBox(2);
        virusGroup.setAlignment(javafx.geometry.Pos.CENTER);
        
        if (virus.isEnveloped()) {
            virusGroup.getChildren().add(createEnvelopedVirus());
        } else {
            virusGroup.getChildren().add(createNonEnvelopedVirus());
        }
        
        // Labels
        Label virusLabel = new Label(virusName);
        virusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        virusLabel.setTextFill(Color.web("#1a237e"));
        
        Label naTypeLabel = new Label(virus.getNucleicAcid().getType() + " genome");
        naTypeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 9));
        naTypeLabel.setTextFill(
            virus.getNucleicAcid().getType().equalsIgnoreCase("DNA") ? 
            Color.web("#7e57c2") : Color.web("#ff6f00")
        );
        
        virusGroup.getChildren().addAll(virusLabel, naTypeLabel);
        return virusGroup;
    }
    
    private StackPane createEnvelopedVirus() {
        EnvelopedVirus envVirus = (EnvelopedVirus) virus;
        StackPane virusStack = new StackPane();
        
        // Envelope
        javafx.scene.Node envelope = createEnvelope();
        
        // Glycoprotein spikes
        Pane spikes = createGlycoproteins(envVirus.getLipidEnvelop().getGlycoproteins());
        
        // Capsid (semi-transparent)
        javafx.scene.Node capsid = createCapsidShape(20, true);
        
        // Nucleic acid
        javafx.scene.Node nucleicAcid = createNucleicAcidVisualization(14);
        
        virusStack.getChildren().addAll(envelope, spikes, capsid, nucleicAcid);
        virusStack.setPrefSize(60, 60);
        return virusStack;
    }
    
    private StackPane createNonEnvelopedVirus() {
        StackPane virusStack = new StackPane();
        
        // Capsid
        javafx.scene.Node capsid = createCapsidShape(30, false);
        
        // Nucleic acid (larger and more visible)
        javafx.scene.Node nucleicAcid = createNucleicAcidVisualization(22);
        
        virusStack.getChildren().addAll(capsid, nucleicAcid);
        virusStack.setPrefSize(60, 60);
        return virusStack;
    }
    
    private javafx.scene.Node createEnvelope() {
        Image envelopeImage = ImageLoader.getEnvelope();
        
        if (envelopeImage != null) {
            ImageView envelopeView = new ImageView(envelopeImage);
            envelopeView.setFitWidth(60);
            envelopeView.setFitHeight(60);
            envelopeView.setOpacity(0.7);
            return envelopeView;
        }
        
        // Simple fallback
        Circle envelope = new Circle(30);
        envelope.setFill(Color.web("#e57373", 0.7));
        envelope.setStroke(Color.web("#c62828"));
        envelope.setStrokeWidth(2);
        return envelope;
    }
    
    private javafx.scene.Node createCapsidShape(double size, boolean isInner) {
        Image capsidImage = ImageLoader.getCapsidByShape(virus.getCapsid().getShape());
        
        if (capsidImage != null) {
            ImageView capsidView = new ImageView(capsidImage);
            capsidView.setFitWidth(size * 2);
            capsidView.setFitHeight(size * 2);
            capsidView.setPreserveRatio(true);
            capsidView.setOpacity(isInner ? 0.6 : 0.8);
            return capsidView;
        }
        
        // Simple fallback
        Circle circle = new Circle(30, 30, size);
        circle.setFill(isInner ? Color.web("#7e57c2", 0.6) : Color.web("#64b5f6", 0.7));
        circle.setStroke(Color.web("#1565c0"));
        circle.setStrokeWidth(2);
        return circle;
    }
    
    private javafx.scene.Node createNucleicAcidVisualization(double size) {
        String naType = virus.getNucleicAcid().getType();
        Image naImage = ImageLoader.getNucleicAcidByType(naType);
        
        if (naImage != null) {
            ImageView naView = new ImageView(naImage);
            naView.setFitWidth(size * 2);
            naView.setFitHeight(size * 2);
            naView.setPreserveRatio(true);
            return naView;
        }
        
        // Simple fallback - just a colored circle
        Color naColor = naType.equalsIgnoreCase("DNA") ? Color.web("#7e57c2") : Color.web("#ff6f00");
        Circle naCircle = new Circle(30, 30, size * 0.6);
        naCircle.setFill(naColor);
        naCircle.setStroke(naColor.darker());
        naCircle.setStrokeWidth(2);
        return naCircle;
    }
    
    private Pane createGlycoproteins(List<Glycoprotein> glycoproteins) {
        Pane spikes = new Pane();
        Image spikeImage = ImageLoader.getSpikeProtein();
        if (spikeImage == null) return spikes;
        
        int spikeCount = glycoproteins.size() * 6;
        
        for (int i = 0; i < spikeCount; i++) {
            double angle = i * 2 * Math.PI / spikeCount;
            double x = 30 + 30 * Math.cos(angle);
            double y = 30 + 30 * Math.sin(angle);
            
            // Just place and rotate the image
            ImageView spike = new ImageView(spikeImage);
            spike.setFitWidth(12);
            spike.setFitHeight(12);
            spike.setX(x - 6);
            spike.setY(y - 6);
            spike.setRotate(Math.toDegrees(angle));
            spikes.getChildren().add(spike);
        }
        
        return spikes;
    }
    
    @FXML
    private void handleStart() {
        if (animationRunning) return;
        
        animationRunning = true;
        startBtn.setDisable(true);
        resetBtn.setDisable(true);
        receptorComboBox.setDisable(true);
        logArea.clear();
        
        mainAnimation = new SequentialTransition();
        
        // Check if enveloped virus with incompatible receptor
        final boolean willFail;
        if (virus.isEnveloped()) {
            EnvelopedVirus envVirus = (EnvelopedVirus) virus;
            willFail = !envVirus.getLipidEnvelop().hasCompatibleReceptor(hostCell);
        } else {
            willFail = false;
        }
        
        // Phase 1: Attachment
        addAttachmentAnimation();
        
        if (willFail) {
            // Phase 2: Rejection (failure animation)
            addRejectionAnimation();
        } else {
            // Phase 2: Entry (successful infection)
            addEntryAnimation();
            
            // Phase 3: Injection
            addInjectionAnimation();
        }
        
        mainAnimation.setOnFinished(e -> {
            animationRunning = false;
            resetBtn.setDisable(false);
            receptorComboBox.setDisable(false);
            
            if (willFail) {
                appendLog("\n❌ INFECTION FAILED - Receptor mismatch!\n");
                appendLog("The virus cannot infect the host cell.\n");
            } else {
                appendLog("\n✓ INFECTION SUCCESSFUL\n");
            }
        });
        
        mainAnimation.play();
    }
    
    private void addAttachmentAnimation() {
        TranslateTransition moveToCell = new TranslateTransition(Duration.seconds(2), virusContainer);
        moveToCell.setToX(280);
        moveToCell.setInterpolator(Interpolator.EASE_BOTH);
        
        ParallelTransition attachmentAnim;
        if (virus.getCapsid().getShape().equalsIgnoreCase("helical")) {
            RotateTransition wobble = new RotateTransition(Duration.seconds(2), virusContainer);
            wobble.setByAngle(360);
            wobble.setCycleCount(2);
            attachmentAnim = new ParallelTransition(moveToCell, wobble);
        } else {
            attachmentAnim = new ParallelTransition(moveToCell);
        }
        
        attachmentAnim.setOnFinished(e -> {
            appendLog("\n[ATTACHMENT PHASE]\n");
            appendLog("Capsid: " + virus.getCapsid().getShape() + "\n");
            appendLog("Genome: " + virus.getNucleicAcid().getType() + "\n");
            
            if (virus.isEnveloped()) {
                EnvelopedVirus envVirus = (EnvelopedVirus) virus;
                List<Glycoprotein> gps = envVirus.getLipidEnvelop().getGlycoproteins();
                appendLog("Enveloped with " + gps.size() + " glycoprotein(s)\n");
                
                if (envVirus.getLipidEnvelop().hasCompatibleReceptor(hostCell)) {
                    appendLog("✓ Receptor binding successful!\n");
                    animateReceptorBinding(true);
                } else {
                    appendLog("✗ Receptor incompatible - binding failed!\n");
                    animateReceptorBinding(false);
                }
            } else {
                appendLog("Non-enveloped - direct attachment\n");
            }
        });
        
        mainAnimation.getChildren().add(attachmentAnim);
        mainAnimation.getChildren().add(new PauseTransition(Duration.seconds(1.5)));
    }
    
    private void animateReceptorBinding(boolean success) {
        for (int i = 2; i < 6; i++) {
            if (i >= receptorMarkers.size()) continue;
            
            Circle marker = receptorMarkers.get(i);
            
            // Find corresponding ImageView
            javafx.scene.Node targetNode = null;
            for (javafx.scene.Node node : animationPane.getChildren()) {
                if (node instanceof ImageView) {
                    ImageView iv = (ImageView) node;
                    double dx = Math.abs(iv.getX() + 6 - marker.getCenterX());
                    double dy = Math.abs(iv.getY() + 6 - marker.getCenterY());
                    if (dx < 1 && dy < 1) {
                        targetNode = iv;
                        break;
                    }
                }
            }
            
            if (targetNode != null) {
                ScaleTransition pulse = new ScaleTransition(Duration.seconds(0.3), targetNode);
                pulse.setToX(2.0);
                pulse.setToY(2.0);
                pulse.setCycleCount(2);
                pulse.setAutoReverse(true);
                pulse.setDelay(Duration.seconds(i * 0.2));
                pulse.play();
            }
        }
    }
    
    private double getAttachmentDuration() {
        switch (virus.getCapsid().getShape().toLowerCase()) {
            case "icosahedral": return 2.5;
            case "helical": return 2.0;
            case "conical": return 1.8;
            default: return 2.0;
        }
    }
    
    private void addRejectionAnimation() {
        appendLog("\n[REJECTION PHASE]\n");
        appendLog("Glycoproteins cannot bind to receptor\n");
        appendLog("Virus is being repelled...\n");
        
        // Get current position
        double currentX = virusContainer.getTranslateX();
        
        // Shake animation (rejection)
        Timeline shake = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(virusContainer.translateXProperty(), currentX)),
            new KeyFrame(Duration.seconds(0.1), new KeyValue(virusContainer.translateXProperty(), currentX - 10)),
            new KeyFrame(Duration.seconds(0.2), new KeyValue(virusContainer.translateXProperty(), currentX + 10)),
            new KeyFrame(Duration.seconds(0.3), new KeyValue(virusContainer.translateXProperty(), currentX - 5)),
            new KeyFrame(Duration.seconds(0.4), new KeyValue(virusContainer.translateXProperty(), currentX + 5)),
            new KeyFrame(Duration.seconds(0.5), new KeyValue(virusContainer.translateXProperty(), currentX))
        );
        shake.setCycleCount(3);
        mainAnimation.getChildren().add(shake);
        
        // Bounce back animation
        TranslateTransition bounceBack = new TranslateTransition(Duration.seconds(2), virusContainer);
        bounceBack.setToX(30);
        bounceBack.setInterpolator(Interpolator.EASE_IN);
        
        // Rotation during bounce
        RotateTransition spin = new RotateTransition(Duration.seconds(2), virusContainer);
        spin.setByAngle(-360);
        
        // Fade slightly to show failure
        FadeTransition fade = new FadeTransition(Duration.seconds(2), virusContainer);
        fade.setToValue(0.5);
        
        ParallelTransition rejection = new ParallelTransition(bounceBack, spin, fade);
        
        rejection.setOnFinished(e -> {
            // Cell shows relief (no infection)
            FillTransition cellRelief = new FillTransition(Duration.seconds(0.8), hostCellShape);
            cellRelief.setToValue(Color.web("#81c784"));  // Green = healthy
            cellRelief.play();
        });
        
        mainAnimation.getChildren().add(rejection);
        mainAnimation.getChildren().add(new PauseTransition(Duration.seconds(1)));
    }
    
    private void addEntryAnimation() {
        if (virus.isEnveloped()) {
            addEnvelopedEntryAnimation();
        } else {
            addNonEnvelopedEntryAnimation();
        }
    }
    
    private void addEnvelopedEntryAnimation() {
        appendLog("\n[ENTRY PHASE]\n");
        appendLog("Envelope fusion with membrane\n");
        appendLog("Capsid entering cytoplasm\n");
        
        ScaleTransition shrink = new ScaleTransition(Duration.seconds(1.5), virusContainer);
        shrink.setToX(0.8);
        shrink.setToY(0.8);
        
        TranslateTransition enter = new TranslateTransition(Duration.seconds(1.5), virusContainer);
        enter.setToX(320);
        
        ParallelTransition entryAnim;
        if (virus.getCapsid().getShape().equalsIgnoreCase("helical")) {
            RotateTransition rotate = new RotateTransition(Duration.seconds(1.5), virusContainer);
            rotate.setByAngle(180);
            entryAnim = new ParallelTransition(shrink, enter, rotate);
        } else {
            entryAnim = new ParallelTransition(shrink, enter);
        }
        
        mainAnimation.getChildren().add(entryAnim);
        mainAnimation.getChildren().add(new PauseTransition(Duration.seconds(1)));
    }
    
    private void addNonEnvelopedEntryAnimation() {
        appendLog("\n[ENTRY PHASE]\n");
        appendLog("Capsid dissolving at surface\n");
        appendLog("Genome release initiated\n");
        
        ScaleTransition shrink = new ScaleTransition(Duration.seconds(1.8), virusContainer);
        shrink.setToX(0.5);
        shrink.setToY(0.5);
        
        TranslateTransition enter = new TranslateTransition(Duration.seconds(1.8), virusContainer);
        enter.setToX(300);
        
        Timeline shake = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(virusContainer.rotateProperty(), 0)),
            new KeyFrame(Duration.seconds(0.1), new KeyValue(virusContainer.rotateProperty(), 5)),
            new KeyFrame(Duration.seconds(0.2), new KeyValue(virusContainer.rotateProperty(), -5)),
            new KeyFrame(Duration.seconds(0.3), new KeyValue(virusContainer.rotateProperty(), 0))
        );
        shake.setCycleCount(6);
        
        ParallelTransition entryAnim = new ParallelTransition(shrink, enter, shake);
        mainAnimation.getChildren().add(entryAnim);
        mainAnimation.getChildren().add(new PauseTransition(Duration.seconds(1)));
    }
    
    private void addInjectionAnimation() {
        appendLog("\n[INJECTION PHASE]\n");
        String naType = virus.getNucleicAcid().getType();
        appendLog(naType + " genome released into cell\n");
        appendLog("Host machinery hijacked\n");
        
        FadeTransition fade = new FadeTransition(Duration.seconds(2), virusContainer);
        fade.setToValue(0.3);
        
        TranslateTransition finalMove = new TranslateTransition(Duration.seconds(2), virusContainer);
        finalMove.setToX(340);
        
        ParallelTransition injection = new ParallelTransition(fade, finalMove);
        
        injection.setOnFinished(e -> {
            int pulseCount = virus.isEnveloped() ? 4 : 6;
            double pulseScale = virus.isEnveloped() ? 1.1 : 1.15;
            
            ScaleTransition pulse = new ScaleTransition(Duration.seconds(0.5), hostCellShape);
            pulse.setToX(pulseScale);
            pulse.setToY(pulseScale);
            pulse.setCycleCount(pulseCount);
            pulse.setAutoReverse(true);
            pulse.play();
            
            Color infectionColor = naType.equalsIgnoreCase("RNA") ? 
                Color.web("#ffab91") : Color.web("#ce93d8");
            hostCellShape.setFill(infectionColor);
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
        receptorComboBox.setDisable(false);
        
        // Redraw entire animation to fix all visual states
        setupAnimation();
        
        logArea.clear();
        logArea.setText("Select a receptor and click 'Start Infection' to begin...\n");
    }
    
    @FXML
    private void handleBack() {
        app.showVirusSelection(virus.isEnveloped());
    }
    
    private void appendLog(String message) {
        logArea.appendText(message);
    }
    
}
