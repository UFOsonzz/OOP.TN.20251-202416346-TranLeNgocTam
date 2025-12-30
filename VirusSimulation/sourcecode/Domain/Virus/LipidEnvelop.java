package Domain.Virus;
import java.util.List;
import Domain.Host.HostCell;

/**
 * LipidEnvelop contains glycoproteins that act as keys
 * to bind with specific receptors on host cells
 */
public class LipidEnvelop {
    private List<Glycoprotein> glycoproteins;
    
    public LipidEnvelop(List<Glycoprotein> glycoproteins) {
        this.glycoproteins = glycoproteins;
    }
    
    public List<Glycoprotein> getGlycoproteins() {
        return glycoproteins;
    }
    
    /**
     * Checks if any glycoprotein can bind to the host cell receptor
     * This implements the lock-key mechanism
     */
    public Boolean hasCompatibleReceptor(HostCell hostCell) {
        if (hostCell == null || hostCell.getReceptor() == null) {
            return false;
        }
        
        String receptorType = hostCell.getReceptor().getType();
        
        // Check if any glycoprotein matches the receptor type
        for (Glycoprotein gp : glycoproteins) {
            // Matching logic: glycoprotein name should be compatible with receptor type
            // For example: "CD4" glycoprotein binds to "CD4" receptor
            // "Spike" glycoprotein binds to "ACE2" receptor
            if (isCompatible(gp.getName(), receptorType)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Helper method to determine compatibility between glycoprotein and receptor
     */
    private boolean isCompatible(String glycoproteinName, String receptorType) {
        // HIV: gp120 binds to CD4 receptor
        if (glycoproteinName.equalsIgnoreCase("gp120") && receptorType.equalsIgnoreCase("CD4")) {
            return true;
        }
        
        // SARS-CoV-2: Spike protein binds to ACE2 receptor
        if (glycoproteinName.equalsIgnoreCase("Spike") && receptorType.equalsIgnoreCase("ACE2")) {
            return true;
        }
        
        // Generic matching: if names match
        if (glycoproteinName.equalsIgnoreCase(receptorType)) {
            return true;
        }
        
        return false;
    }
}
