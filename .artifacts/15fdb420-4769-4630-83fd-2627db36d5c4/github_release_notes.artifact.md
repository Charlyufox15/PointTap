# Release Notes - PointTap v1.0.0 🚀

¡Bienvenido a la primera versión oficial de **PointTap**! Esta herramienta ha sido diseñada específicamente para facilitar la captura de datos geográficos en proyectos de ingeniería civil y trazado de carreteras.

## 📋 Descripción
PointTap permite a los usuarios registrar coordenadas GPS precisas con un solo toque, visualizar los puntos capturados en una lista organizada y exportar la información en formato JSON para su uso en software profesional de topografía o GIS.

## ✨ Características Principales
- **Captura GPS de Alta Precisión:** Registro instantáneo de latitud y longitud utilizando `FusedLocationProvider`.
- **Almacenamiento Local Robusto:** Los puntos se guardan automáticamente en archivos `.json` dentro de la carpeta `Download/PointTap` del dispositivo.
- **Acceso Directo a Archivos:** Botón integrado para abrir el gestor de archivos directamente en la ubicación de tus datos.
- **Compartir Sin Esfuerzo:** Opción para compartir la lista de puntos actual mediante correo, WhatsApp, Drive, etc.
- **Interfaz Moderna:** Desarrollada con Jetpack Compose y Material Design 3 para una experiencia fluida y adaptativa.
- **Gestión de Permisos:** Sistema integrado de solicitud de permisos de ubicación y almacenamiento.

## 🛠️ Detalles Técnicos
- **Lenguaje:** Kotlin
- **UI Framework:** Jetpack Compose (Material 3)
- **Manejo de Ubicación:** Google Play Services Location API
- **Serialización:** Kotlinx Serialization (JSON)
- **Arquitectura:** MVVM (Model-View-ViewModel) con Coroutines para procesos asíncronos.
- **API Mínima:** Android 10 (API 29) para asegurar compatibilidad con Scoped Storage.

## 📦 Instalación
1. Descarga el archivo `app-debug.apk` (o `app-release.apk`) adjunto en este release.
2. Transfiere el archivo a tu dispositivo Android.
3. Abre el archivo e instálalo (asegúrate de permitir la instalación desde fuentes desconocidas si es necesario).
4. Al abrir la app, concede los permisos de Ubicación y Almacenamiento para activar todas las funciones.

## 📂 Estructura de Datos (JSON)
Los archivos generados tienen la siguiente estructura:
```json
[
  {
    "latitude": 10.123456,
    "longitude": -20.654321,
    "timestamp": 1722345678901
  }
]
```

---
*Desarrollado para optimizar el trabajo de campo en ingeniería.*
