# Release Notes - PointTap v1.0.1 🛰️

¡Estamos emocionados de presentar la versión **1.0.1** de PointTap! Esta actualización introduce herramientas avanzadas para la automatización del trabajo de campo y una mejor organización de tus archivos geográficos.

## 🚀 ¿Qué hay de nuevo?

### ⏱️ Captura Automática de Puntos
Ya no es necesario presionar el botón por cada punto. Hemos añadido el nuevo **Modo Automático**:
- Registra tu ubicación automáticamente cada **5 segundos**.
- **Controles Dinámicos:** Pausa la grabación si necesitas detenerte y reanúdala cuando estés listo.
- Botón **Stop** dedicado para finalizar el tramo y volver al modo manual.

### 🏷️ Etiquetado de Caminos (Calle/Camino/Carretera)
Mejora la organización de tus datos. Al guardar un archivo:
- Aparecerá un cuadro de diálogo para ingresar una **etiqueta personalizada** (ej. "Ruta 5 - Km 20").
- Esta etiqueta se añade automáticamente a cada punto dentro del archivo JSON, facilitando la identificación en oficina.

### 📂 Historial y Panel Lateral (Drawer)
Accede a tus trabajos anteriores sin salir de la app:
- **Nuevo Panel Lateral:** Desliza desde la izquierda o usa el botón de menú para ver todos tus archivos guardados en `Download/PointTap`.
- **Compartir desde el Historial:** Reenvía archivos JSON antiguos rápidamente con el botón de compartir integrado en la lista.

### 🎨 Interfaz Optimizada
- **Rediseño de Botones:** Los controles de captura se han movido a la parte inferior derecha en una disposición vertical para un manejo más cómodo con una sola mano.
- **Barra de Estado:** Nuevo indicador visual que muestra claramente cuando el modo automático está activo o pausado.

## 🛠️ Mejoras y Correcciones
- **Estabilidad de Archivos:** Se corrigió un error que impedía guardar archivos en ciertos dispositivos al optimizar la ruta hacia la carpeta de Descargas pública.
- **Simplificación de Datos:** Se eliminó el campo de altitud innecesario para centrar el modelo de datos en coordenadas 2D de alta precisión.
- **Iconografía:** Se integró la librería de iconos extendida de Material Design para una navegación más intuitiva.

## 📂 Actualización de Estructura JSON
Los puntos ahora incluyen el campo opcional `label`:
```json
[
  {
    "latitude": 10.123456,
    "longitude": -20.654321,
    "timestamp": 1722345678901,
    "label": "Nombre de la Carretera"
  }
]
```

---
*PointTap sigue evolucionando para ser tu mejor aliado en el trazado de carreteras.*
