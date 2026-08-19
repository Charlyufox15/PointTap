# Walkthrough: Formato de Coordenadas Puro (Array)

Se ha realizado un ajuste crítico en la estructura del archivo JSON para eliminar cualquier dato alfanumérico (llaves y etiquetas) y entregar únicamente un arreglo de coordenadas puras.

## Cambios Realizados

### Estructura JSON de Solo Coordenadas
- **Eliminación de Objetos `{}`:** Se ha eliminado la estructura de objetos con claves `"latitude"` y `"longitude"`.
- **Formato de Arreglo `[]`:** Ahora cada punto se representa como un pequeño arreglo `[latitud, longitud]`.
- **Resultado Final:** El archivo completo es ahora una matriz de números, eliminando texto innecesario y facilitando la importación en sistemas que esperan coordenadas brutas.

## Ejemplo del Nuevo Formato
Antes:
`[{"latitude": 10.1, "longitude": -20.1}, ...]`

Ahora (v1.0.3):
`[[10.1, -20.1], [10.2, -20.2], ...]`

## Verificación Técnica
- **Mapeo Dinámico:** El `MainViewModel` ahora transforma los objetos `GeoPoint` en listas simples de números antes de la serialización.
- **Limpieza Total:** Se eliminaron todas las referencias a claves de texto dentro del flujo de exportación.

> [!IMPORTANT]
> Este cambio asegura que el archivo sea interpretado directamente como una lista de coordenadas matemáticas, sin datos alfanuméricos que puedan causar errores en herramientas de ingeniería legacy.
