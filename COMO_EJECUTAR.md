# 🚀 Como usar el ejecutable de Biblioteca

## Opción 1: Doble click directo (Más fácil)

1. **Navega a la carpeta del proyecto:**
   ```
   d:\Windows\Desktop\biblioteca_poo
   ```

2. **Haz doble click en:**
   ```
   Biblioteca.bat
   ```

✅ ¡La aplicación se iniciará automáticamente!

---

## Opción 2: Por línea de comandos

### Desde PowerShell o CMD:

```bash
cd d:\Windows\Desktop\biblioteca_poo
java -jar target/Biblioteca.jar
```

---

## Requisitos

- **Java 17** o superior instalado
- Ejecutar una vez: `mvn clean package` (si modifica el código)

---

## ¿Qué contiene?

- ✅ **Biblioteca.bat** - Launcher ejecutable (haz doble click)
- ✅ **target/Biblioteca.jar** - Aplicación compilada y lista

---

## Si tienes problemas

### Si el .bat no abre la aplicación:

1. Verifica que Java esté instalado:
   ```bash
   java -version
   ```

2. Recompila el proyecto:
   ```bash
   mvn clean package
   ```

3. Ejecuta directamente el JAR:
   ```bash
   java -jar target/Biblioteca.jar
   ```

---

## ℹ️ Información del ejecutable

- **Nombre:** Biblioteca.jar
- **Tipo:** Aplicación de consola Java
- **Tamaño:** ~200 KB (completa, sin dependencias externas)
- **Compilada con:** Maven 3.x + Java 17

---

¡Disfruta usando la Biblioteca! 📚
