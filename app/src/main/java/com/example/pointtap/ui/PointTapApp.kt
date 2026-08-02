package com.example.pointtap.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pointtap.data.GeoPoint
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointTapApp(viewModel: MainViewModel) {
    val context = LocalContext.current
    val points = viewModel.points
    val isLocating = viewModel.isLocating.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PointTap") },
                actions = {
                    IconButton(onClick = { viewModel.savePointsToFile(context) }) {
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
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
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
        }
    }
}
