# Walkthrough: Rediseño de Controles de Captura

Se ha optimizado la ubicación de los controles para mejorar la ergonomía y facilitar el acceso a los dos modos de captura (Manual y Automático).

## Cambios Realizados

### Nueva Disposición Vertical (FABs)
- **Consolidación de Controles:** El botón de modo automático se ha movido de la barra superior a la esquina inferior derecha, justo encima del botón de captura manual.
- **Jerarquía Visual:**
    - **Botón Superior (Reloj):** Activa el modo de captura automática cada 5 segundos. Tiene un color secundario para diferenciarse.
    - **Botón Inferior (+):** Realiza la captura manual de un único punto. Es el botón principal en color sólido.
- **Espaciado Ergonómico:** Ambos botones están alineados verticalmente con una separación adecuada para evitar toques accidentales.

## Cómo usar el nuevo diseño

1. **Modo Manual:** Pulsa el botón circular grande con el signo **"+"** en la esquina inferior.
2. **Modo Automático:** Pulsa el botón circular que está justo encima con el icono del **reloj (Timer)**.
    - Al hacerlo, ambos botones se ocultarán y aparecerán los controles de **Pausa** y **Stop** ya conocidos.
3. **Regreso al Inicio:** Al pulsar **Stop (Rojo)**, volverás a ver la columna de dos botones (Auto y Manual).

## Verificación Técnica
- **Diseño:** Se utilizó un componente `Column` dentro del `floatingActionButton` del Scaffold para agrupar los botones.
- **Build:** Compilación exitosa.

> [!TIP]
> Esta nueva ubicación permite controlar toda la operación de captura con un solo pulgar, sin tener que alcanzar la parte superior de la pantalla.
