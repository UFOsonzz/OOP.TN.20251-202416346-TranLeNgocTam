package Domain.Virus;

/**
 * Represents the protein shell that protects viral genetic material
 * Different viruses have different capsid shapes (icosahedral, helical, conical, etc.)
 */
public class Capsid {
    private String shape; // e.g., "Icosahedral", "Helical", "Conical"
    
    public Capsid(String shape) {
        this.shape = shape;
    }
    
    public String getShape() {
        return shape;
    }
}
