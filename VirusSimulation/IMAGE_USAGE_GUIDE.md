# Image Usage Guide - VirusSimulation Project

## Folder Structure
```
VirusSimulation/
└── sourcecode/
    └── resources/
        └── images/
            ├── backgrounds/
            ├── viruses/
            ├── nucleic_acids/
            ├── cells/
            └── icons/
```

## Image Specifications

### 1. Backgrounds
| File | Size | Format | Description |
|------|------|--------|-------------|
| `main_menu_bg.png` | 1024x768 | PNG | Gradient background (blue/white) |
| `simulation_bg.png` | 1024x768 | PNG | Light background for animation |
| `cell_texture.png` | 512x512 | PNG | Texture for host cell surface |

### 2. Virus Components
| File | Size | Format | Transparency | Description |
|------|------|--------|--------------|-------------|
| `capsid_icosahedral.png` | 150x150 | PNG | Yes | 20-sided capsid (blue) |
| `capsid_helical.png` | 150x150 | PNG | Yes | Spiral capsid (green) |
| `capsid_conical.png` | 150x150 | PNG | Yes | Cone-shaped capsid (orange) |
| `envelope.png` | 200x200 | PNG | Yes (60%) | Lipid envelope (red/pink) |
| `spike_protein.png` | 50x50 | PNG | Yes | Glycoprotein spike (red) |

### 3. Nucleic Acids
| File | Size | Format | Transparency | Description |
|------|------|--------|--------------|-------------|
| `dna_helix.png` | 100x120 | PNG | Yes | Double helix (purple) |
| `rna_strand.png` | 100x120 | PNG | Yes | Single strand (orange) |

### 4. Host Cell
| File | Size | Format | Transparency | Description |
|------|------|--------|--------------|-------------|
| `host_cell.png` | 200x200 | PNG | Yes | Round cell (green) |
| `receptor_cd4.png` | 30x30 | PNG | Yes | CD4 receptor (yellow) |
| `receptor_ace2.png` | 30x30 | PNG | Yes | ACE2 receptor (yellow) |
| `receptor_car.png` | 30x30 | PNG | Yes | CAR receptor (yellow) |

### 5. UI Icons
| File | Size | Format | Transparency | Description |
|------|------|--------|--------------|-------------|
| `play_button.png` | 64x64 | PNG | Yes | Green play icon |
| `reset_button.png` | 64x64 | PNG | Yes | Orange reset icon |
| `back_button.png` | 64x64 | PNG | Yes | Gray back arrow |
| `help_icon.png` | 64x64 | PNG | Yes | Blue help icon |

## How to Use Images in Code

### Option 1: With ImageLoader utility
```java
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Load capsid image based on virus property
Image capsidImage = ImageLoader.getCapsidByShape(virus.getCapsid().getShape());
ImageView capsidView = new ImageView(capsidImage);
capsidView.setFitWidth(60);
capsidView.setFitHeight(60);
```

### Option 2: Fallback to hardcoded shapes
```java
// Try to load image, fallback to Circle if not available
if (ImageLoader.imageExists("viruses/capsid_icosahedral.png")) {
    ImageView capsid = new ImageView(ImageLoader.getCapsidByShape("Icosahedral"));
} else {
    Circle capsid = new Circle(30);  // Fallback to hardcoded shape
    capsid.setFill(Color.BLUE);
}
```

### Option 3: Background Image
```java
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Background;

// Set background for Pane
Image bgImage = ImageLoader.getSimulationBackground();
BackgroundImage background = new BackgroundImage(
    bgImage,
    BackgroundRepeat.NO_REPEAT,
    BackgroundRepeat.NO_REPEAT,
    BackgroundPosition.CENTER,
    BackgroundSize.DEFAULT
);
animationPane.setBackground(new Background(background));
```

## Where to Get Images

### Free Resources (with license attribution):
1. **Flaticon** - https://www.flaticon.com/
   - Virus icons, cell icons, UI buttons
   - Free with attribution

2. **Freepik** - https://www.freepik.com/
   - Medical illustrations, backgrounds
   - Free with attribution

3. **Unsplash** - https://unsplash.com/
   - High-quality backgrounds
   - Free, no attribution required

4. **OpenGameArt** - https://opengameart.org/
   - Sprite sheets, textures
   - Various licenses (check each)

5. **BioRender** (for educational projects)
   - Scientific illustrations
   - Free tier available

### DIY Options:
1. Use **Inkscape** (free vector editor) to create:
   - Simple capsid shapes
   - DNA/RNA strands
   - Receptor icons

2. Use **GIMP** (free image editor) to:
   - Create gradients for backgrounds
   - Add transparency
   - Resize and optimize

3. Use **PowerPoint/LibreOffice Draw** to:
   - Create simple shapes
   - Export as PNG with transparency
   - Add colors and effects

## Priority Order (What to add first)

### Phase 1: Essential (improves visual quality immediately)
1. ✅ `dna_helix.png` - Most important for DNA/RNA distinction
2. ✅ `rna_strand.png` - Most important for DNA/RNA distinction
3. ✅ `capsid_icosahedral.png` - Most common capsid type
4. ✅ `envelope.png` - For enveloped viruses

### Phase 2: Important (adds polish)
5. `simulation_bg.png` - Better than plain color
6. `host_cell.png` - More realistic cell
7. `spike_protein.png` - For glycoproteins
8. `receptor_cd4.png`, `receptor_ace2.png` - For lock-key visualization

### Phase 3: Nice to Have (UI improvements)
9. UI icons (play, reset, back buttons)
10. Other capsid shapes (helical, conical)
11. Main menu background

## Notes
- All images should be PNG format with transparency where needed
- Keep file sizes reasonable (< 100KB each)
- Test images on both light and dark backgrounds
- Include LICENSE.txt if using attributed images
- Fallback to hardcoded shapes if images missing (code already handles this)
