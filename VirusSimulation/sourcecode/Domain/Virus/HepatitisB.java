package Domain.Virus;

/**
 * Hepatitis B Virus (HBV)
 * An enveloped virus that infects the liver
 * Uses HBsAg (Hepatitis B surface antigen) for cell entry
 * Contains DNA genetic material in an icosahedral capsid
 */
public class HepatitisB extends EnvelopedVirus {
    
    // Auto-registration with VirusRepository
    static {
        VirusRepository.registerVirus(HepatitisB.class);
    }
    
    public HepatitisB(NucleicAcid nucleicAcid, Capsid capsid, InfectionStrategy infectionStrategy, LipidEnvelop lipidEnvelop) {
        super("HepatitisB", nucleicAcid, capsid, infectionStrategy, lipidEnvelop);
    }

    public static HepatitisB createDefault() {
        NucleicAcid nucleicAcid = new NucleicAcid("DNA");
        Capsid capsid = new Capsid("Icosahedral");
        Glycoprotein hbsAg = new Glycoprotein("HBsAg");
        java.util.List<Glycoprotein> glycoproteins = java.util.Arrays.asList(hbsAg);
        LipidEnvelop envelope = new LipidEnvelop(glycoproteins);
        LockKeyInfection strategy = new LockKeyInfection();
        return new HepatitisB(nucleicAcid, capsid, strategy, envelope);
    }
}
