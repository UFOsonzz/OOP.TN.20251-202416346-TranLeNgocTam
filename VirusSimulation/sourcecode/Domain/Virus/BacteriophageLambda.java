package Domain.Virus;

/**
 * Bacteriophage Lambda (λ)
 * A non-enveloped virus that infects bacteria (E. coli)
 * Has an icosahedral head and a tail structure
 * Contains DNA genetic material
 * Used extensively in molecular biology research
 */
public class BacteriophageLambda extends NonEnvelopedVirus {
    
    // Auto-registration with VirusRepository
    static {
        VirusRepository.registerVirus(BacteriophageLambda.class);
    }
    
    public BacteriophageLambda(NucleicAcid nucleicAcid, Capsid capsid, InfectionStrategy infectionStrategy) {
        super("BacteriophageLambda", nucleicAcid, capsid, infectionStrategy);
    }

    public static BacteriophageLambda createDefault() {
        NucleicAcid nucleicAcid = new NucleicAcid("DNA");
        Capsid capsid = new Capsid("Icosahedral");
        DirectInfection strategy = new DirectInfection();
        return new BacteriophageLambda(nucleicAcid, capsid, strategy);
    }
}
