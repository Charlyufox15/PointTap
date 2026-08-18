package com.example.pointtap.ui

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pointtap.data.GeoPoint
import com.example.pointtap.data.SavedFile
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointTapApp(viewModel: MainViewModel) {
    val context = LocalContext.current
    val points = viewModel.points
    val savedFiles = viewModel.savedFiles
    val isLocating = viewModel.isLocating.value
    val autoCaptureStatus = viewModel.autoCaptureStatus.value
    
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    var showLabelDialog by remember { mutableStateOf(false) }
    var roadLabel by remember { mutableStateOf("") }

    if (showLabelDialog) {
        AlertDialog(
            onDismissRequest = { showLabelDialog = false },
            title = { Text("Etiquetar Camino") },
            text = {
                Column {
                    Text("Ingresa el nombre de la Calle/Camino/Carretera:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = roadLabel,
                        onValueChange = { roadLabel = it },
                        label = { Text("Nombre") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.savePointsToFile(context, roadLabel.ifBlank { null })
                    showLabelDialog = false
                    roadLabel = ""
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLabelDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Historial de Archivos",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                if (savedFiles.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay archivos guardados.")
                    }
                } else {
                    LazyColumn {
                        items(savedFiles) { file ->
                            SavedFileItem(file) {
                                viewModel.shareSavedFile(context, file)
                            }
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("PointTap") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú de Historial")
                        }
                    },
                    actions = {
                        IconButton(onClick = { 
                            if (points.isNotEmpty()) {
                                showLabelDialog = true 
                            } else {
                                viewModel.savePointsToFile(context)
                            }
                        }) {
                            Icon(Icons.Default.Save, contentDescription = "Guardar en archivo")
                        }
                        IconButton(onClick = { viewModel.openFolder(context) }) {
                            Icon(Icons.Default.Folder, contentDescription = "Abrir carpeta")
                        }
                        IconButton(onClick = {
                            val json = viewModel.exportToJson()
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, json)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "Compartir Puntos JSON")
                            context.startActivity(shareIntent)
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "Compartir")
                        }
                    }
                )
            },
            floatingActionButton = {
                if (autoCaptureStatus == AutoCaptureStatus.IDLE) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Botón Automático (Reloj)
                        FloatingActionButton(
                            onClick = { viewModel.startAutoCapture() },
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(Icons.Default.Timer, contentDescription = "Puntos automáticos")
                        }
                        
                        // Botón Manual (Suma)
                        FloatingActionButton(
                            onClick = { viewModel.addCurrentPoint() },
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            if (isLocating) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                            } else {
                                Icon(Icons.Default.Add, contentDescription = "Marcar Punto")
                            }
                        }
                    }
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        // Pausa / Reanudar
                        FloatingActionButton(
                            onClick = {
                                if (autoCaptureStatus == AutoCaptureStatus.RUNNING) {
                                    viewModel.pauseAutoCapture()
                                } else {
                                    viewModel.resumeAutoCapture()
                                }
                            },
                            containerColor = if (autoCaptureStatus == AutoCaptureStatus.RUNNING) Color.Yellow else Color.Green
                        ) {
                            if (autoCaptureStatus == AutoCaptureStatus.RUNNING) {
                                Icon(Icons.Default.Pause, contentDescription = "Pausar")
                            } else {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Reanudar")
                            }
                        }
                        
                        // Stop
                        FloatingActionButton(
                            onClick = { viewModel.stopAutoCapture() },
                            containerColor = Color.Red
                        ) {
                            Icon(Icons.Default.Stop, contentDescription = "Terminar automático")
                        }
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                if (autoCaptureStatus != AutoCaptureStatus.IDLE) {
                    Surface(
                        color = if (autoCaptureStatus == AutoCaptureStatus.RUNNING) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (autoCaptureStatus == AutoCaptureStatus.RUNNING) "MODO AUTOMÁTICO ACTIVO (Cada 5s)" else "MODO AUTOMÁTICO PAUSADO",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = if (autoCaptureStatus == AutoCaptureStatus.RUNNING) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
                
                if (points.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay puntos marcados aún.")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(points) { point ->
                            PointItem(point)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PointItem(point: GeoPoint) {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    val dateStr = sdf.format(Date(point.timestamp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Punto: ${point.latitude}, ${point.longitude}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Fecha: $dateStr", style = MaterialTheme.typography.bodySmall)
            point.label?.let {
                Text(text = "Etiqueta: $it", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun SavedFileItem(file: SavedFile, onShare: () -> Unit) {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val dateStr = sdf.format(Date(file.date))

    ListItem(
        headlineContent = { Text(file.name) },
        supportingContent = { Text(dateStr) },
        trailingContent = {
            IconButton(onClick = onShare) {
                Icon(Icons.Default.Share, contentDescription = "Compartir archivo")
            }
        },
        modifier = Modifier.clickable { onShare() }
    )
}
