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
            // Delegate compatibility check to the Glycoprotein itself
            if (gp.isCompatible(receptorType)) {
                return true;
            }
        }
        
        return false;
    }
}
