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
        System.out.println("\n--- Starting infection: " + this.name + " (" + (isEnveloped() ? "Enveloped" : "Non-enveloped") + ") ---");
        
        // Phase 1: Attachment
        System.out.println("\n[PHASE 1: ATTACHMENT]");
        infectionStrategy.attach(this, hostCell);
        
        // Phase 2: Entry
        System.out.println("\n[PHASE 2: ENTRY]");
        infectionStrategy.enter(this, hostCell);
        
        // Phase 3: Nucleic Acid Injection
        System.out.println("\n[PHASE 3: NUCLEIC ACID INJECTION]");
        infectionStrategy.injectNucleicAcid(this, hostCell);
        
        System.out.println("\n--- Infection completed! ---\n");
    }
    
    /**
     * Returns a description of the virus structure
     * @return String describing virus components
     */
    public String getDescription() {
        String desc = "Virus: " + name + "\n";
        desc += "Type: " + (isEnveloped() ? "Enveloped" : "Non-enveloped") + "\n";
        desc += "Nucleic Acid: " + nucleicAcid.getType() + "\n";
        desc += "Capsid Shape: " + capsid.getShape() + "\n";
        
        if (this instanceof EnvelopedVirus) {
            EnvelopedVirus envVirus = (EnvelopedVirus) this;
            desc += "Glycoproteins: ";
            for (Glycoprotein gp : envVirus.getLipidEnvelop().getGlycoproteins()) {
                desc += gp.getName() + " ";
            }
            desc += "\n";
        }
        
        return desc;
    }
}

