package Domain.Virus;

/**
 * Represents a glycoprotein on the lipid envelope of enveloped viruses
 * Glycoproteins act as "keys" that bind to specific receptors ("locks") on host cells
 * Examples: gp120 (HIV), Spike protein (SARS-CoV-2)
 */
public class Glycoprotein {
    private String name;
    
    public Glycoprotein(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * Checks if this glycoprotein is compatible with a given receptor type
     * Implements the lock-key mechanism at the molecular level
     * @param receptorType The type of receptor on the host cell
     * @return true if this glycoprotein can bind to the receptor
     */
    public boolean isCompatible(String receptorType) {
        if (receptorType == null) {
            return false;
        }
        
        // HIV: gp120 binds to CD4 receptor
        if (this.name.equalsIgnoreCase("gp120") && receptorType.equalsIgnoreCase("CD4")) {
            return true;
        }
        
        // SARS-CoV-2: Spike protein binds to ACE2 receptor
        if (this.name.equalsIgnoreCase("Spike") && receptorType.equalsIgnoreCase("ACE2")) {
            return true;
        }
        
        // Generic matching: if names match
        if (this.name.equalsIgnoreCase(receptorType)) {
            return true;
        }
        
        return false;
    }
}
