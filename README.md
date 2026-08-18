# Descripción Detallada de PointTap

**PointTap** es una herramienta especializada para ingenieros y topógrafos diseñada para simplificar la captura de coordenadas geográficas en proyectos de trazado de carreteras. La aplicación permite registrar puntos precisos de latitud y longitud, visualizarlos en tiempo real y exportarlos en formatos estructurados para su posterior procesamiento.

---

## Componentes y Funcionalidades Principales

### 1. Gestión de Ubicación (GPS de Alta Precisión)
La app utiliza el proveedor de ubicación fusionado (`FusedLocationProviderClient`) de Google Play Services para garantizar la máxima precisión disponible en el dispositivo.
- **Captura Instantánea:** Al presionar el botón "+", la app solicita una actualización de ubicación única con prioridad de alta precisión.
- **Datos Capturados:** Cada punto incluye Latitud, Longitud y una marca de tiempo exacta (Timestamp).

### 2. Gestión de Permisos Inteligente
Para garantizar un funcionamiento sin errores, la app implementa un sistema de validación de permisos al inicio:
- **Ubicación:** Requiere permisos de ubicación precisa (`ACCESS_FINE_LOCATION`) para obtener las coordenadas.
- **Almacenamiento:** Solicita permisos de lectura y escritura para gestionar los archivos JSON en la memoria del dispositivo.
- **Bloqueo Preventivo:** La interfaz principal no se activa hasta que el usuario concede los permisos necesarios, evitando cierres inesperados.

### 3. Almacenamiento y Exportación
La aplicación ofrece múltiples formas de extraer y guardar los datos:
- **Guardado Local en Carpeta:** Genera archivos con extensión `.json` dentro de la carpeta pública `Download/PointTap`. Cada archivo es nombrado automáticamente con la fecha y hora de creación para evitar duplicados.
- **Formato Estándar:** El archivo generado utiliza el estándar JSON, lo que lo hace compatible con herramientas de escritorio, software de GIS o sistemas de trazado de carreteras.
- **Compartir Directo:** Permite enviar el listado de puntos actual como texto plano/JSON a través de otras aplicaciones (WhatsApp, Email, Telegram, etc.) sin necesidad de guardarlo primero.

### 4. Interfaz de Usuario (UI)
Diseñada bajo los principios de **Material Design 3**, la interfaz es limpia y funcional:
- **Lista de Puntos:** Presenta tarjetas informativas para cada punto capturado, facilitando la revisión rápida en campo.
- **Feedback Visual:** Incluye indicadores de carga (progress indicators) mientras el GPS intenta fijar la posición, informando al usuario que la captura está en progreso.
- **Acceso Directo a Archivos:** Un botón dedicado abre el gestor de archivos del sistema directamente en la carpeta de descargas, permitiendo al usuario gestionar sus mediciones sin salir de la experiencia del flujo de trabajo.

---

## Detalles Técnicos de las Funciones

| Función | Descripción Técnica |
| :--- | :--- |
| **Marcar Punto** | Lógica asíncrona (Coroutines) que consulta el sensor GPS y añade un objeto `GeoPoint` a la lista reactiva. |
| **Guardar (JSON)** | Serializa la lista de puntos y utiliza `MediaStore` para escribir físicamente el archivo en el almacenamiento externo. |
| **Abrir Carpeta** | Ejecuta un `Intent` con múltiples niveles de respaldo para navegar por el sistema de archivos de Android hasta la ruta de `Download/PointTap`. |
| **Exportar/Compartir** | Convierte la sesión actual en una cadena de texto formateada y lanza el selector de aplicaciones compartidas de Android. |

---

> [!TIP]
> **Recomendación de Uso:** Para obtener resultados óptimos en el trazado de carreteras, se recomienda esperar 2-3 segundos en la posición antes de presionar el botón de captura para permitir que el GPS se estabilice.

> [!IMPORTANT]
> **Privacidad:** Todos los datos se almacenan localmente en el dispositivo. La app no envía coordenadas a servidores externos a menos que el usuario decida compartirlas manualmente.
