@echo off
REM ===============================================
REM Virus Simulation GUI Launcher - FXML Version
REM ===============================================

echo.
echo =============================================
echo   VIRUS INFECTION SIMULATION - GUI (FXML)
echo =============================================
echo.

REM ===============================================
REM IMPORTANT: Set your JavaFX SDK path here
REM ===============================================

set JAVAFX_PATH="C:\Users\admin\Downloads\openjfx-25.0.1_windows-x64_bin-sdk\javafx-sdk-25.0.1\lib" 

REM Check if JavaFX path exists
if not exist %JAVAFX_PATH% (
    echo ERROR: JavaFX SDK not found at: %JAVAFX_PATH%
    pause
    exit /b 1
)

echo JavaFX Path: %JAVAFX_PATH%
echo.

REM Clean old compiled classes
echo Cleaning old compiled files...
if exist "%~dp0sourcecode\GUI\*.class" del /Q "%~dp0sourcecode\GUI\*.class"
if exist "%~dp0sourcecode\GUI\controllers\*.class" del /Q "%~dp0sourcecode\GUI\controllers\*.class"
if exist "%~dp0sourcecode\Domain\*.class" del /Q "%~dp0sourcecode\Domain\*.class"
if exist "%~dp0sourcecode\Domain\Host\*.class" del /Q "%~dp0sourcecode\Domain\Host\*.class"
if exist "%~dp0sourcecode\Domain\Virus\*.class" del /Q "%~dp0sourcecode\Domain\Virus\*.class"
echo.

REM Navigate to source directory
cd /d "%~dp0sourcecode"

echo Compiling Java files...
echo.

javac --module-path %JAVAFX_PATH% --add-modules javafx.controls,javafx.fxml -cp . GUI\*.java GUI\controllers\*.java Domain\*.java Domain\Host\*.java Domain\Virus\*.java

if errorlevel 1 (
    echo.
    echo ========================================
    echo   COMPILATION FAILED
    echo ========================================
    pause
    exit /b 1
)

echo.
echo ========================================
echo   COMPILATION SUCCESSFUL
echo ========================================
echo.
echo Starting GUI application (FXML version)...
echo.

java --module-path %JAVAFX_PATH% --add-modules javafx.controls,javafx.fxml -cp . GUI.VirusSimulationAppFXML

echo.
echo Application closed.
pause