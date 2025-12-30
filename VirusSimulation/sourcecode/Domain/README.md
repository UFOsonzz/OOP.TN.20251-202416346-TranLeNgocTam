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
- **VirusFactory.java** - Factory class for creating virus instances
- **VirusRepository.java** - Repository for managing virus collections
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

### 5. Factory Pattern
- `VirusFactory` creates properly configured virus instances
- Simplifies virus creation and ensures consistency

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
// Create a host cell with CD4 receptor
HostCell hostCell = new HostCell(new Receptor("CD4"));

// Create HIV virus using factory
HIV hiv = VirusFactory.createHIV();

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

## Extending the System

To add a new virus:

1. Extend either `EnvelopedVirus` or `NonEnvelopedVirus`
2. Create appropriate components (nucleic acid, capsid, envelope)
3. Assign the correct infection strategy
4. Add to `VirusFactory` for easy creation

Example:
```java
public class Influenza extends EnvelopedVirus {
    public Influenza(NucleicAcid nucleicAcid, Capsid capsid, 
                    InfectionStrategy strategy, LipidEnvelop envelope) {
        super("Influenza", nucleicAcid, capsid, 
              new LockKeyInfection(), envelope);
    }
}
```

## Notes for GUI Integration

The domain classes are designed to be GUI-independent:
- Use `virus.getDescription()` to display virus information
- Call `virus.infect(hostCell)` to run infection simulation
- Console output can be captured and displayed in GUI
- Animation steps correspond to: attach() → enter() → injectNucleicAcid()
