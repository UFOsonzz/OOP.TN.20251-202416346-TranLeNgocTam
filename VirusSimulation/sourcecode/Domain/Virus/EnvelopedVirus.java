package Domain.Virus;

/**
 * Abstract class representing enveloped viruses
 * These viruses have a lipid envelope with glycoproteins
 * They use lock-key mechanism to infect host cells
 * Examples: HIV, SARS-CoV-2, Influenza
 */
public abstract class EnvelopedVirus extends Virus {
    protected LipidEnvelop lipidEnvelop;
    
    public EnvelopedVirus(String name, NucleicAcid nucleicAcid, Capsid capsid, 
                         InfectionStrategy infectionStrategy, LipidEnvelop lipidEnvelop) {
        super(name, nucleicAcid, capsid, infectionStrategy);
        this.lipidEnvelop = lipidEnvelop;
    }
    
    public LipidEnvelop getLipidEnvelop() {
        return lipidEnvelop;
    }
    
    @Override
    public boolean isEnveloped() {
        return true;
    }
}
