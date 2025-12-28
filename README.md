# Automation Sofka - Reto Técnico QA Full Automation

Proyecto completo de automatización de pruebas que cubre UI (E2E), API, Performance y CI/CD, desarrollado con SerenityBDD, Screenplay Pattern, Gradle y JMeter.

## 📋 Tabla de Contenidos

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Prerrequisitos](#prerrequisitos)
- [Instalación](#instalación)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Ejecución de Pruebas](#ejecución-de-pruebas)
- [Reportes](#reportes)
- [Integración con BrowserStack](#integración-con-browserstack)
- [CI/CD Pipeline](#cicd-pipeline)
- [Entregables](#entregables)

## 📝 Descripción del Proyecto

Este proyecto implementa un framework completo de automatización de pruebas que incluye:

1. **Automatización E2E (UI)**: Flujo completo de "Contact Us" con carga de archivos
2. **Pruebas de API**: Validación del ciclo de vida de recursos (POST y GET)
3. **Pruebas de Performance**: Pruebas de carga con JMeter (50 VUs, 5 minutos)
4. **CI/CD**: Pipeline automatizado con GitHub Actions

## 🛠 Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación
- **Gradle 8.x**: Gestor de dependencias y construcción
- **SerenityBDD 4.0.30**: Framework de automatización y reportes
- **Screenplay Pattern**: Patrón de diseño para pruebas
- **JUnit 5**: Framework de testing
- **RestAssured**: Librería para pruebas de API
- **JMeter 5.6.2**: Herramienta de pruebas de performance
- **Selenium WebDriver**: Automatización de navegadores
- **BrowserStack**: Plataforma de testing en la nube
- **GitHub Actions**: CI/CD

## 📦 Prerrequisitos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java JDK 17** o superior
  ```bash
  java -version
  ```

- **Gradle 8.x** (opcional, el proyecto incluye Gradle Wrapper)
  ```bash
  gradle -v
  ```

- **ChromeDriver** (se descarga automáticamente con Serenity)
  - O configurar ChromeDriver en el PATH

- **JMeter 5.6.2** (para pruebas de performance)
  - Descargar desde: https://jmeter.apache.org/download_jmeter.cgi
  - Configurar variable de entorno `JMETER_HOME` (opcional)

- **Git** para clonar el repositorio

### Credenciales de BrowserStack (Opcional)

Para ejecutar pruebas en BrowserStack, configura las siguientes variables de entorno:

```bash
export BROWSERSTACK_USERNAME=tu_usuario
export BROWSERSTACK_ACCESS_KEY=tu_access_key
```

O en Windows:
```cmd
set BROWSERSTACK_USERNAME=tu_usuario
set BROWSERSTACK_ACCESS_KEY=tu_access_key
```

## 🚀 Instalación

1. **Clonar el repositorio**
   ```bash
   git clone <url-del-repositorio>
   cd automation_sofka
   ```

2. **Verificar Java**
   ```bash
   java -version
   ```

3. **Dar permisos de ejecución al wrapper de Gradle** (Linux/Mac)
   ```bash
   chmod +x gradlew
   ```

4. **Construir el proyecto**
   ```bash
   ./gradlew build
   ```
   
   En Windows:
   ```cmd
   gradlew.bat build
   ```

## 📁 Estructura del Proyecto

```
automation_sofka/
├── .github/
│   └── workflows/
│       └── ci-cd.yml              # Pipeline de CI/CD
├── jmeter/
│   └── performance-test.jmx       # Plan de pruebas de JMeter
├── scripts/
│   ├── run-performance-test.sh    # Script para ejecutar pruebas de performance (Linux/Mac)
│   ├── run-performance-test.bat   # Script para ejecutar pruebas de performance (Windows)
│   └── generate-performance-report.ps1  # Generador de reportes de performance
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── automation/
│   │               ├── models/           # Modelos de datos
│   │               ├── tasks/            # Tareas del patrón Screenplay
│   │               ├── userinterfaces/   # Page Objects
│   │               ├── questions/        # Questions del patrón Screenplay
│   │               └── utils/            # Utilidades y configuraciones
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── automation/
│       │           └── runners/          # Runners de pruebas
│       └── resources/
│           ├── serenity.conf             # Configuración de Serenity
│           └── testdata/                 # Datos de prueba
├── build.gradle                         # Configuración de Gradle
├── settings.gradle                      # Configuración del proyecto
├── gradle.properties                    # Propiedades de Gradle
└── README.md                            # Este archivo
```

## ▶️ Ejecución de Pruebas

### Pruebas UI (E2E)

Ejecutar todas las pruebas UI:
```bash
./gradlew test --tests "com.automation.runners.ContactUsTest"
```

Generar reportes de Serenity:
```bash
./gradlew aggregate
```

Los reportes se generarán en: `target/site/serenity/index.html`

### Pruebas de API

Ejecutar pruebas de API:
```bash
./gradlew test --tests "com.automation.runners.ApiTest"
```

### Pruebas de Performance

#### Opción 1: Usando JMeter directamente

**Linux/Mac:**
```bash
export JMETER_HOME=/ruta/a/jmeter
./scripts/run-performance-test.sh
```

**Windows:**
```cmd
set JMETER_HOME=C:\apache-jmeter-5.6.2
scripts\run-performance-test.bat
```

#### Opción 2: Ejecutar manualmente

```bash
$JMETER_HOME/bin/jmeter -n -t jmeter/performance-test.jmx -l jmeter-results/performance_test.jtl -e -o jmeter-results/report
```

Los resultados se guardarán en:
- `jmeter-results/performance_test.jtl` (resultados en formato JTL)
- `jmeter-results/report/` (reporte HTML)

### Ejecutar todas las pruebas

```bash
./gradlew test
./gradlew aggregate
```

## 📊 Reportes

### Reportes de Serenity

Después de ejecutar las pruebas, los reportes se generan automáticamente:

```bash
./gradlew aggregate
```

Abrir el reporte:
```bash
open target/site/serenity/index.html
```

O en Windows:
```cmd
start target\site\serenity\index.html
```

### Reportes de Bugs

Si se encuentran bugs durante la ejecución, se generará automáticamente un reporte en:

```
reports/bugs/bug-report-YYYYMMDD-HHMMSS.html
```

### Reportes de Performance

Los reportes de performance se generan en:

```
jmeter-results/report/index.html
```

## 🌐 Integración con BrowserStack

Para ejecutar pruebas en BrowserStack:

1. **Configurar credenciales** (variables de entorno o en serenity.conf):
   ```bash
   export BROWSERSTACK_USERNAME=tu_usuario
   export BROWSERSTACK_ACCESS_KEY=tu_access_key
   ```

2. **Ejecutar pruebas**:
   ```bash
   ./gradlew test --tests "com.automation.runners.ContactUsTest"
   ```

Las pruebas se ejecutarán automáticamente en BrowserStack si las credenciales están configuradas. De lo contrario, se ejecutarán localmente.

### Configuración en serenity.conf

El archivo `src/test/resources/serenity.conf` incluye la configuración para BrowserStack. Puedes modificar los capabilities según tus necesidades.

## 🔄 CI/CD Pipeline

El pipeline de CI/CD está configurado en `.github/workflows/ci-cd.yml` y se ejecuta automáticamente en:

- Push a las ramas `main`, `master` o `develop`
- Pull requests a las ramas `main`, `master` o `develop`

### Jobs del Pipeline

1. **UI Tests**: Ejecuta las pruebas E2E y genera reportes
2. **API Tests**: Ejecuta las pruebas de API
3. **Performance Tests**: Ejecuta las pruebas de performance con JMeter
4. **Build Summary**: Genera un resumen de la ejecución

### Configurar Secrets en GitHub

Para usar BrowserStack en el pipeline, configura los siguientes secrets en GitHub:

1. Ve a Settings → Secrets and variables → Actions
2. Agrega:
   - `BROWSERSTACK_USERNAME`
   - `BROWSERSTACK_ACCESS_KEY`

### Ver Reportes en GitHub Actions

Después de cada ejecución del pipeline, los reportes están disponibles como artifacts:

1. Ve a la pestaña "Actions" en GitHub
2. Selecciona el workflow ejecutado
3. Descarga los artifacts:
   - `serenity-reports`: Reportes de Serenity
   - `bug-reports`: Reportes de bugs encontrados
   - `performance-test-results`: Resultados de performance
   - `execution-summary`: Resumen de la ejecución

## 📦 Entregables

El proyecto incluye todos los entregables requeridos:

### 1. 
- La ejecución del pipeline en GitHub Actions
- La ejecución de pruebas en BrowserStack (si está configurado)
- La ejecución de pruebas de performance

### 2. Reporte de Ejecución

Los reportes se generan en:
- **Serenity**: `target/site/serenity/`
- **Performance**: `jmeter-results/report/`
- **Bugs**: `reports/bugs/`

### 3. Proyecto Empaquetado

Para empaquetar el proyecto:

```bash
zip -r automation_sofka.zip . -x "*.git*" -x "*.idea*" -x "*target*" -x "*.gradle*"
```

O en Windows, usar un compresor como 7-Zip o WinRAR.

### 4. Reporte de Bugs

Si se encuentran bugs, el reporte se genera automáticamente en `reports/bugs/`.

## 🔧 Configuración Adicional

### Modificar URLs

Las URLs se configuran en `src/main/java/com/automation/utils/Constants.java`:

```java
public static final String BASE_URL = "https://automationexercise.com";
public static final String API_BASE_URL = "https://reqres.in/api";
```

### Modificar Configuración de Serenity

Edita `src/test/resources/serenity.conf` para modificar:
- Configuración del navegador
- Timeouts
- Configuración de BrowserStack
- Entornos

### Modificar Pruebas de Performance

Edita `jmeter/performance-test.jmx` para modificar:
- Número de usuarios virtuales
- Duración de la prueba
- Ramp-up time
- Endpoints a probar

## 🐛 Solución de Problemas

### Error: ChromeDriver no encontrado

Serenity descarga automáticamente ChromeDriver. Si hay problemas:
```bash
./gradlew clean
./gradlew build
```

### Error: No se pueden ejecutar pruebas en BrowserStack

Verifica que las credenciales estén configuradas correctamente:
```bash
echo $BROWSERSTACK_USERNAME
echo $BROWSERSTACK_ACCESS_KEY
```

### Error: JMeter no encontrado

Configura la variable de entorno:
```bash
export JMETER_HOME=/ruta/a/jmeter
```

O instala JMeter y actualiza la ruta en los scripts.

## 📝 Notas Importantes

- Las pruebas UI requieren conexión a Internet
- Las pruebas de API usan el servicio público `reqres.in`
- Las pruebas de performance pueden tardar varios minutos
- Los reportes de Serenity se generan después de ejecutar `./gradlew aggregate`

## 👤 Autor

Desarrollado como parte del Reto Técnico QA - Automation Sofka

## 📄 Licencia

Este proyecto es de uso educativo y para demostración de habilidades técnicas.

---


