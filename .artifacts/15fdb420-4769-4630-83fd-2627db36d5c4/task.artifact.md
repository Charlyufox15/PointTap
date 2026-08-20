# Tareas: Reversión a Versión 1.0.3 (Formato Simple)

- [ ] Revertir configuración de versión
    - [x] Cambiar a `versionCode 4` y `versionName "1.0.3"` en `build.gradle.kts`
- [ ] Revertir formato de exportación en ViewModel
    - [ ] Cambiar `exportToJson` a formato `[[lat, lon], ...]`
    - [ ] Cambiar extensión de archivo a `.json` en `savePointsToFile`
    - [ ] Cambiar MIME type a `application/json`
- [ ] Limpieza de archivos GeoJSON
    - [ ] Eliminar `GeoJsonModels.kt`
- [ ] Verificación
    - [ ] Ejecutar build
    - [ ] Verificación manual del formato JSON
