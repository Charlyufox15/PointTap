# Walkthrough: JSON Simplificado y Nombres Personalizados

Se ha optimizado la exportación de datos para cumplir con los requerimientos específicos de limpieza de archivos y organización.

## Cambios Realizados

### JSON Ultra-Limpio
- **Solo Coordenadas:** El archivo JSON ahora solo contiene los campos `latitude` y `longitude`.
- **Exclusión de Metadatos:** Se eliminaron los campos `timestamp` y `label` del archivo final para reducir el peso y mantener solo la información esencial de georreferenciación.
- **Persistencia en la UI:** Aunque no se guardan en el JSON, la fecha y la etiqueta siguen siendo visibles en la lista de la aplicación para referencia del usuario.

### Organización de Archivos
- **Etiquetas en el Nombre:** Al guardar un archivo, el nombre de la "Calle/Camino/Carretera" que ingreses ahora forma parte del nombre del archivo físico.
- **Formato de Archivo:** `[Nombre_de_Via]_[Timestamp].json` (ej: `Ruta_66_1722345678.json`).
- **Compatibilidad:** Se reemplazan los espacios por guiones bajos en el nombre del archivo para asegurar la compatibilidad con todos los sistemas de archivos.

## Cómo usar los nuevos cambios

1. **Captura:** Registra tus puntos normalmente.
2. **Guardar:** Presiona el icono de **Guardar**.
3. **Ingresar Vía:** Escribe el nombre (ej. "Camino Vecinal").
4. **Verificar:** Al abrir la carpeta de descargas, verás el archivo con el nombre que ingresaste. Al abrir el archivo, verás que solo contiene las coordenadas.

## Verificación Técnica
- **@Transient:** Se utilizó la anotación de serialización de Kotlin para excluir campos sin afectar la lógica interna de la app.
- **Sanitización de Nombres:** Se añadió lógica para limpiar el nombre del archivo de caracteres no permitidos.

> [!NOTE]
> Al compartir el texto directamente (vía el botón de compartir), el JSON resultante también estará limpio de etiquetas y tiempos, enviando solo la lista de coordenadas.
