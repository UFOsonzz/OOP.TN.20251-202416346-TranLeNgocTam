package Domain.Virus;
import java.util.List;
import Domain.Host.HostCell;

public class LipidEnvelop {
    private List<Glycoprotein> glycoproteins;
    
    public LipidEnvelop(List<Glycoprotein> glycoproteins) {
        this.glycoproteins = glycoproteins;
    }
    
    public List<Glycoprotein> getGlycoproteins() {
        return glycoproteins;
    }
    
    public Boolean hasCompatibleReceptor(HostCell hostCell) {
        
        String receptorType = hostCell.getReceptor().getType();
        // ton tai glyco match receptor
        for (Glycoprotein gp : glycoproteins) {
            if (gp.isCompatible(receptorType)) {
                return true;
            }
        }
        return false;
    }
}