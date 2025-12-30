package Domain.Virus;

import java.util.Arrays;
import java.util.List;

/**
 * Factory class to create virus instances with proper configuration
 * This simplifies virus creation and ensures consistency
 */
public class VirusFactory {
    
    /**
     * Creates an HIV virus with appropriate configuration
     * HIV is an enveloped virus with RNA genome
     */
    public static HIV createHIV() {
        NucleicAcid nucleicAcid = new NucleicAcid("RNA");
        Capsid capsid = new Capsid("Conical");
        
        // HIV uses gp120 and gp41 glycoproteins to bind to CD4 receptors
        Glycoprotein gp120 = new Glycoprotein("gp120");
        Glycoprotein gp41 = new Glycoprotein("gp41");
        List<Glycoprotein> glycoproteins = Arrays.asList(gp120, gp41);
        
        LipidEnvelop envelope = new LipidEnvelop(glycoproteins);
        LockKeyInfection strategy = new LockKeyInfection();
        
        return new HIV(nucleicAcid, capsid, strategy, envelope);
    }
    
    /**
     * Creates a SARS-CoV-2 virus with appropriate configuration
     * SARS-CoV-2 is an enveloped virus with RNA genome
     */
    public static SARSCoV2 createSARSCoV2() {
        NucleicAcid nucleicAcid = new NucleicAcid("RNA");
        Capsid capsid = new Capsid("Helical");
        
        // SARS-CoV-2 uses Spike protein to bind to ACE2 receptors
        Glycoprotein spike = new Glycoprotein("Spike");
        List<Glycoprotein> glycoproteins = Arrays.asList(spike);
        
        LipidEnvelop envelope = new LipidEnvelop(glycoproteins);
        LockKeyInfection strategy = new LockKeyInfection();
        
        return new SARSCoV2(nucleicAcid, capsid, strategy, envelope);
    }
    
    /**
     * Creates an Adenovirus with appropriate configuration
     * Adenovirus is a non-enveloped virus with DNA genome
     */
    public static AdenoVirus createAdenoVirus() {
        NucleicAcid nucleicAcid = new NucleicAcid("DNA");
        Capsid capsid = new Capsid("Icosahedral");
        DirectInfection strategy = new DirectInfection();
        
        return new AdenoVirus(nucleicAcid, capsid, strategy);
    }
    
    /**
     * Creates a Poliovirus with appropriate configuration
     * Poliovirus is a non-enveloped virus with RNA genome
     */
    public static PolioVirus createPolioVirus() {
        NucleicAcid nucleicAcid = new NucleicAcid("RNA");
        Capsid capsid = new Capsid("Icosahedral");
        DirectInfection strategy = new DirectInfection();
        
        return new PolioVirus(nucleicAcid, capsid, strategy);
    }
    
    /**
     * Creates a VirusRepository with all available viruses
     */
    public static VirusRepository createVirusRepository() {
        List<Virus> envelopedViruses = Arrays.asList(
            createHIV(),
            createSARSCoV2()
        );
        
        List<Virus> nonEnvelopedViruses = Arrays.asList(
            createAdenoVirus(),
            createPolioVirus()
        );
        
        return new VirusRepository(nonEnvelopedViruses, envelopedViruses);
    }
}
