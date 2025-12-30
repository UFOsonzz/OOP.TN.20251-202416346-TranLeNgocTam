# Virus Simulation - GUI với FXML

Đã tạo **2 phiên bản GUI**:

## 📁 Phiên bản 1: Programmatic (Code thuần Java)
- **Tất cả UI code bằng Java**
- **File chính**: `GUI/VirusSimulationApp.java`
- **Chạy**: `run_gui.bat`
- **Ưu điểm**: Đơn giản, không cần FXML, dễ debug
- **Classes**:
  - `VirusSimulationApp.java`
  - `MainMenuScreen.java`
  - `VirusSelectionScreen.java`
  - `InfectionSimulationScreen.java`
  - `HelpDialog.java`
  - `QuitConfirmationDialog.java`

## 📁 Phiên bản 2: FXML (Tách biệt View và Logic) ✨
- **UI design với FXML files**
- **Logic trong Controller classes**
- **File chính**: `GUI/VirusSimulationAppFXML.java`
- **Chạy**: `run_gui_fxml.bat`
- **Ưu điểm**: Tách biệt view/logic, dùng Scene Builder được, professional

### Cấu trúc FXML Version:

```
GUI/
├── fxml/                           # FXML files (View)
│   ├── MainMenu.fxml
│   ├── VirusSelection.fxml
│   └── InfectionSimulation.fxml
├── controllers/                    # Controllers (Logic)
│   ├── MainMenuController.java
│   ├── VirusSelectionController.java
│   └── InfectionSimulationController.java
├── VirusSimulationAppFXML.java    # Main app
├── HelpDialog.java                # Dialogs (reused)
└── QuitConfirmationDialog.java
```

## 🚀 Cách chạy

### Windows:

**Phiên bản Code thuần:**
```batch
run_gui.bat
```

**Phiên bản FXML:**
```batch
run_gui_fxml.bat
```

### Linux/Mac:

**Phiên bản Code thuần:**
```bash
./run_gui.sh
```

**Phiên bản FXML:**
```bash
./run_gui_fxml.sh
```

### Manual Compilation:

```bash
# Set JavaFX path
set JAVAFX_PATH=D:\javafx-sdk-23.0.1\lib

# Compile
javac --module-path %JAVAFX_PATH% --add-modules javafx.controls ^
      Domain/Host/*.java Domain/Virus/*.java Domain/*.java ^
      GUI/controllers/*.java GUI/*.java

# Run programmatic version
java --module-path %JAVAFX_PATH% --add-modules javafx.controls GUI.VirusSimulationApp

# Run FXML version
java --module-path %JAVAFX_PATH% --add-modules javafx.controls GUI.VirusSimulationAppFXML
```

## 📝 So sánh 2 phiên bản

| Tiêu chí | Programmatic | FXML |
|----------|-------------|------|
| **Độ phức tạp** | Đơn giản hơn | Phức tạp hơn một chút |
| **Tách biệt UI/Logic** | Trong cùng class | Hoàn toàn tách biệt |
| **Scene Builder** | Không dùng được | Dùng được |
| **File count** | Ít hơn | Nhiều hơn (FXML + Controller) |
| **Phù hợp** | Project nhỏ | Project lớn, team |
| **Debug** | Dễ hơn | Khó hơn một chút |
| **Professional** | ✓ | ✓✓ |

## 🎨 FXML Features

### MainMenu.fxml
- BorderPane layout với 3 sections
- Styled buttons với effects
- Gradient background
- Event handlers: `onAction="#methodName"`

### VirusSelection.fxml
- Dynamic virus list loading
- Custom button styling
- ScrollPane ready

### InfectionSimulation.fxml
- Animation pane với FXML
- TextArea cho infection log
- Button controls

### Controllers
- `@FXML` annotations cho components
- `initialize()` method tự động gọi
- `setApp()` để connect với main app
- Separation of concerns

## 💡 Sử dụng Scene Builder (Optional)

1. Download Scene Builder: https://gluonhq.com/products/scene-builder/
2. Mở file `.fxml` trong Scene Builder
3. Drag & drop components
4. Chỉnh styling, layout
5. Assign Controller class
6. Set fx:id cho components
7. Set onAction cho buttons
8. Save và compile

## 🔧 FXML Loading Process

```java
// 1. Load FXML file
FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MainMenu.fxml"));
Parent root = loader.load();

// 2. Get controller instance
MainMenuController controller = loader.getController();

// 3. Initialize controller
controller.setApp(this);

// 4. Create scene
Scene scene = new Scene(root, width, height);
stage.setScene(scene);
```

## 📖 FXML Syntax Highlights

```xml
<!-- fx:id để reference trong Controller -->
<Button fx:id="startBtn" text="Start"/>

<!-- Event handler -->
<Button onAction="#handleStart"/>

<!-- Style inline -->
<Button style="-fx-background-color: #4caf50;"/>

<!-- Import custom classes -->
<?import javafx.scene.control.Button?>

<!-- Controller class -->
fx:controller="GUI.controllers.MainMenuController"
```

## ✅ Cả 2 phiên bản đều có đầy đủ:

- ✅ Main menu với lựa chọn virus type
- ✅ Virus selection screen
- ✅ Animation simulation với 3 phases
- ✅ Infection log
- ✅ Help dialog
- ✅ Quit confirmation
- ✅ Back navigation
- ✅ Hover effects
- ✅ Smooth animations

## 🎯 Chọn version nào?

**Dùng Programmatic nếu:**
- Project nhỏ
- Muốn code đơn giản
- Không cần Scene Builder
- Debug nhanh

**Dùng FXML nếu:**
- Project lớn
- Team collaboration
- Muốn tách biệt UI/Logic
- Dùng Scene Builder
- Professional practice

## 📚 Tài liệu tham khảo

- JavaFX FXML: https://openjfx.io/javadoc/21/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html
- Scene Builder: https://gluonhq.com/products/scene-builder/
- JavaFX Tutorial: https://docs.oracle.com/javafx/

---

**Cả 2 version đều chạy hoàn hảo!** Bạn có thể thử cả 2 và chọn cái phù hợp nhất. 🎉
