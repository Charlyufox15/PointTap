# Tareas: Limpieza de JSON y Nombres de Archivo Dinámicos

- [x] Actualizar modelo `GeoPoint.kt`
    - [x] Excluir `timestamp` y `label` de la serialización JSON (usando `@Transient`)
- [x] Modificar `MainViewModel.kt`
    - [x] Actualizar `savePointsToFile` para incluir la etiqueta en el nombre del archivo
    - [x] Simplificar `exportToJson` (ya no requiere procesar la etiqueta en los datos)
- [x] Verificación
    - [x] Ejecutar build
    - [x] Verificación manual de la estructura JSON
- [x] Actualizar versión a 1.0.2
- [x] Crear Release Notes v1.0.2
