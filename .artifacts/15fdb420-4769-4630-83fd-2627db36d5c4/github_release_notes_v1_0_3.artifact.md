# Release Notes - PointTap v1.0.3 🎯

Esta versión establece el estándar de **simplicidad y compatibilidad técnica**, optimizando el formato de los datos para herramientas de ingeniería que requieren coordenadas numéricas puras.

## 🚀 Lo nuevo en v1.0.3

### 🔢 Formato de Coordenadas Puras (Arrays)
Hemos simplificado la estructura interna del archivo para ofrecer la máxima compatibilidad:
- **Cero Texto Innecesario:** Se han eliminado las llaves `{}` y las etiquetas de campo (`"latitude"`, `"longitude"`).
- **Estructura de Matriz:** Los puntos ahora se guardan como una lista de arreglos numéricos: `[[lat, lon], [lat, lon]]`.
- **Extensión Estándar:** Los archivos utilizan la extensión `.json` para ser reconocidos por cualquier editor de texto o software de cálculo.

### ⏲️ Potencia en el Campo (Mantenido)
Se mantienen todas las funciones avanzadas introducidas en versiones anteriores:
- **Captura Automática:** Registro de ubicación cada 5 segundos con controles de pausa.
- **Etiquetado Inteligente:** El nombre de la vía se utiliza para nombrar el archivo físico automáticamente.
- **Historial Completo:** Panel lateral para revisar y compartir mediciones pasadas.

## 🛠️ Detalles de la Versión
- **Formato de Salida:** JSON (Matriz de números).
- **Ubicación:** `Download/PointTap`.
- **Versión de Compilación:** 4.

## 📂 Ejemplo del Formato de Datos
```json
[
  [14.123456, -90.654321],
  [14.123500, -90.654400],
  [14.123600, -90.654500]
]
```

---
*PointTap: La herramienta más rápida y limpia para el trazado de rutas.*
