package Domain.Host;

/**
 * Represents a receptor on the host cell membrane
 * Receptors act as "locks" that specific viral glycoproteins ("keys") can bind to
 * Examples: CD4 (for HIV), ACE2 (for SARS-CoV-2), CAR (for Adenovirus)
 */
public class Receptor {
    private String type;
    
    public Receptor(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return type + " receptor";
    }
}
