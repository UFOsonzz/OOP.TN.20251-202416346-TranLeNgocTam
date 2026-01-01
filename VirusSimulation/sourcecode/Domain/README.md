# Virus Simulation Domain Layer

This package contains the complete domain model for the Virus Infection Simulation project.

## Package Structure

### Domain.Host
- **HostCell.java** - Represents a host cell that can be infected by viruses
- **Receptor.java** - Represents cell surface receptors that viruses bind to

### Domain.Virus
- **Virus.java** - Abstract base class for all viruses
- **EnvelopedVirus.java** - Abstract class for viruses with lipid envelopes
- **NonEnvelopedVirus.java** - Abstract class for viruses without lipid envelopes

#### Virus Components
- **NucleicAcid.java** - Represents viral genetic material (DNA or RNA)
- **Capsid.java** - Protein shell protecting the nucleic acid
- **LipidEnvelop.java** - Lipid envelope containing glycoproteins (enveloped viruses only)
- **Glycoprotein.java** - Surface proteins that bind to cell receptors

#### Infection Strategies (Strategy Pattern)
- **InfectionStrategy.java** - Interface defining infection process
- **DirectInfection.java** - Strategy for non-enveloped viruses
- **LockKeyInfection.java** - Strategy for enveloped viruses using lock-key mechanism

#### Concrete Virus Implementations

**Enveloped Viruses:**
- **HIV.java** - Human Immunodeficiency Virus
  - RNA virus with conical capsid
  - Uses gp120/gp41 to bind CD4 receptors
  
- **SARSCoV2.java** - COVID-19 virus
  - RNA virus with helical capsid
  - Uses Spike protein to bind ACE2 receptors

**Non-Enveloped Viruses:**
- **AdenoVirus.java** - Adenovirus
  - DNA virus with icosahedral capsid
  - Direct infection mechanism
  
- **PolioVirus.java** - Poliovirus
  - RNA virus with icosahedral capsid
  - Direct infection mechanism

#### Helper Classes
- **VirusRepository.java** - Singleton repository using Registry Pattern for managing virus collections with auto-discovery
- **VirusSimulationDemo.java** - Demo class showing infection simulations

## Key OOP Concepts Demonstrated

### 1. Encapsulation
- All virus components (nucleic acid, capsid, envelope) are encapsulated
- Private fields with public getters

### 2. Inheritance
- Base `Virus` class with specialized `EnvelopedVirus` and `NonEnvelopedVirus`
- Concrete virus classes (HIV, SARS-CoV-2, etc.) inherit from abstract parents

### 3. Polymorphism
- `InfectionStrategy` interface allows different infection mechanisms
- `infect()` method works with any virus type
- Different viruses demonstrate unique behaviors

### 4. Strategy Pattern
- Infection behavior is delegated to strategy objects
- `DirectInfection` for non-enveloped viruses
- `LockKeyInfection` for enveloped viruses

### 5. Singleton Pattern + Registry Pattern
- `VirusRepository` is a Singleton that manages all viruses
- Uses Registry Pattern with Reflection for auto-discovery
- Viruses self-register using static initialization blocks
- Repository automatically discovers and creates registered viruses
- Extensible design - no need to modify repository when adding viruses

### 6. Open-Closed Principle (SOLID)
- System is open for extension but closed for modification
- Adding new viruses requires NO changes to existing code
- Just create a new virus class with static registration block

## How Viruses Work

### Non-Enveloped Viruses (DirectInfection)
1. **Attachment**: Capsid proteins bind directly to cell membrane
2. **Entry**: Capsid dissolves at cell surface
3. **Injection**: Nucleic acid released directly into cell

### Enveloped Viruses (LockKeyInfection)
1. **Attachment**: Glycoproteins (keys) bind to specific receptors (locks)
2. **Entry**: Lipid envelope fuses with cell membrane
3. **Injection**: Capsid enters cell, then releases nucleic acid

## Usage Example

```java
// Get the singleton repository instance
VirusRepository repository = VirusRepository.getInstance();

// Create a host cell with CD4 receptor
HostCell hostCell = new HostCell(new Receptor("CD4"));

// Get HIV virus from repository
Virus hiv = repository.findVirusByName("HIV");

// Display virus information
System.out.println(hiv.getDescription());

// Simulate infection
hiv.infect(hostCell);
```

## Running the Demo

To see the complete simulation:

```java
public static void main(String[] args) {
    VirusSimulationDemo.main(args);
}
```

## Lock-Key Mechanism Examples

| Virus | Glycoprotein (Key) | Receptor (Lock) |
|-------|-------------------|-----------------|
| HIV | gp120 | CD4 |
| SARS-CoV-2 | Spike | ACE2 |

## Extending the System (Open-Closed Principle)

**⭐ The system now follows the Open-Closed Principle - you can add new viruses WITHOUT modifying any existing classes! ⭐**

To add a new virus, follow these simple steps:

1. **Create a new virus class** extending either `EnvelopedVirus` or `NonEnvelopedVirus`
2. **Add a static registration block** to automatically register with VirusRepository
3. **Implement a createDefault() method** that returns a configured instance
4. **That's it!** No need to modify VirusRepository, VirusFactory, or any other files

### Example: Adding Influenza Virus

```java
package Domain.Virus;

public class Influenza extends EnvelopedVirus {
    
    // Auto-registration - this runs when class is loaded!
    static {
        VirusRepository.registerVirus(Influenza.class);
    }
    
    public Influenza(NucleicAcid nucleicAcid, Capsid capsid, 
                    InfectionStrategy strategy, LipidEnvelop envelope) {
        super("Influenza", nucleicAcid, capsid, strategy, envelope);
    }
    
    // Factory method required for auto-creation
    public static Influenza createDefault() {
        NucleicAcid nucleicAcid = new NucleicAcid("RNA");
        Capsid capsid = new Capsid("Helical");
        
        Glycoprotein hemagglutinin = new Glycoprotein("Hemagglutinin");
        Glycoprotein neuraminidase = new Glycoprotein("Neuraminidase");
        java.util.List<Glycoprotein> glycoproteins = 
            java.util.Arrays.asList(hemagglutinin, neuraminidase);
        
        LipidEnvelop envelope = new LipidEnvelop(glycoproteins);
        LockKeyInfection strategy = new LockKeyInfection();
        
        return new Influenza(nucleicAcid, capsid, strategy, envelope);
    }
}
```

### How It Works

- **Singleton Pattern**: VirusRepository uses Singleton to ensure one instance
- **Registry Pattern**: Repository maintains a registry of virus classes
- **Reflection**: Repository uses reflection to call `createDefault()` on registered classes
- **Static Initialization**: Each virus class registers itself when the JVM loads it
- **Automatic Discovery**: When you get the repository instance, all registered viruses are loaded automatically

### Benefits

✅ **No modification needed** to VirusRepository when adding new viruses  
✅ **Follows Open-Closed Principle** - open for extension, closed for modification  
✅ **No Factory class needed** - Repository manages everything  
✅ **Easy to extend** - just create new class and it's automatically integrated  
✅ **Type-safe** - uses generics and reflection properly  
✅ **Self-documenting** - each virus declares its own configuration  
✅ **Single Responsibility** - Repository handles both storage and creation logic

## Notes for GUI Integration

The domain classes are designed to be GUI-independent:
- Use `virus.getDescription()` to display virus information
- Call `virus.infect(hostCell)` to run infection simulation
- Console output can be captured and displayed in GUI
- Animation steps correspond to: attach() → enter() → injectNucleicAcid()
