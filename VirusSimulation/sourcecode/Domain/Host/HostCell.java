package Domain.Host;

/**
 * Represents a host cell that can be infected by viruses
 * Each cell has specific receptors on its membrane that viruses can bind to
 */
public class HostCell {
    private Receptor receptor;
    
    public HostCell(Receptor receptor) {
        this.receptor = receptor;
    }
    
    public Receptor getReceptor() {
        return receptor;
    }
}
