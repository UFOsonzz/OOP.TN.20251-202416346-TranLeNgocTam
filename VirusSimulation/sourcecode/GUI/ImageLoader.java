package GUI;

import javafx.scene.image.Image;
import java.io.InputStream;

/**
 * Utility class for loading images from resources folder
 * Makes it easy to use images instead of hardcoded shapes
 */
public class ImageLoader {
    
    private static final String BASE_PATH = "/resources/images/";
    
    // Backgrounds
    public static Image getMainMenuBackground() {
        return loadImage("backgrounds/main_menu_bg.png");
    }
    
    public static Image getSimulationBackground() {
        return loadImage("backgrounds/simulation_bg.png");
    }
    
    public static Image getCellTexture() {
        return loadImage("backgrounds/cell_texture.png");
    }
    
    /**
     * Get background image by name (generic method)
     */
    public static Image getBackgroundImage(String name) {
        switch (name) {
            case "menu_background":
                return getMainMenuBackground();
            case "simulation_background":
                return getSimulationBackground();
            default:
                return null;
        }
    }
    
    /**
     * Get texture image by name (generic method)
     */
    public static Image getTexture(String name) {
        switch (name) {
            case "cell_texture":
                return getCellTexture();
            default:
                return null;
        }
    }
    
    // Capsids
    public static Image getCapsidByShape(String shape) {
        if (shape == null) {
            return loadImage("viruses/capsid_icosahedral.png");
        }
        
        String normalized = shape.toLowerCase().trim();
        
        switch (normalized) {
            case "icosahedral":
                return loadImage("viruses/capsid_icosahedral.png");
            case "helical":
                return loadImage("viruses/capsid_helical.png");
            case "conical":
                return loadImage("viruses/capsid_conical.png");
            default:
                return loadImage("viruses/capsid_icosahedral.png");
        }
    }
    
    public static Image getEnvelope() {
        return loadImage("viruses/envelope.png");
    }
    
    public static Image getSpikeProtein() {
        return loadImage("viruses/spike_protein.png");
    }
    
    // Nucleic Acids
    public static Image getNucleicAcidByType(String type) {
        if (type.equalsIgnoreCase("DNA")) {
            return loadImage("nucleic_acids/dna_helix.png");
        } else {
            return loadImage("nucleic_acids/rna_strand.png");
        }
    }
    
    // Host Cell
    public static Image getHostCell() {
        return loadImage("cells/host_cell.png");
    }
    
    public static Image getReceptorByType(String type) {
        switch (type.toUpperCase()) {
            case "CD4":
                return loadImage("cells/receptor_cd4.png");
            case "ACE2":
                return loadImage("cells/receptor_ace2.png");
            case "CAR":
                return loadImage("cells/receptor_car.png");
            default:
                return loadImage("cells/receptor_car.png");
        }
    }
    
    public static Image getCellMembrane() {
        return loadImage("cells/cell_membrane.png");
    }
    
    // UI Icons
    public static Image getPlayButton() {
        return loadImage("icons/play_button.png");
    }
    
    public static Image getResetButton() {
        return loadImage("icons/reset_button.png");
    }
    
    public static Image getBackButton() {
        return loadImage("icons/back_button.png");
    }
    
    public static Image getHelpIcon() {
        return loadImage("icons/help_icon.png");
    }
    
    /**
     * Load image from resources folder
     * Returns null if image not found (fallback to hardcoded shapes)
     */
    private static Image loadImage(String relativePath) {
        try {
            String fullPath = BASE_PATH + relativePath;
            InputStream stream = ImageLoader.class.getResourceAsStream(fullPath);
            
            if (stream != null) {
                System.out.println("✓ Loaded image: " + fullPath);
                return new Image(stream);
            } else {
                System.err.println("✗ Image not found: " + fullPath);
                return null;
            }
        } catch (Exception e) {
            System.err.println("✗ Error loading image: " + relativePath);
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Check if image exists (for fallback logic)
     */
    public static boolean imageExists(String relativePath) {
        try {
            InputStream stream = ImageLoader.class.getResourceAsStream(BASE_PATH + relativePath);
            return stream != null;
        } catch (Exception e) {
            return false;
        }
    }
}
