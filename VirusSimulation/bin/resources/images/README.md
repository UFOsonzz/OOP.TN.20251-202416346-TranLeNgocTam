# Placeholders for virus simulation images

## Current Structure
This folder contains subfolders for different image categories:

- **backgrounds/** - Background images for GUI screens
- **viruses/** - Virus component images (capsids, envelope, spikes)
- **nucleic_acids/** - DNA and RNA visualization images
- **cells/** - Host cell and receptor images
- **icons/** - UI button icons

## To Add Images
1. Download or create images according to IMAGE_USAGE_GUIDE.md
2. Place them in appropriate subfolders
3. Use ImageLoader.java to load them in your code
4. Code will automatically fall back to hardcoded shapes if images are missing

## Priority
Start with:
1. dna_helix.png and rna_strand.png (nucleic_acids/)
2. capsid_icosahedral.png (viruses/)
3. envelope.png (viruses/)

See IMAGE_USAGE_GUIDE.md for full details and image specifications.
