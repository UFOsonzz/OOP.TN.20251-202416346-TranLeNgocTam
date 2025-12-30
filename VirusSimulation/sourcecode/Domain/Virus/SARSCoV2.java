package Domain.Virus;

/**
 * SARS-CoV-2 (COVID-19 virus)
 * An enveloped coronavirus that causes respiratory illness
 * Uses Spike protein to bind to ACE2 receptors on lung cells
 * Contains RNA genetic material in a helical capsid
 */
public class SARSCoV2 extends EnvelopedVirus {
    public SARSCoV2(NucleicAcid nucleicAcid, Capsid capsid, InfectionStrategy infectionStrategy, LipidEnvelop lipidEnvelop) {
        super("SARS-CoV-2", nucleicAcid, capsid, infectionStrategy, lipidEnvelop);
    }

    public static SARSCoV2 createDefault() {
        NucleicAcid nucleicAcid = new NucleicAcid("RNA");
        Capsid capsid = new Capsid("Helical");
        Glycoprotein spike = new Glycoprotein("Spike");
        java.util.List<Glycoprotein> glycoproteins = java.util.Arrays.asList(spike);
        LipidEnvelop envelope = new LipidEnvelop(glycoproteins);
        LockKeyInfection strategy = new LockKeyInfection();
        return new SARSCoV2(nucleicAcid, capsid, strategy, envelope);
    }
}
