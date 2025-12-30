package Domain;

import Domain.Host.HostCell;
import Domain.Host.Receptor;
import Domain.Virus.*;

/**
 * Demonstration class to show how the virus simulation works
 * This class demonstrates the infection process for different virus types
 */
public class VirusSimulationDemo {
    
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("   VIRUS INFECTION SIMULATION DEMO");
        System.out.println("==============================================\n");
        
        // Create virus repository with all viruses
        VirusRepository repository = VirusFactory.createVirusRepository();
        
        // Demo 1: Non-Enveloped Viruses
        demonstrateNonEnvelopedViruses(repository);
        
        // Demo 2: Enveloped Viruses
        demonstrateEnvelopedViruses(repository);
        
        System.out.println("\n==============================================");
        System.out.println("   SIMULATION COMPLETED");
        System.out.println("==============================================");
    }
    
    /**
     * Demonstrates infection process for non-enveloped viruses
     */
    private static void demonstrateNonEnvelopedViruses(VirusRepository repository) {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  NON-ENVELOPED VIRUSES DEMONSTRATION      ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        // Create a generic host cell with a receptor
        HostCell hostCell = new HostCell(new Receptor("CAR")); // Coxsackievirus and Adenovirus Receptor
        
        for (Virus virus : repository.getNonEnvelopedViruses()) {
            System.out.println("\n--- " + virus.getName() + " Information ---");
            System.out.println(virus.getDescription());
            
            // Simulate infection
            virus.infect(hostCell);
            
            waitForAnimation();
        }
    }
    
    /**
     * Demonstrates infection process for enveloped viruses
     */
    private static void demonstrateEnvelopedViruses(VirusRepository repository) {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  ENVELOPED VIRUSES DEMONSTRATION          ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        // Demo HIV infection
        System.out.println("\n=== HIV Infection Scenario ===");
        HostCell cd4Cell = new HostCell(new Receptor("CD4")); // T-cell with CD4 receptor
        Virus hiv = repository.getEnvelopedViruses().get(0);
        
        System.out.println("\n--- " + hiv.getName() + " Information ---");
        System.out.println(hiv.getDescription());
        hiv.infect(cd4Cell);
        
        waitForAnimation();
        
        // Demo SARS-CoV-2 infection
        System.out.println("\n=== SARS-CoV-2 Infection Scenario ===");
        HostCell lungCell = new HostCell(new Receptor("ACE2")); // Lung cell with ACE2 receptor
        Virus sarscov2 = repository.getEnvelopedViruses().get(1);
        
        System.out.println("\n--- " + sarscov2.getName() + " Information ---");
        System.out.println(sarscov2.getDescription());
        sarscov2.infect(lungCell);
        
        waitForAnimation();
        
        // Demo failed infection (incompatible receptor)
        System.out.println("\n=== Failed Infection Scenario (Incompatible Receptor) ===");
        HostCell incompatibleCell = new HostCell(new Receptor("Unknown")); // Cell with unknown receptor
        System.out.println("\n--- Attempting HIV infection on incompatible cell ---");
        hiv.infect(incompatibleCell);
    }
    
    /**
     * Simulates animation delay for better visualization
     */
    private static void waitForAnimation() {
        try {
            Thread.sleep(1000); // 1 second pause between demonstrations
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Alternative demo method that allows user to select specific viruses
     */
    public static void demonstrateSpecificVirus(Virus virus, HostCell hostCell) {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  VIRUS INFECTION DEMONSTRATION            ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        System.out.println("--- Virus Information ---");
        System.out.println(virus.getDescription());
        
        System.out.println("--- Host Cell Information ---");
        System.out.println("Receptor Type: " + hostCell.getReceptor().getType());
        
        virus.infect(hostCell);
    }
}
