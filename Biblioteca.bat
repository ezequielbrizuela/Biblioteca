@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion

REM Obtener la ruta del directorio actual (donde está el .bat)
set SCRIPT_DIR=%~dp0

REM Ruta al JAR
set JAR_FILE=%SCRIPT_DIR%target\Biblioteca.jar

REM Verificar si el JAR existe
if not exist "!JAR_FILE!" (
    echo.
    echo ╔════════════════════════════════════════════════════════╗
    echo ║           ERROR: Archivo no encontrado                ║
    echo ╚════════════════════════════════════════════════════════╝
    echo.
    echo El archivo !JAR_FILE! no existe.
    echo.
    echo Soluciones:
    echo 1. Compilar el proyecto con: mvn clean package
    echo 2. Verificar que Maven esté instalado
    echo.
    pause
    exit /b 1
)

REM Ejecutar el JAR
echo.
echo ╔════════════════════════════════════════════════════════╗
echo ║      SISTEMA DE BIBLIOTECA UNIVERSITARIA               ║
echo ║                 Iniciando...                           ║
echo ╚════════════════════════════════════════════════════════╝
echo.

java -jar "!JAR_FILE!"

REM Mantener la ventana abierta si hay error
if !errorlevel! neq 0 (
    echo.
    echo ╔════════════════════════════════════════════════════════╗
    echo ║              Error durante la ejecución                ║
    echo ╚════════════════════════════════════════════════════════╝
    echo.
    pause
    exit /b !errorlevel!
)

endlocal
