package Domain.Virus;
import java.util.List;

/**
 * Repository class that stores and manages collections of viruses
 * Organizes viruses by type: enveloped and non-enveloped
 */
public class VirusRepository {
    private List<Virus> nonEnvelopedViruses;
    private List<Virus> envelopedViruses;
    
    public VirusRepository(List<Virus> nonEnvelopedViruses, List<Virus> envelopedViruses) {
        this.nonEnvelopedViruses = nonEnvelopedViruses;
        this.envelopedViruses = envelopedViruses;
    }
    
    public List<Virus> getNonEnvelopedViruses() {
        return nonEnvelopedViruses;
    }
    
    public List<Virus> getEnvelopedViruses() {
        return envelopedViruses;
    }
    
    /**
     * Gets all viruses regardless of type
     */
    public List<Virus> getAllViruses() {
        List<Virus> allViruses = new java.util.ArrayList<>();
        allViruses.addAll(nonEnvelopedViruses);
        allViruses.addAll(envelopedViruses);
        return allViruses;
    }
    
    /**
     * Finds a virus by name
     */
    public Virus findVirusByName(String name) {
        for (Virus virus : getAllViruses()) {
            if (virus.getName().equalsIgnoreCase(name)) {
                return virus;
            }
        }
        return null;
    }
}
