package simple.demo.xxpermissionskt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hjq.permissions.XXPermissions
import com.hjq.permissions.permission.PermissionLists
import simple.demo.xxpermissionskt.ui.theme.XXPermissionsKTTheme
import simple.library.xxpermissions.compose.isGranted
import simple.library.xxpermissions.compose.rememberXXPermissionsState

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
    val permissionsState = rememberXXPermissionsState(PermissionLists.getCameraPermission())
    val buttonText = if (permissionsState.status.isGranted) "Permissions isGranted" else "Request Permissions"

    Scaffold { paddingValues ->
        FlowColumn(
            modifier = Modifier.padding(paddingValues)
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