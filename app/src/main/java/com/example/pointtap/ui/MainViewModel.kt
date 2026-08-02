package com.example.pointtap.ui

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pointtap.data.GeoPoint
import com.example.pointtap.location.LocationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.OutputStream

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val locationService = LocationService(application)
    
    private val _points = mutableStateListOf<GeoPoint>()
    val points: List<GeoPoint> = _points

    private val _isLocating = mutableStateOf(false)
    val isLocating: State<Boolean> = _isLocating

    fun addCurrentPoint() {
        viewModelScope.launch {
            _isLocating.value = true
            val location = locationService.getCurrentLocation()
            if (location != null) {
                _points.add(
                    GeoPoint(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                )
            }
            _isLocating.value = false
        }
    }

    fun exportToJson(): String {
        return Json.encodeToString(_points.toList())
    }

    fun savePointsToFile(context: Context) {
        if (_points.isEmpty()) {
            Toast.makeText(context, "No hay puntos para guardar", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            val success = withContext(Dispatchers.IO) {
                try {
                    val json = exportToJson()
                    val filename = "puntos_${System.currentTimeMillis()}.json"
                    
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "application/json")
                        // minSdk is 29 (Android 10), so RELATIVE_PATH is available
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/PointTap")
                    }

                    val uri: Uri? = context.contentResolver.insert(
                        MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                        contentValues
                    )

                    uri?.let {
                        val outputStream: OutputStream? = context.contentResolver.openOutputStream(it)
                        outputStream?.use { stream ->
                            stream.write(json.toByteArray())
                        }
                        true
                    } ?: false
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
            }

            if (success) {
                Toast.makeText(context, "Archivo guardado en Download/PointTap", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Error al guardar el archivo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun openFolder(context: Context) {
        // Option 1: Try to open the specific folder in Download/PointTap
        val intent = Intent(Intent.ACTION_VIEW).apply {
            // For modern Android, we use the Documents provider if possible
            val authority = "com.android.externalstorage.documents"
            val documentId = "primary:Download/PointTap"
            val uri = Uri.parse("content://$authority/document/" + Uri.encode(documentId))
            
            setDataAndType(uri, "vnd.android.document/directory")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            // Option 2: Fallback to the system Downloads UI
            try {
                val downloadIntent = Intent(android.app.DownloadManager.ACTION_VIEW_DOWNLOADS)
                context.startActivity(downloadIntent)
            } catch (e2: Exception) {
                // Option 3: Final fallback to general file picker
                val fallbackIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "*/*"
                    addCategory(Intent.CATEGORY_OPENABLE)
                }
                context.startActivity(Intent.createChooser(fallbackIntent, "Abrir archivos"))
            }
        }
    }
}
