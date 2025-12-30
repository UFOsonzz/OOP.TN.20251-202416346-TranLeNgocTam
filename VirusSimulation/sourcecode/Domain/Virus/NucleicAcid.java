package Domain.Virus;

/**
 * Represents the genetic material of a virus
 * Can be either DNA or RNA
 */
public class NucleicAcid {
    private String type; // "DNA" or "RNA"
    
    public NucleicAcid(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return type;
    }
}

