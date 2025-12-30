package Domain.Virus;
import Domain.Host.HostCell;

/**
 * DirectInfection strategy for non-enveloped viruses
 * These viruses attach directly to the host cell, dissolve their capsid,
 * and release nucleic acid directly into the cell.
 */
public class DirectInfection implements InfectionStrategy {
   
    @Override
    public void attach(Virus virus, HostCell hostCell) {
        System.out.println(virus.getName() + " is attaching directly to the host cell surface...");
        System.out.println("Capsid proteins bind to cell membrane receptors.");
    }

    @Override
    public void enter(Virus virus, HostCell hostCell) {
        System.out.println(virus.getName() + " is entering the host cell...");
        System.out.println("The capsid is being dissolved at the cell surface.");
        System.out.println("Capsid shape: " + virus.getCapsid().getShape());
    }
  
    @Override
    public void injectNucleicAcid(Virus virus, HostCell hostCell) {
        System.out.println("Injecting nucleic acid directly into the host cell...");
        System.out.println("Nucleic acid type: " + virus.getNucleicAcid().getType());
        System.out.println("The viral genetic material is now inside the host cell!");
        System.out.println("Infection process completed for " + virus.getName());
    }
}
