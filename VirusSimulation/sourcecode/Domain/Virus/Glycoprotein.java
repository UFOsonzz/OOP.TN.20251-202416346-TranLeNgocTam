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
    
    @Override
    public String toString() {
        return name;
    }
}
