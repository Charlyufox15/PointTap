# Walkthrough: Reversión a Versión 1.0.3 (Formato Simple)

Se ha revertido la aplicación al estado funcional de la versión 1.0.3, volviendo al formato de coordenadas numéricas simplificadas.

## Cambios Realizados

### Reversión de Estructura de Datos
- **Formato JSON Simple:** Se eliminó la estructura GeoJSON. Los archivos vuelven a ser una lista de arreglos numéricos: `[[lat, lon], [lat, lon]]`.
- **Extensión de Archivo:** Se cambió de nuevo la extensión de `.geojson` a `.json`.
- **Tipo de Contenido:** Se restableció el MIME type a `application/json`.

### Configuración del Proyecto
- **Versión:** Se ajustó el `versionCode` a 4 y el `versionName` a `"1.0.3"`.
- **Limpieza:** Se eliminaron los modelos de datos GeoJSON que ya no son necesarios.

## Ejemplo del Formato Restablecido (1.0.3)
`[[10.1234, -20.5678], [10.1235, -20.5679]]`

## Verificación Técnica
- **Build:** La aplicación compila correctamente sin las dependencias de GeoJSON.
- **Pruebas:** Se actualizaron las pruebas unitarias para validar el formato de matriz de números.

> [!NOTE]
> Aunque los archivos ahora son más simples, la funcionalidad de etiquetado en el nombre del archivo y la captura automática se mantienen activas.
