package Domain.Virus;

/**
 * Herpes Simplex Virus (HSV)
 * An enveloped virus that causes cold sores and genital herpes
 * Uses gB, gC, gD, and gH glycoproteins for cell entry
 * Contains DNA genetic material in an icosahedral capsid
 */
public class HerpesVirus extends EnvelopedVirus {
    
    // Auto-registration with VirusRepository
    static {
        VirusRepository.registerVirus(HerpesVirus.class);
    }
    
    public HerpesVirus(NucleicAcid nucleicAcid, Capsid capsid, InfectionStrategy infectionStrategy, LipidEnvelop lipidEnvelop) {
        super("HerpesVirus", nucleicAcid, capsid, infectionStrategy, lipidEnvelop);
    }

    public static HerpesVirus createDefault() {
        NucleicAcid nucleicAcid = new NucleicAcid("DNA");
        Capsid capsid = new Capsid("Icosahedral");
        Glycoprotein gB = new Glycoprotein("gB");
        Glycoprotein gD = new Glycoprotein("gD");
        java.util.List<Glycoprotein> glycoproteins = java.util.Arrays.asList(gB, gD);
        LipidEnvelop envelope = new LipidEnvelop(glycoproteins);
        LockKeyInfection strategy = new LockKeyInfection();
        return new HerpesVirus(nucleicAcid, capsid, strategy, envelope);
    }
}
