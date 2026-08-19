# Release Notes - PointTap v1.0.3 🎯

Esta versión introduce un cambio estructural en los archivos JSON para garantizar la máxima compatibilidad con sistemas de importación de coordenadas puras.

## 🚀 Lo nuevo en v1.0.3

### 🔢 Formato de Coordenadas Puras (Arrays)
- **Cero Datos Alfanuméricos:** Se han eliminado las llaves `{}` y las etiquetas de texto (`"latitude"`, `"longitude"`) del archivo exportado.
- **Estructura de Matriz:** Los puntos ahora se guardan en un formato de arreglo de arreglos `[[lat, lon], [lat, lon]]`.
- **Importación Directa:** Este formato es el estándar para muchos motores de cálculo y herramientas de trazado que requieren solo los valores numéricos sin metadatos.

## 🛠️ Ajustes Realizados
- **Transformación de Datos:** El motor de exportación ahora procesa los puntos internamente para remover los nombres de los campos.
- **Versión Actualizada:** Proyecto actualizado a la versión **1.0.3**.

## 📂 Ejemplo del Nuevo Archivo
```json
[
  [10.123456, -20.654321],
  [10.123500, -20.654400],
  [10.123600, -20.654500]
]
```

---
*PointTap: Datos limpios para una ingeniería más rápida.*
