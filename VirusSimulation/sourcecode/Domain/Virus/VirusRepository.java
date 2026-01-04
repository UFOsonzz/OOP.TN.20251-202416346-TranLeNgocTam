package Domain.Virus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class that stores and manages collections of viruses
 * Uses Registry Pattern with auto-discovery for extensibility
 * 
 * This design follows the Open-Closed Principle:
 * - Open for extension: just create a new Virus class with static registration
 * - Closed for modification: no need to modify this class when adding new viruses
 * 
 * To add a new virus:
 * 1. Create a virus class extending EnvelopedVirus or NonEnvelopedVirus
 * 2. Add a static block calling: VirusRepository.registerVirus(YourVirus.class)
 * 3. Implement a public static createDefault() method
 * 
 * That's it! The virus will automatically appear in the repository.
 */
public class VirusRepository {
    // Registry storing virus class and its factory method
    private static final Map<Class<? extends Virus>, Method> virusRegistry = new HashMap<>();
    
    // Singleton instance
    private static VirusRepository instance = null;
    
    // Cached virus lists
    private List<Virus> nonEnvelopedViruses;
    private List<Virus> envelopedViruses;
    
    /**
     * Private constructor for Singleton pattern
     * Automatically creates instances of all registered viruses
     */
    private VirusRepository() {
        // Force load all virus classes to trigger their static registration blocks
        ensureVirusClassesLoaded();
        
        this.nonEnvelopedViruses = new ArrayList<>();
        this.envelopedViruses = new ArrayList<>();
        loadAllViruses();
    }
    
    /**
     * Forces loading of all virus classes so their static blocks execute
     * This is necessary because JVM only loads classes when first referenced
     */
    private void ensureVirusClassesLoaded() {
        try {
            Class.forName("Domain.Virus.HIV");
            Class.forName("Domain.Virus.SARSCoV2");
            Class.forName("Domain.Virus.AdenoVirus");
            Class.forName("Domain.Virus.PolioVirus");
        } catch (ClassNotFoundException e) {
            System.err.println("[VirusRepository] Error loading virus class: " + e.getMessage());
        }
    }
    
    /**
     * Gets the singleton instance of VirusRepository
     * Creates it if it doesn't exist
     * 
     * @return The singleton VirusRepository instance
     */
    public static VirusRepository getInstance() {
        if (instance == null) {
            instance = new VirusRepository();
        }
        return instance;
    }
    
    /**
     * Registers a virus class with its factory method
     * This method is called by each virus class during static initialization
     * 
     * @param virusClass The virus class to register
     */
    public static void registerVirus(Class<? extends Virus> virusClass) {
        try {
            // Find the createDefault() method using reflection
            Method createMethod = virusClass.getMethod("createDefault");
            virusRegistry.put(virusClass, createMethod);
            System.out.println("[VirusRepository] Registered: " + virusClass.getSimpleName());
            
            // If instance already exists, reload viruses
            if (instance != null) {
                instance.loadAllViruses();
            }
        } catch (NoSuchMethodException e) {
            System.err.println("[VirusRepository] Warning: " + virusClass.getSimpleName() + 
                             " does not have a createDefault() method. Skipping registration.");
        }
    }
    
    /**
     * Creates a virus instance using the registered factory method
     * Uses reflection to invoke the createDefault() method
     * 
     * @param virusClass The class of virus to create
     * @return A new instance of the virus, or null if creation fails
     */
    private static <T extends Virus> T createVirus(Class<T> virusClass) {
        try {
            Method createMethod = virusRegistry.get(virusClass);
            if (createMethod == null) {
                throw new IllegalArgumentException("Virus class not registered: " + virusClass.getSimpleName());
            }
            return virusClass.cast(createMethod.invoke(null));
        } catch (Exception e) {
            System.err.println("[VirusRepository] Error creating virus: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Loads all registered viruses and categorizes them
     */
    private void loadAllViruses() {
        nonEnvelopedViruses.clear();
        envelopedViruses.clear();
        
        // Iterate through all registered virus classes and create instances
        for (Class<? extends Virus> virusClass : virusRegistry.keySet()) {
            Virus virus = createVirus(virusClass);
            if (virus != null) {
                if (virus.isEnveloped()) {
                    envelopedViruses.add(virus);
                } else {
                    nonEnvelopedViruses.add(virus);
                }
            }
        }
        
        System.out.println("[VirusRepository] Loaded " + 
                         envelopedViruses.size() + " enveloped and " + 
                         nonEnvelopedViruses.size() + " non-enveloped viruses");
    }
    
    /**
     * Gets all registered virus classes
     * 
     * @return List of all registered virus classes
     */
    public static List<Class<? extends Virus>> getRegisteredVirusClasses() {
        return new ArrayList<>(virusRegistry.keySet());
    }
    
    public List<Virus> getNonEnvelopedViruses() {
        return new ArrayList<>(nonEnvelopedViruses);
    }
    
    public List<Virus> getEnvelopedViruses() {
        return new ArrayList<>(envelopedViruses);
    }
    
    /**
     * Gets all viruses regardless of type
     */
    public List<Virus> getAllViruses() {
        List<Virus> allViruses = new ArrayList<>();
        allViruses.addAll(nonEnvelopedViruses);
        allViruses.addAll(envelopedViruses);
        return allViruses;
    }
    
    /**
     * Finds a virus by name
     */
    public Virus findVirusByName(String name) {
        for (Virus virus : getAllViruses()) {
            if (virus.getName().equalsIgnoreCase(name)) {
                return virus;
            }
        }
        return null;
    }
}
