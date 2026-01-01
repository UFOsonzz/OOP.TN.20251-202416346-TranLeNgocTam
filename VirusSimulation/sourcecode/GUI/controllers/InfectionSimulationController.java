package GUI.controllers;

import Domain.Host.HostCell;
import Domain.Host.Receptor;
import Domain.Virus.*;
import GUI.ImageLoader;
import GUI.VirusSimulationAppFXML;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
 * Controller for Infection Simulation screen (FXML version)
 * Uses OOP principles to create animations based on virus properties, not hardcoded names
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
    
    // Separate components for detailed animation
    private VBox virusContainer;
    private javafx.scene.Node envelopeShape;
    private javafx.scene.Node capsidShape;
    private javafx.scene.Node nucleicAcidShape;
    private List<javafx.scene.Node> glycoproteinSpikes;
    private Circle hostCellShape;
    private List<Circle> receptorMarkers;
    
    private boolean animationRunning = false;
    private SequentialTransition mainAnimation;
    
    public void setApp(VirusSimulationAppFXML app) {
        this.app = app;
    }
    
    public void setVirusName(String virusName) {
        this.virusName = virusName;
        
        // Get virus from repository
        VirusRepository repository = VirusRepository.getInstance();
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
        logArea.setText("Click Start to begin...\n");
    }
    
    private void updateUI() {
        titleLabel.setText(virusName + " Infection Simulation");
        infoLabel.setText(virus.getDescription().replace("\n", " | "));
    }
    
    private void setupAnimation() {
        animationPane.getChildren().clear();
        glycoproteinSpikes = new ArrayList<>();
        receptorMarkers = new ArrayList<>();
        
        // Try to use host cell image
        Image hostCellImage = ImageLoader.getHostCell();
        if (hostCellImage != null) {
            ImageView hostCellView = new ImageView(hostCellImage);
            hostCellView.setFitWidth(160);
            hostCellView.setFitHeight(160);
            hostCellView.setX(320);
            hostCellView.setY(95);
            animationPane.getChildren().add(hostCellView);
        }
        
        // Create host cell shape (for animation)
        hostCellShape = new Circle(400, 175, 80);
        hostCellShape.setFill(hostCellImage != null ? Color.TRANSPARENT : Color.web("#90EE90"));
        hostCellShape.setStroke(Color.web("#228B22"));
        hostCellShape.setStrokeWidth(3);
        animationPane.getChildren().add(hostCellShape);
        
        // Add receptor markers ONLY for enveloped viruses (lock-key mechanism)
        if (virus.isEnveloped()) {
            Image receptorImage = ImageLoader.getReceptorByType(hostCell.getReceptor().getType());
            
            for (int i = 0; i < 8; i++) {
                double angle = i * Math.PI / 4;
                double x = 400 + 85 * Math.cos(angle);
                double y = 175 + 85 * Math.sin(angle);
                
                if (receptorImage != null) {
                    // Use receptor image
                    ImageView receptor = new ImageView(receptorImage);
                    receptor.setFitWidth(12);
                    receptor.setFitHeight(12);
                    receptor.setX(x - 6);
                    receptor.setY(y - 6);
                    animationPane.getChildren().add(receptor);
                    // Store as Circle for animation compatibility
                    Circle receptorCircle = new Circle(x, y, 6);
                    receptorCircle.setFill(Color.TRANSPARENT);
                    receptorMarkers.add(receptorCircle);
                } else {
                    // Fallback to circle
                    Circle receptor = new Circle(x, y, 6);
                    receptor.setFill(Color.web("#ffd54f"));
                    receptor.setStroke(Color.web("#f57f17"));
                    receptor.setStrokeWidth(2);
                    receptorMarkers.add(receptor);
                    animationPane.getChildren().add(receptor);
                }
            }
        }
        
        // Add cell label
        Text cellLabel = new Text(350, 180, "Host Cell");
        cellLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cellLabel.setFill(Color.WHITE);
        animationPane.getChildren().add(cellLabel);
        
        // Show receptor type only for enveloped viruses
        if (virus.isEnveloped()) {
            Text receptorLabel = new Text(350, 195, "Receptor: " + hostCell.getReceptor().getType());
            receptorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
            receptorLabel.setFill(Color.web("#ffeb3b"));
            animationPane.getChildren().add(receptorLabel);
        }
        
        // Create virus with separate components
        virusContainer = createVirusWithComponents();
        virusContainer.setLayoutX(50);
        virusContainer.setLayoutY(120);
        
        animationPane.getChildren().add(virusContainer);
    }
    
    private VBox createVirusWithComponents() {
        VBox virusGroup = new VBox(2);
        virusGroup.setAlignment(javafx.geometry.Pos.CENTER);
        
        if (virus.isEnveloped()) {
            // Enveloped virus - create based on properties
            EnvelopedVirus envVirus = (EnvelopedVirus) virus;
            StackPane virusStack = new StackPane();
            
            // Try to use envelope image
            javafx.scene.Node envelope;
            Image envelopeImage = ImageLoader.getEnvelope();
            if (envelopeImage != null) {
                ImageView envelopeView = new ImageView(envelopeImage);
                envelopeView.setFitWidth(60);
                envelopeView.setFitHeight(60);
                envelopeView.setOpacity(0.7);
                envelope = envelopeView;
            } else {
                // Fallback to circle
                Circle envelopeCircle = new Circle(30);
                envelopeCircle.setFill(Color.web("#e57373", 0.7));
                envelopeCircle.setStroke(Color.web("#c62828"));
                envelopeCircle.setStrokeWidth(2);
                envelope = envelopeCircle;
            }
            
            // Glycoprotein spikes - count based on actual glycoproteins
            Pane spikes = createGlycoproteins(envVirus.getLipidEnvelop().getGlycoproteins());
            
            // Inner capsid with shape-specific appearance (semi-transparent)
            javafx.scene.Node capsid = createCapsidByShape(virus.getCapsid().getShape(), 20, true);
            
            // Nucleic acid - visible through semi-transparent capsid
            javafx.scene.Node nucleicAcid = createNucleicAcidVisualization(14);
            
            virusStack.getChildren().addAll(envelope, spikes, capsid, nucleicAcid);
            virusStack.setPrefSize(60, 60);
            virusGroup.getChildren().add(virusStack);
            
        } else {
            // Non-enveloped virus - shape based on capsid type
            StackPane virusStack = new StackPane();
            
            // Capsid (semi-transparent so nucleic acid is visible)
            javafx.scene.Node capsid = createCapsidByShape(virus.getCapsid().getShape(), 30, false);
            
            // Nucleic acid - BIGGER and more visible for non-enveloped
            javafx.scene.Node nucleicAcid = createNucleicAcidVisualization(22);
            
            virusStack.getChildren().addAll(capsid, nucleicAcid);
            virusStack.setPrefSize(60, 60);
            virusGroup.getChildren().add(virusStack);
        }
        
        // Virus label with nucleic acid type highlighted
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
    
    private javafx.scene.Node createCapsidByShape(String shape, double size, boolean isInner) {
        // Try to load image first
        Image capsidImage = ImageLoader.getCapsidByShape(shape);
        
        if (capsidImage != null) {
            ImageView capsidView = new ImageView(capsidImage);
            capsidView.setFitWidth(size * 2);
            capsidView.setFitHeight(size * 2);
            capsidView.setPreserveRatio(true);
            capsidView.setOpacity(isInner ? 0.6 : 0.8);
            return capsidView;
        }
        
        // Fallback to hardcoded shapes
        Pane container = new Pane();
        Color fillColor = isInner ? Color.web("#7e57c2") : Color.web("#64b5f6");
        Color strokeColor = fillColor.darker();
        double opacity = isInner ? 0.6 : 0.7;
        
        switch (shape.toLowerCase()) {
            case "icosahedral":
                Polygon icosa = new Polygon();
                for (int i = 0; i < 6; i++) {
                    double angle = 2 * Math.PI * i / 6;
                    icosa.getPoints().addAll(
                        30 + size * Math.cos(angle),
                        30 + size * Math.sin(angle)
                    );
                }
                icosa.setFill(Color.web(fillColor.toString(), opacity));
                icosa.setStroke(strokeColor);
                icosa.setStrokeWidth(2);
                container.getChildren().add(icosa);
                break;
                
            case "helical":
                javafx.scene.shape.Ellipse helix = new javafx.scene.shape.Ellipse(30, 30, size * 0.8, size);
                helix.setFill(Color.web(fillColor.toString(), opacity));
                helix.setStroke(strokeColor);
                helix.setStrokeWidth(2);
                container.getChildren().add(helix);
                
                for (int i = 0; i < 4; i++) {
                    Line helixLine = new Line(
                        30 - size * 0.6, 30 - size + i * size/2,
                        30 + size * 0.6, 30 - size + i * size/2
                    );
                    helixLine.setStroke(strokeColor);
                    helixLine.setStrokeWidth(1);
                    container.getChildren().add(helixLine);
                }
                break;
                
            case "conical":
                Polygon cone = new Polygon(
                    30, 30 - size,
                    30 - size * 0.7, 30 + size * 0.5,
                    30 + size * 0.7, 30 + size * 0.5
                );
                cone.setFill(Color.web(fillColor.toString(), opacity));
                cone.setStroke(strokeColor);
                cone.setStrokeWidth(2);
                container.getChildren().add(cone);
                break;
                
            default:
                Circle circle = new Circle(30, 30, size);
                circle.setFill(Color.web(fillColor.toString(), opacity));
                circle.setStroke(strokeColor);
                circle.setStrokeWidth(2);
                container.getChildren().add(circle);
        }
        
        return container;
    }
    
    private javafx.scene.Node createNucleicAcidVisualization(double size) {
        String naType = virus.getNucleicAcid().getType();
        
        // Try to load image first
        Image naImage = ImageLoader.getNucleicAcidByType(naType);
        
        if (naImage != null) {
            ImageView naView = new ImageView(naImage);
            naView.setFitWidth(size * 2);
            naView.setFitHeight(size * 2);
            naView.setPreserveRatio(true);
            return naView;
        }
        
        // Fallback to hardcoded drawing
        Pane naContainer = new Pane();
        
        if (naType.equalsIgnoreCase("DNA")) {
            // DNA: Double helix structure
            Color dnaColor = Color.web("#7e57c2");
            
            // Create double helix pattern
            for (int i = 0; i < 8; i++) {
                double y = 30 - size/2 + i * size/7;
                double offset = Math.sin(i * Math.PI / 3) * size/4;
                
                // Strand 1
                Circle base1 = new Circle(30 + offset, y, 2.5);
                base1.setFill(dnaColor);
                base1.setStroke(dnaColor.darker());
                base1.setStrokeWidth(1);
                naContainer.getChildren().add(base1);
                
                // Strand 2 (complementary)
                Circle base2 = new Circle(30 - offset, y, 2.5);
                base2.setFill(dnaColor.brighter());
                base2.setStroke(dnaColor.darker());
                base2.setStrokeWidth(1);
                naContainer.getChildren().add(base2);
                
                // Base pairs (connecting lines)
                if (i < 7) {
                    Line basePair = new Line(30 + offset, y, 30 - offset, y);
                    basePair.setStroke(Color.web("#9575cd", 0.6));
                    basePair.setStrokeWidth(1);
                    naContainer.getChildren().add(basePair);
                }
            }
            
            // Add "DNA" label in center
            Text dnaLabel = new Text(22, 32, "DNA");
            dnaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 8));
            dnaLabel.setFill(Color.WHITE);
            dnaLabel.setStroke(dnaColor.darker());
            dnaLabel.setStrokeWidth(0.5);
            naContainer.getChildren().add(dnaLabel);
            
        } else {
            // RNA: Single wavy strand
            Color rnaColor = Color.web("#ff6f00");
            
            // Create single wavy strand
            for (int i = 0; i < 10; i++) {
                double y = 30 - size/2 + i * size/9;
                double offset = Math.sin(i * Math.PI / 2.5) * size/3;
                
                Circle base = new Circle(30 + offset, y, 3);
                base.setFill(rnaColor);
                base.setStroke(rnaColor.darker());
                base.setStrokeWidth(1);
                naContainer.getChildren().add(base);
                
                // Connect bases with lines
                if (i < 9) {
                    double nextY = 30 - size/2 + (i+1) * size/9;
                    double nextOffset = Math.sin((i+1) * Math.PI / 2.5) * size/3;
                    Line connector = new Line(30 + offset, y, 30 + nextOffset, nextY);
                    connector.setStroke(rnaColor);
                    connector.setStrokeWidth(2);
                    naContainer.getChildren().add(connector);
                }
            }
            
            // Add "RNA" label in center
            Text rnaLabel = new Text(22, 32, "RNA");
            rnaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 8));
            rnaLabel.setFill(Color.WHITE);
            rnaLabel.setStroke(rnaColor.darker());
            rnaLabel.setStrokeWidth(0.5);
            naContainer.getChildren().add(rnaLabel);
        }
        
        return naContainer;
    }
    
    private Pane createGlycoproteins(List<Glycoprotein> glycoproteins) {
        Pane spikes = new Pane();
        Image spikeImage = ImageLoader.getSpikeProtein();
        
        int spikeCount = glycoproteins.size() * 6;
        
        for (int i = 0; i < spikeCount; i++) {
            double angle = i * 2 * Math.PI / spikeCount;
            double x = 30 + 30 * Math.cos(angle);
            double y = 30 + 30 * Math.sin(angle);
            
            if (spikeImage != null) {
                // Use spike image
                ImageView spike = new ImageView(spikeImage);
                spike.setFitWidth(12);
                spike.setFitHeight(12);
                spike.setX(x - 6);
                spike.setY(y - 6);
                spike.setRotate(Math.toDegrees(angle));
                spikes.getChildren().add(spike);
            } else {
                // Fallback to line drawing
                double x1 = 30 + 25 * Math.cos(angle);
                double y1 = 30 + 25 * Math.sin(angle);
                double x2 = 30 + 35 * Math.cos(angle);
                double y2 = 30 + 35 * Math.sin(angle);
                
                Color spikeColor = Color.web("#ff5252");
                
                Line spike = new Line(x1, y1, x2, y2);
                spike.setStroke(spikeColor);
                spike.setStrokeWidth(3);
                spikes.getChildren().add(spike);
                
                Circle spikeEnd = new Circle(x2, y2, 3);
                spikeEnd.setFill(Color.web("#ffeb3b"));
                spikes.getChildren().add(spikeEnd);
            }
        }
        
        return spikes;
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
            appendLog("\nInfection complete.\n");
        });
        
        mainAnimation.play();
    }
    
    private void addAttachmentAnimation() {
        // Speed varies by capsid shape (smaller shapes move faster)
        double duration = getAttachmentDuration();
        
        TranslateTransition moveToCell = new TranslateTransition(Duration.seconds(duration), virusContainer);
        moveToCell.setToX(280);
        moveToCell.setInterpolator(Interpolator.EASE_BOTH);
        
        // Add wobble for helical capsids
        if (virus.getCapsid().getShape().equalsIgnoreCase("helical")) {
            RotateTransition wobble = new RotateTransition(Duration.seconds(duration), virusContainer);
            wobble.setByAngle(360);
            wobble.setCycleCount(2);
            ParallelTransition combined = new ParallelTransition(moveToCell, wobble);
            mainAnimation.getChildren().add(combined);
        } else {
            mainAnimation.getChildren().add(moveToCell);
        }
        
        moveToCell.setOnFinished(e -> {
            appendLog("\n[ATTACHMENT]\n");
            appendLog("Capsid: " + virus.getCapsid().getShape() + "\n");
            appendLog("Genome: " + virus.getNucleicAcid().getType() + "\n");
            
            if (virus.isEnveloped()) {
                EnvelopedVirus envVirus = (EnvelopedVirus) virus;
                List<Glycoprotein> gps = envVirus.getLipidEnvelop().getGlycoproteins();
                appendLog("Enveloped with " + gps.size() + " glycoprotein(s)\n");
                
                if (envVirus.getLipidEnvelop().hasCompatibleReceptor(hostCell)) {
                    appendLog("Receptor binding successful\n");
                    animateReceptorBinding();
                }
            } else {
                appendLog("Non-enveloped - direct attachment\n");
            }
        });
        
        mainAnimation.getChildren().add(new PauseTransition(Duration.seconds(1.5)));
    }
    
    private void animateReceptorBinding() {
        // Highlight nearest receptors (left side)
        for (int i = 2; i < 6; i++) {  // Receptors 2-5 are on the left side nearest to virus
            if (i >= receptorMarkers.size()) continue;
            
            Circle receptor = receptorMarkers.get(i);
            
            // Find corresponding ImageView if using images
            javafx.scene.Node targetNode = receptor;
            for (javafx.scene.Node node : animationPane.getChildren()) {
                if (node instanceof ImageView) {
                    ImageView iv = (ImageView) node;
                    double dx = Math.abs(iv.getX() + 6 - receptor.getCenterX());
                    double dy = Math.abs(iv.getY() + 6 - receptor.getCenterY());
                    if (dx < 1 && dy < 1) {
                        targetNode = iv;
                        break;
                    }
                }
            }
            
            // Pulse animation on the visible node
            ScaleTransition pulse = new ScaleTransition(Duration.seconds(0.3), targetNode);
            pulse.setToX(2.0);
            pulse.setToY(2.0);
            pulse.setCycleCount(2);
            pulse.setAutoReverse(true);
            
            // Color change if it's a circle (not image)
            if (targetNode instanceof Circle) {
                FillTransition colorChange = new FillTransition(Duration.seconds(0.5), (Circle)targetNode);
                colorChange.setToValue(Color.web("#4caf50"));  // Green = bound
                ParallelTransition binding = new ParallelTransition(pulse, colorChange);
                binding.setDelay(Duration.seconds(i * 0.2));
                binding.play();
            } else {
                // Just pulse for images
                pulse.setDelay(Duration.seconds(i * 0.2));
                pulse.play();
            }
        }
    }
    
    private double getAttachmentDuration() {
        // Smaller/simpler shapes move faster
        switch (virus.getCapsid().getShape().toLowerCase()) {
            case "icosahedral": return 2.5;  // Larger, slower
            case "helical": return 2.0;      // Medium
            case "conical": return 1.8;      // Smaller, faster
            default: return 2.0;
        }
    }
    
    private void addEntryAnimation() {
        if (virus.isEnveloped()) {
            // Enveloped: smooth fusion, less shrinkage
            addEnvelopedEntryAnimation();
        } else {
            // Non-enveloped: dramatic dissolution at surface
            addNonEnvelopedEntryAnimation();
        }
    }
    
    private void addEnvelopedEntryAnimation() {
        ScaleTransition shrink = new ScaleTransition(Duration.seconds(1.5), virusContainer);
        shrink.setToX(0.8);  // Less shrinkage for enveloped
        shrink.setToY(0.8);
        
        TranslateTransition enter = new TranslateTransition(Duration.seconds(1.5), virusContainer);
        enter.setToX(320);
        
        // Add rotation for helical capsids
        if (virus.getCapsid().getShape().equalsIgnoreCase("helical")) {
            RotateTransition rotate = new RotateTransition(Duration.seconds(1.5), virusContainer);
            rotate.setByAngle(180);
            ParallelTransition entryAnim = new ParallelTransition(shrink, enter, rotate);
            mainAnimation.getChildren().add(entryAnim);
        } else {
            ParallelTransition entryAnim = new ParallelTransition(shrink, enter);
            mainAnimation.getChildren().add(entryAnim);
        }
        
        shrink.setOnFinished(e -> {
            appendLog("\n[ENTRY]\n");
            appendLog("Envelope fusion with membrane\n");
            appendLog("Capsid entering cytoplasm\n");
        });
        
        mainAnimation.getChildren().add(new PauseTransition(Duration.seconds(1)));
    }
    
    private void addNonEnvelopedEntryAnimation() {
        // More dramatic shrinkage for non-enveloped (capsid dissolves)
        ScaleTransition shrink = new ScaleTransition(Duration.seconds(1.8), virusContainer);
        shrink.setToX(0.5);  // More shrinkage - capsid breaking down
        shrink.setToY(0.5);
        
        TranslateTransition enter = new TranslateTransition(Duration.seconds(1.8), virusContainer);
        enter.setToX(300);  // Less movement - dissolves at surface
        
        // Add shaking effect to show dissolution
        Timeline shake = new Timeline(
            new KeyFrame(Duration.ZERO, 
                new KeyValue(virusContainer.rotateProperty(), 0)),
            new KeyFrame(Duration.seconds(0.1), 
                new KeyValue(virusContainer.rotateProperty(), 5)),
            new KeyFrame(Duration.seconds(0.2), 
                new KeyValue(virusContainer.rotateProperty(), -5)),
            new KeyFrame(Duration.seconds(0.3), 
                new KeyValue(virusContainer.rotateProperty(), 0))
        );
        shake.setCycleCount(6);
        
        ParallelTransition entryAnim = new ParallelTransition(shrink, enter, shake);
        
        entryAnim.setOnFinished(e -> {
            appendLog("\n[ENTRY]\n");
            appendLog("Capsid dissolving\n");
            appendLog("Genome release\n");
        });
        
        mainAnimation.getChildren().add(entryAnim);
        mainAnimation.getChildren().add(new PauseTransition(Duration.seconds(1)));
    }
    
    private void addInjectionAnimation() {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), virusContainer);
        fade.setToValue(0.3);
        
        TranslateTransition finalMove = new TranslateTransition(Duration.seconds(2), virusContainer);
        finalMove.setToX(340);
        
        ParallelTransition injection = new ParallelTransition(fade, finalMove);
        
        injection.setOnFinished(e -> {
            appendLog("\n[INJECTION]\n");
            String naType = virus.getNucleicAcid().getType();
            appendLog(naType + " genome released\n");
            appendLog("Host cell hijacked\n");
            
            // Cell reaction - intensity based on virus properties
            int pulseCount = virus.isEnveloped() ? 4 : 6;  // Non-enveloped more dramatic
            double pulseScale = virus.isEnveloped() ? 1.1 : 1.15;
            
            ScaleTransition pulse = new ScaleTransition(Duration.seconds(0.5), hostCellShape);
            pulse.setToX(pulseScale);
            pulse.setToY(pulseScale);
            pulse.setCycleCount(pulseCount);
            pulse.setAutoReverse(true);
            pulse.play();
            
            // Color change based on infection type
            FadeTransition cellColorChange = new FadeTransition(Duration.seconds(1), hostCellShape);
            if (naType.equalsIgnoreCase("RNA")) {
                hostCellShape.setFill(Color.web("#ffab91"));  // Orange tint for RNA
            } else {
                hostCellShape.setFill(Color.web("#ce93d8"));  // Purple tint for DNA
            }
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
        virusContainer.setTranslateX(0);
        virusContainer.setTranslateY(0);
        virusContainer.setScaleX(1);
        virusContainer.setScaleY(1);
        virusContainer.setOpacity(1);
        virusContainer.setRotate(0);
        
        // Reset cell
        hostCellShape.setScaleX(1);
        hostCellShape.setScaleY(1);
        hostCellShape.setFill(Color.web("#81c784"));
        
        // Reset receptors
        for (Circle receptor : receptorMarkers) {
            receptor.setScaleX(1);
            receptor.setScaleY(1);
            receptor.setFill(Color.web("#ffd54f"));
        }
        
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
    
    private String getReceptorForVirus(String virusName) {
        // For enveloped viruses, try to infer from glycoproteins
        if (virus.isEnveloped()) {
            EnvelopedVirus envVirus = (EnvelopedVirus) virus;
            List<Glycoprotein> gps = envVirus.getLipidEnvelop().getGlycoproteins();
            
            for (Glycoprotein gp : gps) {
                String gpName = gp.getName().toLowerCase();
                if (gpName.contains("gp120")) return "CD4";
                if (gpName.contains("spike")) return "ACE2";
                if (gpName.contains("hemagglutinin")) return "Sialic Acid";
            }
            return "Generic";
        } else {
            // Non-enveloped viruses use different receptors
            return virus.getCapsid().getShape().equalsIgnoreCase("icosahedral") ? "CAR" : "PVR";
        }
    }
    
}
