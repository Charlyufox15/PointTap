# Plan de implementación: Exportación en formato GeoJSON

Este plan detalla la transición del formato de coordenadas simple al estándar internacional **GeoJSON**, optimizado para herramientas de mapeo y trazado de carreteras profesional.

## User Review Required

> [!IMPORTANT]
> El archivo ahora seguirá el estándar **RFC 7946 (GeoJSON)**.
> El orden de las coordenadas cambiará a `[longitud, latitud]` como lo exige la norma internacional GeoJSON.
> La extensión del archivo cambiará de `.json` a `.geojson`.

## Proposed Changes

### Capa de Datos

#### [NEW] [GeoJsonModels.kt](file:///C:/Users/charl/AndroidStudioProjects/PointTap/app/src/main/java/com/example/pointtap/data/GeoJsonModels.kt)
- Definir las clases serializables para `FeatureCollection`, `Feature` y `LineString`.

---

### Lógica de Negocio y ViewModel

#### [MODIFY] [MainViewModel.kt](file:///C:/Users/charl/AndroidStudioProjects/PointTap/app/src/main/java/com/example/pointtap/ui/MainViewModel.kt)
- **Refactorizar `exportToJson`:** Transformar la lista de puntos en un objeto GeoJSON de tipo `LineString`.
- **Actualizar `savePointsToFile`:**
    - Cambiar la extensión del archivo a `.geojson`.
    - Ajustar el MIME type a `application/geo+json`.

---

## Verification Plan

### Manual Verification
1. Abrir la app y capturar puntos de una carretera.
2. Presionar "Guardar" e ingresar el nombre de la vía.
3. Verificar que el archivo generado termine en `.geojson`.
4. Abrir el archivo y validar que tenga la estructura:
   ```json
   {
     "type": "FeatureCollection",
     "features": [{
       "type": "Feature",
       "geometry": { "type": "LineString", "coordinates": [[lon, lat], ...] }
     }]
   }
   ```
5. Importar en un visor de mapas (como geojson.io) para validar la ruta.
