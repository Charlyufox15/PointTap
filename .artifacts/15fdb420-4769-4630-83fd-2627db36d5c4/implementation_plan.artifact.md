# Plan de implementación: Corrección de Acceso a Carpeta

Este plan detalla la corrección del "Acceso Directo" a la carpeta de puntos, utilizando un Intent más compatible con el gestor de archivos del sistema en Android 10+.

## User Review Required

> [!IMPORTANT]
> Abrir una carpeta específica programáticamente en Android es complejo debido a las restricciones de seguridad (Scoped Storage). Se implementará un flujo de navegación que intentará abrir la carpeta `Download/PointTap` directamente, con un respaldo (fallback) al gestor de descargas general si el primero falla.

## Proposed Changes

### Lógica de Negocio y ViewModel

#### [MODIFY] [MainViewModel.kt](file:///C:/Users/charl/AndroidStudioProjects/PointTap/app/src/main/java/com/example/pointtap/ui/MainViewModel.kt)
- **Refactorización de `openFolder`:**
    - Usar `Intent.ACTION_VIEW` con el MIME type `vnd.android.document/directory`.
    - Intentar apuntar al proveedor de almacenamiento externo (`com.android.externalstorage.documents`).
    - Implementar un segundo respaldo usando `DownloadManager.ACTION_VIEW_DOWNLOADS`.
    - Añadir un último respaldo al selector de archivos general.

---

## Verification Plan

### Manual Verification
1. Abrir la app.
2. Presionar el icono de la Carpeta.
3. Verificar si se abre el gestor de archivos.
4. Si no abre la carpeta exacta, verificar que al menos abra la lista de Descargas donde se encuentra la carpeta "PointTap".
