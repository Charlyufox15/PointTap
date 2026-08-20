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
import com.example.pointtap.data.SavedFile
import com.example.pointtap.location.LocationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.OutputStream

enum class AutoCaptureStatus {
    IDLE, RUNNING, PAUSED
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val locationService = LocationService(application)
    
    private val _points = mutableStateListOf<GeoPoint>()
    val points: List<GeoPoint> = _points

    private val _savedFiles = mutableStateListOf<SavedFile>()
    val savedFiles: List<SavedFile> = _savedFiles

    private val _isLocating = mutableStateOf(false)
    val isLocating: State<Boolean> = _isLocating

    // Automatic capture state
    private val _autoCaptureStatus = mutableStateOf(AutoCaptureStatus.IDLE)
    val autoCaptureStatus: State<AutoCaptureStatus> = _autoCaptureStatus
    
    private var autoCaptureJob: Job? = null

    init {
        loadSavedFiles(application)
    }

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

    fun startAutoCapture() {
        if (_autoCaptureStatus.value == AutoCaptureStatus.IDLE) {
            _autoCaptureStatus.value = AutoCaptureStatus.RUNNING
            runAutoCaptureLoop()
        }
    }

    fun pauseAutoCapture() {
        if (_autoCaptureStatus.value == AutoCaptureStatus.RUNNING) {
            _autoCaptureStatus.value = AutoCaptureStatus.PAUSED
            autoCaptureJob?.cancel()
        }
    }

    fun resumeAutoCapture() {
        if (_autoCaptureStatus.value == AutoCaptureStatus.PAUSED) {
            _autoCaptureStatus.value = AutoCaptureStatus.RUNNING
            runAutoCaptureLoop()
        }
    }

    fun stopAutoCapture() {
        _autoCaptureStatus.value = AutoCaptureStatus.IDLE
        autoCaptureJob?.cancel()
        autoCaptureJob = null
    }

    private fun runAutoCaptureLoop() {
        autoCaptureJob?.cancel()
        autoCaptureJob = viewModelScope.launch {
            while (_autoCaptureStatus.value == AutoCaptureStatus.RUNNING) {
                addCurrentPoint()
                delay(5000) // 5 seconds
            }
        }
    }

    fun exportToJson(): String {
        // Map points to a simple list of coordinates [lat, lon]
        val simplePoints = _points.map { listOf(it.latitude, it.longitude) }
        return Json.encodeToString(simplePoints)
    }

    fun savePointsToFile(context: Context, label: String? = null) {
        if (_points.isEmpty()) {
            Toast.makeText(context, "No hay puntos para guardar", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            val success = withContext(Dispatchers.IO) {
                try {
                    val json = exportToJson()
                    val timestamp = System.currentTimeMillis()
                    val filename = if (label.isNullOrBlank()) {
                        "puntos_$timestamp.json"
                    } else {
                        "${label.replace(" ", "_")}_$timestamp.json"
                    }
                    
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "application/json")
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
                loadSavedFiles(context)
            } else {
                Toast.makeText(context, "Error al guardar el archivo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loadSavedFiles(context: Context) {
        viewModelScope.launch {
            val files = withContext(Dispatchers.IO) {
                val list = mutableListOf<SavedFile>()
                val projection = arrayOf(
                    MediaStore.MediaColumns._ID,
                    MediaStore.MediaColumns.DISPLAY_NAME,
                    MediaStore.MediaColumns.DATE_ADDED
                )
                
                val selection = "${MediaStore.MediaColumns.RELATIVE_PATH} LIKE ?"
                val selectionArgs = arrayOf("Download/PointTap%")
                val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"

                context.contentResolver.query(
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
                )?.use { cursor ->
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                    val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                    val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)

                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(idColumn)
                        val name = cursor.getString(nameColumn)
                        val date = cursor.getLong(dateColumn) * 1000 // To milliseconds
                        val contentUri = Uri.withAppendedPath(MediaStore.Downloads.EXTERNAL_CONTENT_URI, id.toString())
                        list.add(SavedFile(name, contentUri, date))
                    }
                }
                list
            }
            _savedFiles.clear()
            _savedFiles.addAll(files)
        }
    }

    fun shareSavedFile(context: Context, file: SavedFile) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/json"
            putExtra(Intent.EXTRA_STREAM, file.uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Compartir ${file.name}"))
    }

    fun openFolder(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            val authority = "com.android.externalstorage.documents"
            val documentId = "primary:Download/PointTap"
            val uri = Uri.parse("content://$authority/document/" + Uri.encode(documentId))
            
            setDataAndType(uri, "vnd.android.document/directory")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            try {
                val downloadIntent = Intent(android.app.DownloadManager.ACTION_VIEW_DOWNLOADS)
                context.startActivity(downloadIntent)
            } catch (e2: Exception) {
                val fallbackIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "*/*"
                    addCategory(Intent.CATEGORY_OPENABLE)
                }
                context.startActivity(Intent.createChooser(fallbackIntent, "Abrir archivos"))
            }
        }
    }
}
