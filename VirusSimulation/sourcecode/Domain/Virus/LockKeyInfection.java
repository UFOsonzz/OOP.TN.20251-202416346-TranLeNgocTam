package Domain.Virus;
import Domain.Host.HostCell;

/**
 * LockKeyInfection strategy for enveloped viruses
 * These viruses use glycoproteins (keys) to bind to specific receptors (locks)
 * on the host cell membrane before fusing and injecting their genetic material.
 */
public class LockKeyInfection implements InfectionStrategy {
    
    @Override
    public void attach(Virus virus, HostCell hostCell) {
        System.out.println(virus.getName() + " is approaching the host cell...");
        
        if (virus instanceof EnvelopedVirus) {
            EnvelopedVirus envVirus = (EnvelopedVirus) virus;
            System.out.println("Checking lock-key compatibility...");
            
            if (envVirus.getLipidEnvelop().hasCompatibleReceptor(hostCell)) {
                System.out.println("Lock-key match found!");
                System.out.print("Glycoproteins binding to receptor: ");
                for (Glycoprotein gp : envVirus.getLipidEnvelop().getGlycoproteins()) {
                    System.out.print(gp.getName() + " ");
                }
                System.out.println();
                System.out.println("Host cell receptor type: " + hostCell.getReceptor().getType());
            } else {
                System.out.println("Warning: No compatible receptor found. Infection may fail.");
            }
        }
    }
    
    @Override
    public void enter(Virus virus, HostCell hostCell) {
        System.out.println(virus.getName() + " is fusing with the host cell membrane...");
        System.out.println("Lipid envelope merges with cell membrane.");
        System.out.println("Capsid with nucleic acid enters the cell cytoplasm.");
        System.out.println("Capsid shape: " + virus.getCapsid().getShape());
    }
  
    @Override
    public void injectNucleicAcid(Virus virus, HostCell hostCell) {
        System.out.println("Releasing viral genetic material into the host cell...");
        System.out.println("Capsid uncoating inside the cell.");
        System.out.println("Nucleic acid type: " + virus.getNucleicAcid().getType());
        System.out.println("The viral genome is now ready for replication!");
        System.out.println("Infection process completed for " + virus.getName());
    }
}
