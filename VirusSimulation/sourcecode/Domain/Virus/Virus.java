package Domain.Virus;

import Domain.Host.HostCell;

/**
 * Abstract base class for all viruses
 * Contains common attributes and behavior for virus infection simulation
 */
public abstract class Virus {
    protected String name;
    protected NucleicAcid nucleicAcid;
    protected Capsid capsid;
    protected InfectionStrategy infectionStrategy;
    
    public Virus(String name, NucleicAcid nucleicAcid, Capsid capsid, InfectionStrategy infectionStrategy) {
        this.name = name;
        this.nucleicAcid = nucleicAcid;
        this.capsid = capsid;
        this.infectionStrategy = infectionStrategy;
    }
    
    public String getName() {
        return name;
    }
    
    public NucleicAcid getNucleicAcid() {
        return nucleicAcid;
    }
    
    public Capsid getCapsid() {
        return capsid;
    }
    
    public InfectionStrategy getInfectionStrategy() {
        return infectionStrategy;
    }
    
    /**
     * Determines if the virus has a lipid envelope
     * @return true if enveloped, false otherwise
     */
    public abstract boolean isEnveloped();
    
    /**
     * Simulates the complete infection process using the infection strategy
     * This method orchestrates the three phases: attach, enter, and inject
     * @param hostCell The target host cell to infect
     */
    public void infect(HostCell hostCell) {
        System.out.println("\n========================================");
        System.out.println("Starting infection simulation for: " + this.name);
        System.out.println("Virus type: " + (isEnveloped() ? "Enveloped" : "Non-enveloped"));
        System.out.println("========================================\n");
        
        // Phase 1: Attachment
        System.out.println("[PHASE 1: ATTACHMENT]");
        infectionStrategy.attach(this, hostCell);
        
        // Phase 2: Entry
        System.out.println("\n[PHASE 2: ENTRY]");
        infectionStrategy.enter(this, hostCell);
        
        // Phase 3: Nucleic Acid Injection
        System.out.println("\n[PHASE 3: NUCLEIC ACID INJECTION]");
        infectionStrategy.injectNucleicAcid(this, hostCell);
        
        System.out.println("\n========================================");
        System.out.println("Infection simulation completed!");
        System.out.println("========================================\n");
    }
    
    /**
     * Returns a description of the virus structure
     * @return String describing virus components
     */
    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append("Virus: ").append(name).append("\n");
        desc.append("Type: ").append(isEnveloped() ? "Enveloped" : "Non-enveloped").append("\n");
        desc.append("Nucleic Acid: ").append(nucleicAcid.getType()).append("\n");
        desc.append("Capsid Shape: ").append(capsid.getShape()).append("\n");
        
        if (this instanceof EnvelopedVirus) {
            EnvelopedVirus envVirus = (EnvelopedVirus) this;
            desc.append("Glycoproteins: ");
            for (Glycoprotein gp : envVirus.getLipidEnvelop().getGlycoproteins()) {
                desc.append(gp.getName()).append(" ");
            }
            desc.append("\n");
        }
        
        return desc.toString();
    }
}

