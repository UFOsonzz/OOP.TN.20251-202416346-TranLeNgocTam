package Domain.Virus;

/**
 * Adenovirus
 * A non-enveloped virus that causes respiratory infections
 * Infects cells directly without lock-key mechanism
 * Contains DNA genetic material in an icosahedral capsid
 */
public class AdenoVirus extends NonEnvelopedVirus {
    public AdenoVirus(NucleicAcid nucleicAcid, Capsid capsid, InfectionStrategy infectionStrategy) {
        super("AdenoVirus", nucleicAcid, capsid, infectionStrategy);
    }

    public static AdenoVirus createDefault() {
        NucleicAcid nucleicAcid = new NucleicAcid("DNA");
        Capsid capsid = new Capsid("Icosahedral");
        DirectInfection strategy = new DirectInfection();
        return new AdenoVirus(nucleicAcid, capsid, strategy);
    }
}
