package com.example.pointtap

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pointtap.ui.MainViewModel
import com.example.pointtap.ui.PointTapApp
import com.example.pointtap.ui.theme.PointTapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PointTapTheme {
                AppPermissionWrapper {
                    val viewModel: MainViewModel = viewModel()
                    PointTapApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun AppPermissionWrapper(content: @Composable () -> Unit) {
    val context = LocalContext.current
    
    val requiredPermissions = remember {
        mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).apply {
            // Storage permissions behavior changes based on API level
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                // For API 30-32, WRITE is scoped, but READ might be needed for some flows
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            // For API 33+, we use granular media permissions, but for JSON in Documents
            // MediaStore doesn't strictly require these if we only own the files.
            // However, to satisfy "Storage Permissions" request:
        }.toTypedArray()
    }

    var permissionsGranted by remember {
        mutableStateOf(
            requiredPermissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { result ->
            permissionsGranted = result.values.all { it }
        }
    )

    if (permissionsGranted) {
        content()
    } else {
        Scaffold { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { launcher.launch(requiredPermissions) }) {
                    Text("Conceder Permisos (Ubicación y Almacenamiento)")
                }
            }
        }
    }
}
