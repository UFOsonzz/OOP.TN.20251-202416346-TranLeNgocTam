# Visual Guide - Receptor Selection & Infection Animation

## 📱 New UI Layout

```
┌─────────────────────────────────────────────────────────────┐
│         HIV Infection Simulation                            │
│    Enveloped virus | RNA genome | Conical capsid           │
│                                                             │
│  Select Host Cell Receptor: [CD4 ▼]  ✓ Compatible         │
└─────────────────────────────────────────────────────────────┘
┌──────────────────────────┐  ┌────────────────────────────┐
│      Animation           │  │    Simulation Log          │
│                          │  │                            │
│   🦠 ──→  🔴             │  │ [ATTACHMENT PHASE]         │
│   Virus   Cell           │  │ Capsid: Conical            │
│                          │  │ Genome: RNA                │
│   ⭐ Receptors shown     │  │ ✓ Receptor binding         │
│                          │  │   successful!              │
│                          │  │                            │
│                          │  │ [ENTRY PHASE]              │
│                          │  │ Envelope fusion...         │
└──────────────────────────┘  └────────────────────────────┘
     [Start Infection]  [Reset]  [Back]
```

## 🎬 Animation Sequences

### Scenario A: Compatible Receptor (Success)

```
Step 1: ATTACHMENT (2-3 seconds)
🦠 ────────────→ 🔴
   Virus moves    Cell

Step 2: RECEPTOR BINDING
🦠 ⭐⭐⭐⭐ 🟢
   Receptors pulse GREEN

Step 3: ENTRY (1.5-2 seconds)
   🦠 ──→ 🔴
      \___/
   Fusion & entry

Step 4: INJECTION
      💉 🟠
   Genome released
   Cell turns ORANGE/PURPLE
   (infected)

Result: ✓ INFECTION SUCCESSFUL
```

### Scenario B: Incompatible Receptor (Failure)

```
Step 1: ATTACHMENT
🦠 ────────────→ 🔴
   Virus moves    Cell

Step 2: FAILED BINDING
🦠 ⭐⭐⭐⭐ 🔴
   Receptors pulse RED

Step 3: REJECTION (2 seconds)
🦠 ⚡⚡⚡ 🔴
   ↓Shake/wobble
🦠 ←────────── 🔴
   Bounces back
   (spinning)

   🦠💨        🟢
   Virus       Cell stays
   repelled    HEALTHY

Result: ❌ INFECTION FAILED
```

## 🎨 Color Coding System

| Element | Success | Failure | Info |
|---------|---------|---------|------|
| Receptor | 🟢 Green | 🔴 Red | 🟡 Yellow |
| Match Label | Green bg | Red bg | Blue bg |
| Host Cell | 🟠 Orange/Purple (infected) | 🟢 Green (healthy) | 🟢 Green |
| Virus | Full opacity | Faded (0.5) | Normal |

## 🧪 Receptor Compatibility Matrix

### Enveloped Viruses

| Virus | Glycoprotein | Compatible Receptor | Incompatible |
|-------|--------------|---------------------|--------------|
| HIV | gp120 | ✅ CD4 | ❌ ACE2, CAR, PVR, etc. |
| SARS-CoV-2 | Spike | ✅ ACE2 | ❌ CD4, CAR, PVR, etc. |
| Influenza | Hemagglutinin | ✅ Sialic Acid | ❌ CD4, ACE2, etc. |

### Non-Enveloped Viruses

| Virus | Capsid | Receptor | Result |
|-------|--------|----------|--------|
| Adenovirus | Icosahedral | ANY | ✅ Always infects |
| Poliovirus | Icosahedral | ANY | ✅ Always infects |

**Note**: Non-enveloped viruses don't use lock-key mechanism, so they infect regardless of receptor type.

## 📋 User Actions Flow

```
1. User selects virus type
   ↓
2. System shows simulation screen
   ↓
3. User selects receptor from dropdown
   ↓
4. System checks compatibility
   ↓
   ├─ Compatible? → Shows GREEN label
   └─ Incompatible? → Shows RED label
   ↓
5. User clicks "Start Infection"
   ↓
   ├─ Success animation → Cell infected
   └─ Failure animation → Virus bounces back
   ↓
6. User clicks "Reset" to try again
   (or "Back" to choose different virus)
```

## 💻 Technical Details

### FXML Changes
- Added `ComboBox` for receptor selection
- Added `matchLabel` for compatibility status
- Moved all styling from Java to FXML

### Controller Changes
- `handleReceptorChange()` - Updates receptor and UI
- `updateMatchLabel()` - Shows compatibility status
- `addRejectionAnimation()` - NEW failure animation
- Simplified visual creation methods

### Animation Timing
- **Attachment**: 1.8-2.5s (varies by capsid)
- **Binding check**: 0.5s per receptor
- **Entry**: 1.5-1.8s
- **Injection**: 2s
- **Rejection**: 2s shake + 2s bounce = 4s total

## 🎓 Educational Value

Students can learn:
1. **Lock-Key Mechanism**: Why specific receptors matter
2. **Viral Specificity**: Different viruses target different cells
3. **Infection Failure**: Natural immune defense concept
4. **Visual Biology**: See molecular interactions in action
5. **Experimental Learning**: Try different combinations
