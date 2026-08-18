# Plan de implementación: Captura Automática de Puntos

Este plan detalla la adición de una función de captura automática que registra puntos GPS cada 5 segundos, con controles de pausa y parada.

## User Review Required

> [!IMPORTANT]
> La captura automática se ejecutará en segundo plano (mientras la app esté abierta) cada 5 segundos.
> La interfaz cambiará dinámicamente: el botón de captura única se ocultará para dar paso a los controles de **Pausa** y **Stop** durante el modo automático.

## Proposed Changes

### Lógica de Negocio y ViewModel

#### [MODIFY] [MainViewModel.kt](file:///C:/Users/charl/AndroidStudioProjects/PointTap/app/src/main/java/com/example/pointtap/ui/MainViewModel.kt)
- Añadir un estado `AutoCaptureState` (IDLE, RUNNING, PAUSED).
- Implementar un `Job` de Coroutine para el temporizador de 5 segundos.
- Funciones:
    - `startAutoCapture()`: Inicia el bucle de captura.
    - `pauseAutoCapture()`: Detiene el bucle temporalmente pero mantiene el estado.
    - `resumeAutoCapture()`: Reanuda desde la pausa.
    - `stopAutoCapture()`: Detiene todo y limpia el estado para volver al modo manual.

---

### Interfaz de Usuario (Compose)

#### [MODIFY] [PointTapApp.kt](file:///C:/Users/charl/AndroidStudioProjects/PointTap/app/src/main/java/com/example/pointtap/ui/PointTapApp.kt)
- **Botón de Modo Automático:** Añadir un nuevo botón (posiblemente al lado del "+" o dentro de un menú) para activar el modo automático.
- **Controles Dinámicos:**
    - Si el modo automático está activo: Mostrar botones de **Pausa/Play** y **Stop**.
    - Si no está activo: Mostrar el botón estándar de **"+"**.
- **Feedback:** Mostrar un indicador visual (como un texto o icono parpadeante) que informe que el modo automático está trabajando.

---

## Verification Plan

### Manual Verification
1. Abrir la app.
2. Presionar el botón de "Captura Automática".
3. Verificar que se agreguen puntos a la lista cada 5 segundos sin intervención.
4. Presionar "Pausa" y verificar que se detenga la captura.
5. Presionar "Play" y verificar que continúe.
6. Presionar "Stop" y verificar que la interfaz regrese al modo de captura manual (+).
