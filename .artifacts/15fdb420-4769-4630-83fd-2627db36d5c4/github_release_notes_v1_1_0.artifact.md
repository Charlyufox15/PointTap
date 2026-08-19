# Release Notes - PointTap v1.1.0 🗺️

¡Esta es una actualización mayor! Hemos migrado el motor de exportación al estándar internacional **GeoJSON**, lo que permite una integración directa y profesional con software de ingeniería y cartografía.

## 🚀 Gran Novedad: Soporte GeoJSON
- **Estándar Profesional:** Los archivos ahora se guardan con la extensión `.geojson` y siguen la estructura oficial `FeatureCollection`.
- **Geometría de Línea (LineString):** Tus puntos se agrupan automáticamente en una línea continua, ideal para representar el trazado de carreteras en herramientas como QGIS, ArcGIS o Google Earth.
- **Metadatos Integrados:** El nombre de la vía ("Calle/Camino/Carretera") ahora se guarda internamente dentro de las propiedades del archivo GeoJSON, no solo en el nombre del archivo.

## 🛠️ Ajustes Técnicos
- **Orden de Coordenadas:** Se ha ajustado al estándar `[Longitud, Latitud]`.
- **MIME Type:** Actualizado a `application/geo+json` para una mejor identificación por parte del sistema operativo.
- **Versión:** Proyecto actualizado a la versión **1.1.0**.

## 📂 Ejemplo del Nuevo Formato GeoJSON
```json
{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "properties": { "name": "Ruta 66" },
      "geometry": {
        "type": "LineString",
        "coordinates": [
          [-104.123456, 35.654321],
          [-104.123500, 35.654400]
        ]
      }
    }
  ]
}
```

---
*PointTap: Conectando tus mediciones con los estándares del mundo real.*
