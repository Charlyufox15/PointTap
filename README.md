# Descripción Detallada de PointTap (v1.0.3)

**PointTap** es una herramienta técnica de georreferenciación diseñada para optimizar el trabajo de ingenieros y topógrafos en el trazado de carreteras. La aplicación se especializa en la captura de coordenadas geográficas de alta precisión, ofreciendo una salida de datos limpia y lista para procesos de cálculo matemático.

---

## Funcionalidades Principales

### 1. Captura Dual de Coordenadas
La app ofrece dos métodos de trabajo para adaptarse a cualquier situación en campo:
- **Modo Manual:** Captura puntos específicos con el botón **"+"**, ideal para marcar estacas o puntos de control.
- **Modo Automático (Timer):** Registra la ubicación automáticamente cada **5 segundos**. Incluye controles de **Pausa** y **Stop** para gestionar paradas en el trayecto sin ensuciar los datos.

### 2. Formato de Datos Ultra-Simplificado
Diseñado para la máxima compatibilidad con software de ingeniería:
- **Estructura de Matriz:** Los datos se exportan como una lista de arreglos numéricos `[[lat, lon], [lat, lon]]`.
- **Cero Metadatos:** Se han eliminado llaves y etiquetas de texto del archivo final, entregando únicamente los valores numéricos de las coordenadas.
- **Extensión Estándar:** Los archivos se guardan en formato `.json` compatible con cualquier sistema.

### 3. Organización y Almacenamiento Inteligente
- **Nombres Personalizados:** El nombre de la "Calle/Camino/Carretera" que ingresas se utiliza directamente para nombrar el archivo físico (ej: `Ruta_66_1724089200.json`).
- **Historial Integrado:** Un panel lateral (Drawer) permite visualizar todos los archivos guardados en la carpeta `Download/PointTap` y compartirlos instantáneamente por WhatsApp, Email o Drive.
- **Acceso Directo:** Botón dedicado para abrir el gestor de archivos del dispositivo en la ubicación de los datos.

### 4. Interfaz Ergonómica (Material Design 3)
- **Operación con una Mano:** Todos los controles críticos están ubicados en la parte inferior derecha para facilitar el uso mientras se camina.
- **Seguridad Garantizada:** Gestión integrada de permisos de GPS y Almacenamiento para evitar errores durante la jornada de trabajo.

---

## Especificaciones Técnicas

| Función | Detalle Técnico |
| :--- | :--- |
| **Precisión GPS** | Utiliza `FusedLocationProvider` para máxima exactitud. |
| **Frecuencia Auto** | Ciclo de consulta cada 5000ms gestionado por Coroutines. |
| **Exportación** | Serialización JSON optimizada para arreglos numéricos. |
| **Ubicación** | Carpeta pública `Downloads` para fácil acceso externo. |

---

> [!TIP]
> **Dato de Ingeniería:** El formato de arreglo `[lat, lon]` permite copiar y pegar las coordenadas directamente en motores de cálculo o scripts de trazado sin necesidad de limpiar etiquetas de texto adicionales.

> [!IMPORTANT]
> **Privacidad:** Todos los cálculos se realizan localmente. La app no requiere internet y tus coordenadas de ingeniería nunca salen del dispositivo a menos que decidas compartirlas manualmente.
