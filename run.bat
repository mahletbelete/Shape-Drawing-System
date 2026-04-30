@echo off
setlocal
set "SDK_DIR=%~dp0javafx-sdk-26.0.1"
set "LIB_DIR=%SDK_DIR%\lib"
set "OUT_DIR=%~dp0out"
mkdir "%OUT_DIR%" 2>nul
javac --module-path "%LIB_DIR%" --add-modules javafx.controls,javafx.fxml,javafx.swing -d "%OUT_DIR%" src\*.java
if errorlevel 1 exit /b %errorlevel%
java --module-path "%LIB_DIR%" --add-modules javafx.controls,javafx.fxml,javafx.swing --enable-native-access=javafx.graphics -Dprism.order=sw -cp "%OUT_DIR%" Main
