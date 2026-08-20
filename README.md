# Descripción Detallada de PointTap (v1.1.0)

**PointTap** es una herramienta profesional de georreferenciación diseñada específicamente para ingenieros y topógrafos involucrados en el trazado de carreteras y caminos. La aplicación optimiza la captura de coordenadas geográficas, permitiendo tanto el registro manual como automatizado de puntos con alta precisión GPS.

---

## Componentes y Funcionalidades Principales

### 1. Modos de Captura Avanzados
La app ofrece flexibilidad total para el trabajo de campo:
- **Captura Manual:** Permite registrar puntos individuales con precisión quirúrgica mediante el botón **"+"**. Ideal para marcar hitos o puntos de control específicos.
- **Captura Automática (Timer):** Un motor inteligente que registra la ubicación cada **5 segundos** sin intervención del usuario. Incluye controles de **Pausa** y **Stop**, permitiendo gestionar paradas técnicas durante el trayecto.
- **GPS de Alta Precisión:** Utiliza `FusedLocationProviderClient` para garantizar las coordenadas más exactas que el hardware del dispositivo permita.

### 2. Estándar de Datos GeoJSON
A partir de la versión 1.1.0, PointTap adopta el estándar internacional **GeoJSON (RFC 7946)**:
- **Formato Profesional:** Los datos se exportan como una `FeatureCollection` con una geometría de tipo `LineString`.
- **Compatibilidad Directa:** Los archivos generados son compatibles de forma nativa con software de ingeniería y GIS como QGIS, ArcGIS y Google Earth.
- **Orden Internacional:** Las coordenadas se guardan en el formato estándar `[Longitud, Latitud]`.

### 3. Organización y Almacenamiento
- **Nombres de Archivo Personalizados:** Al guardar, se solicita una etiqueta ("Calle/Camino/Carretera") que se integra automáticamente en el nombre del archivo físico (ej: `Ruta_66_1724089200.geojson`).
- **Ubicación Centralizada:** Todos los trabajos se almacenan en la carpeta pública `Download/PointTap`, facilitando su acceso desde cualquier gestor de archivos.
- **Historial Integrado:** Un panel lateral (Drawer) permite visualizar todos los archivos guardados previamente y compartirlos instantáneamente sin salir de la aplicación.

### 4. Interfaz de Usuario (UI) y Experiencia
Diseñada con **Material Design 3**, la interfaz prioriza la ergonomía en campo:
- **Control con una Sola Mano:** Los botones de captura están agrupados verticalmente en la parte inferior derecha para facilitar el uso mientras se camina o se conduce a baja velocidad.
- **Gestión de Permisos Robusta:** Un sistema de validación inicial asegura que la app tenga acceso a la Ubicación y al Almacenamiento antes de comenzar, evitando fallos accidentales.
- **Feedback en Tiempo Real:** Barra de estado dinámica que indica si la captura automática está activa o pausada, junto con una lista visual de los puntos capturados.

---

## Detalles Técnicos de las Funciones

| Función | Descripción Técnica |
| :--- | :--- |
| **Captura Auto** | Ciclo asíncrono basado en Coroutines que consulta el sensor cada 5000ms y actualiza el estado reactivo de la UI. |
| **Guardar (GeoJSON)** | Transforma la lista de puntos en una estructura `FeatureCollection` y la serializa usando `kotlinx-serialization`. |
| **Panel Lateral** | Consulta dinámica de `MediaStore` para listar archivos `.geojson` con filtrado por ruta relativa. |
| **Compartir Historial** | Utiliza `Intent.ACTION_SEND` con `FLAG_GRANT_READ_URI_PERMISSION` para compartir archivos de forma segura. |

---

> [!TIP]
> **Optimización de Trazado:** El modo automático está diseñado para recorridos en vehículos a baja velocidad o caminatas, asegurando una densidad de puntos ideal para el diseño de curvas y pendientes en carreteras.

> [!IMPORTANT]
> **Privacidad y Seguridad:** PointTap no requiere conexión a internet para funcionar ni envía datos a la nube. Toda tu información de ingeniería permanece privada en tu dispositivo hasta que decidas compartirla.
