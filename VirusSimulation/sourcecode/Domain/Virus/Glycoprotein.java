package Domain.Virus;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

// glyco -> receptor: gp120 (HIV), Spike protein (SARS-CoV-2), hemagglutinin (Influenza) <- mo rong

public class Glycoprotein {
    private String name;
    
    private static final Map<String, List<String>> COMPATIBLE_RECEPTORS = new HashMap<>();
    
    static {
        // HIV glycoproteins
        COMPATIBLE_RECEPTORS.put("gp120", Arrays.asList("CD4", "CCR5", "CXCR4"));
        
        // SARS-CoV-2 glycoproteins
        COMPATIBLE_RECEPTORS.put("Spike", Arrays.asList("ACE2"));
        
        // Influenza glycoproteins
        COMPATIBLE_RECEPTORS.put("Hemagglutinin", Arrays.asList("Sialic Acid"));
        COMPATIBLE_RECEPTORS.put("Neuraminidase", Arrays.asList("Sialic Acid"));
        
        // Herpes Simplex Virus glycoproteins
        COMPATIBLE_RECEPTORS.put("gB", Arrays.asList("Generic"));
        COMPATIBLE_RECEPTORS.put("gD", Arrays.asList("Generic"));
        
        // Measles Virus glycoproteins
        COMPATIBLE_RECEPTORS.put("H-protein", Arrays.asList("Generic"));
        COMPATIBLE_RECEPTORS.put("F-protein", Arrays.asList("Generic"));
        
        // Hepatitis B Virus glycoproteins
        COMPATIBLE_RECEPTORS.put("HBsAg", Arrays.asList("Generic"));
    }
    
    public Glycoprotein(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isCompatible(String receptorType) {
        List<String> receptors = COMPATIBLE_RECEPTORS.get(this.name);
        return receptors != null && receptors.contains(receptorType);
    }
}