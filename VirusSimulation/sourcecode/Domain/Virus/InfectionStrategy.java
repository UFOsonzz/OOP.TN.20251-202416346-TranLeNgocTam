package Domain.Virus;
import Domain.Host.HostCell;

/**
 * Strategy interface for virus infection mechanisms
 * Defines the three phases of viral infection:
 * 1. Attachment - virus binds to host cell
 * 2. Entry - virus enters the host cell
 * 3. Nucleic acid injection - viral genome is released into the cell
 * 
 * Different viruses implement this strategy differently:
 * - Non-enveloped viruses use DirectInfection
 * - Enveloped viruses use LockKeyInfection
 */
public interface InfectionStrategy {
    
    /**
     * Phase 1: Virus attaches to the host cell
     * @param virus The virus attempting to attach
     * @param hostCell The target host cell
     */
    void attach(Virus virus, HostCell hostCell);
    
    /**
     * Phase 2: Virus enters the host cell
     * @param virus The virus entering the cell
     * @param hostCell The target host cell
     */
    void enter(Virus virus, HostCell hostCell);
    
    /**
     * Phase 3: Viral nucleic acid is injected into the host cell
     * @param virus The virus injecting its genetic material
     * @param hostCell The target host cell
     */
    void injectNucleicAcid(Virus virus, HostCell hostCell);
}
