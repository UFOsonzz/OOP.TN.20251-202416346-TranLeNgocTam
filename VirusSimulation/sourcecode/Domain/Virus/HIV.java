package Domain.Virus;

/**
 * Human Immunodeficiency Virus (HIV)
 * An enveloped virus that attacks the immune system
 * Uses gp120 and gp41 glycoproteins to bind to CD4 receptors on T-cells
 * Contains RNA genetic material in a conical capsid
 */
public class HIV extends EnvelopedVirus {
    public HIV(NucleicAcid nucleicAcid, Capsid capsid, InfectionStrategy infectionStrategy, LipidEnvelop lipidEnvelop) {
        super("HIV", nucleicAcid, capsid, infectionStrategy, lipidEnvelop);
    }

    public static HIV createDefault() {
        NucleicAcid nucleicAcid = new NucleicAcid("RNA");
        Capsid capsid = new Capsid("Conical");
        Glycoprotein gp120 = new Glycoprotein("gp120");
        Glycoprotein gp41 = new Glycoprotein("gp41");
        java.util.List<Glycoprotein> glycoproteins = java.util.Arrays.asList(gp120, gp41);
        LipidEnvelop envelope = new LipidEnvelop(glycoproteins);
        LockKeyInfection strategy = new LockKeyInfection();
        return new HIV(nucleicAcid, capsid, strategy, envelope);
    }
}
