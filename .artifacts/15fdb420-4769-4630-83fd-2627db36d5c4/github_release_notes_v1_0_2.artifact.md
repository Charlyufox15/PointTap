# Release Notes - PointTap v1.0.2 💎

Esta versión se enfoca en la **optimización de datos** y la **organización avanzada de archivos**, entregando exactamente la información necesaria en el formato más limpio posible.

## 🚀 Novedades de esta versión

### 📂 Nombres de Archivo Inteligentes
- **Identificación Inmediata:** La etiqueta de "Calle/Camino/Carretera" que ingresas al guardar ahora forma parte del **nombre del archivo físico**.
- **Ejemplo:** Si etiquetas como "Camino Rural", el archivo se guardará como `Camino_Rural_1724089200.json`.
- **Compatibilidad Total:** El sistema limpia automáticamente caracteres especiales y espacios para evitar errores al mover los archivos entre dispositivos o computadoras.

### 🧹 JSON Ultra-Limpio (Solo Coordenadas)
- **Información Esencial:** Atendiendo a las necesidades de integración con otros software, hemos simplificado el archivo JSON.
- **Campos Eliminados:** Se quitaron los campos `timestamp` y `label` del archivo exportado.
- **Resultado Final:** El archivo ahora contiene únicamente una lista limpia de pares `latitude` y `longitude`, facilitando su importación directa en herramientas de dibujo y mapeo.

## 🛠️ Mejoras internas
- **Serialización Selectiva:** Implementamos el uso de campos `@Transient` para que la app siga mostrando fechas y nombres en la lista interna sin "ensuciar" el archivo JSON final.
- **Incremento de Versión:** Proyecto actualizado internamente a la versión **1.0.2**.

## 📂 Ejemplo del Nuevo Formato JSON
```json
[
  {
    "latitude": 10.123456,
    "longitude": -20.654321
  },
  {
    "latitude": 10.123500,
    "longitude": -20.654400
  }
]
```

---
*PointTap: Precisión y simplicidad para el trazado de carreteras.*
