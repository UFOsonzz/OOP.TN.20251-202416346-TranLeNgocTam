package Domain.Virus;

/**
 * Tobacco Mosaic Virus (TMV)
 * A non-enveloped plant virus with helical capsid
 * One of the first viruses ever discovered (1892)
 * Infects tobacco and other plants, causing mosaic-like patterns on leaves
 * Contains RNA genetic material in a helical capsid
 */
public class TobaccoMosaicVirus extends NonEnvelopedVirus {
    
    // Auto-registration with VirusRepository
    static {
        VirusRepository.registerVirus(TobaccoMosaicVirus.class);
    }
    
    public TobaccoMosaicVirus(NucleicAcid nucleicAcid, Capsid capsid, InfectionStrategy infectionStrategy) {
        super("TobaccoMosaicVirus", nucleicAcid, capsid, infectionStrategy);
    }

    public static TobaccoMosaicVirus createDefault() {
        NucleicAcid nucleicAcid = new NucleicAcid("RNA");
        Capsid capsid = new Capsid("Helical");
        DirectInfection strategy = new DirectInfection();
        return new TobaccoMosaicVirus(nucleicAcid, capsid, strategy);
    }
}
