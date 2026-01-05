package Domain.Virus;

/**
 * Measles Virus
 * An enveloped virus that causes measles (highly contagious)
 * Uses Hemagglutinin (H) and Fusion (F) glycoproteins
 * Contains RNA genetic material in a helical capsid
 */
public class MeaslesVirus extends EnvelopedVirus {
    
    // Auto-registration with VirusRepository
    static {
        VirusRepository.registerVirus(MeaslesVirus.class);
    }
    
    public MeaslesVirus(NucleicAcid nucleicAcid, Capsid capsid, InfectionStrategy infectionStrategy, LipidEnvelop lipidEnvelop) {
        super("MeaslesVirus", nucleicAcid, capsid, infectionStrategy, lipidEnvelop);
    }

    public static MeaslesVirus createDefault() {
        NucleicAcid nucleicAcid = new NucleicAcid("RNA");
        Capsid capsid = new Capsid("Helical");
        Glycoprotein hemagglutinin = new Glycoprotein("H-protein");
        Glycoprotein fusion = new Glycoprotein("F-protein");
        java.util.List<Glycoprotein> glycoproteins = java.util.Arrays.asList(hemagglutinin, fusion);
        LipidEnvelop envelope = new LipidEnvelop(glycoproteins);
        LockKeyInfection strategy = new LockKeyInfection();
        return new MeaslesVirus(nucleicAcid, capsid, strategy, envelope);
    }
}
