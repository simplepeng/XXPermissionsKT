package simple.demo.xxpermissionskt

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hjq.permissions.XXPermissions
import com.hjq.permissions.permission.PermissionLists
import simple.demo.xxpermissionskt.ui.theme.XXPermissionsKTTheme
import simple.library.xxpermissions.compose.isDoNotAskAgain
import simple.library.xxpermissions.compose.isGranted
import simple.library.xxpermissions.compose.rememberXXPermissionsState
import simple.library.xxpermissions.compose.shouldShowRationale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XXPermissionsKTTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current

//    val permissionsState = rememberXXPermissionsState(
//        PermissionLists.getCameraPermission(),
//        PermissionLists.getReadPhoneStatePermission(),
//    )
    val hasPermission = ContextCompat.checkSelfPermission(context, PermissionLists.getCameraPermission().permissionName)
    val shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, PermissionLists.getCameraPermission().permissionName)
    val permissionsState = rememberXXPermissionsState(PermissionLists.getCameraPermission(), onPermissionResult = { status ->
        if (status.shouldShowRationale) {
            context.toast("shouldShowRationale")
        }
        if (status.isDoNotAskAgain) {
            context.toast("isDoNotAskAgain")
        }
    })
    val buttonText = if (permissionsState.status.isGranted) "Permissions isGranted" else "Request Permissions"

//    if (permissionsState.status.shouldShowRationale) {
//        context.toast("shouldShowRationale")
//    }
//    if (permissionsState.status.isDoNotAskAgain) {
//        context.toast("isDoNotAskAgain")
//    }

    Scaffold { paddingValues ->
        FlowColumn(
            modifier = Modifier.padding(paddingValues),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(onClick = {
                permissionsState.launchPermissionRequest()
            }) {
                Text(
                    text = buttonText
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    XXPermissionsKTTheme {
        MainScreen()
    }
}