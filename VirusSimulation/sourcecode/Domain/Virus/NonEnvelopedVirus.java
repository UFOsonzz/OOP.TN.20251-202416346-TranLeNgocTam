package Domain.Virus;

/**
 * Abstract class representing non-enveloped (naked) viruses
 * These viruses lack a lipid envelope and infect cells directly
 * They dissolve their capsid to release nucleic acid into the host cell
 * Examples: Adenovirus, Poliovirus, Norovirus
 */
public abstract class NonEnvelopedVirus extends Virus {
    
    public NonEnvelopedVirus(String name, NucleicAcid nucleicAcid, Capsid capsid, 
                            InfectionStrategy infectionStrategy) {
        super(name, nucleicAcid, capsid, infectionStrategy);
    }
    
    @Override
    public boolean isEnveloped() {
        return false;
    }
}
