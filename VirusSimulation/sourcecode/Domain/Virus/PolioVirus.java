package Domain.Virus;

/**
 * Poliovirus
 * A non-enveloped virus that causes poliomyelitis
 * Infects cells directly without lock-key mechanism
 * Contains RNA genetic material in an icosahedral capsid
 */
public class PolioVirus extends NonEnvelopedVirus {
    
    // Auto-registration with VirusRepository - no need to modify Repository when adding new virus!
    static {
        VirusRepository.registerVirus(PolioVirus.class);
    }
    
    public PolioVirus(NucleicAcid nucleicAcid, Capsid capsid, InfectionStrategy infectionStrategy) {
        super("PolioVirus", nucleicAcid, capsid, infectionStrategy);
    }

    public static PolioVirus createDefault() {
        NucleicAcid nucleicAcid = new NucleicAcid("RNA");
        Capsid capsid = new Capsid("Icosahedral");
        DirectInfection strategy = new DirectInfection();
        return new PolioVirus(nucleicAcid, capsid, strategy);
    }
}
