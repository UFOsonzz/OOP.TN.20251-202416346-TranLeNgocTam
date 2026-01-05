package Domain.Virus;

/**
 * Influenza Virus (Flu)
 * An enveloped virus that causes seasonal flu
 * Uses Hemagglutinin (HA) and Neuraminidase (NA) glycoproteins
 * Contains RNA genetic material in a helical capsid
 */
public class Influenza extends EnvelopedVirus {
    
    // Auto-registration with VirusRepository
    static {
        VirusRepository.registerVirus(Influenza.class);
    }
    
    public Influenza(NucleicAcid nucleicAcid, Capsid capsid, InfectionStrategy infectionStrategy, LipidEnvelop lipidEnvelop) {
        super("Influenza", nucleicAcid, capsid, infectionStrategy, lipidEnvelop);
    }

    public static Influenza createDefault() {
        NucleicAcid nucleicAcid = new NucleicAcid("RNA");
        Capsid capsid = new Capsid("Helical");
        Glycoprotein hemagglutinin = new Glycoprotein("Hemagglutinin");
        Glycoprotein neuraminidase = new Glycoprotein("Neuraminidase");
        java.util.List<Glycoprotein> glycoproteins = java.util.Arrays.asList(hemagglutinin, neuraminidase);
        LipidEnvelop envelope = new LipidEnvelop(glycoproteins);
        LockKeyInfection strategy = new LockKeyInfection();
        return new Influenza(nucleicAcid, capsid, strategy, envelope);
    }
}
