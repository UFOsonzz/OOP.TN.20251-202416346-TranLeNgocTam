package Domain.Host;
public class HostCell {
    private Receptor receptor;
    
    public HostCell(Receptor receptor) {
        this.receptor = receptor;
    }
    
    public Receptor getReceptor() {
        return receptor;
    }
    
    @Override
    public String toString() {
        return "Host cell with " + receptor.toString();
    }
}
