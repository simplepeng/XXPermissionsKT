package simple.library.xxpermissions.compose

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.hjq.permissions.XXPermissions
import com.hjq.permissions.permission.base.IPermission

@Composable
internal fun rememberMutablePermissionState(
    permission: IPermission,
    onPermissionResult: (Boolean) -> Unit = {}
): MutablePermissionState {
    val context = LocalContext.current
    val permissionState = remember(permission) {
        MutablePermissionState(permission, context, context.findActivity())
    }

    // Refresh the permission status when the lifecycle is resumed
//    PermissionLifecycleCheckerEffect(permissionState)
//
//    // Remember RequestPermission launcher and assign it to permissionState
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
//        permissionState.refreshPermissionStatus()
//        onPermissionResult(it)
//    }
//    DisposableEffect(permissionState, launcher) {
//        permissionState.launcher = launcher
//        onDispose {
//            permissionState.launcher = null
//        }
//    }

    return permissionState
}

@Stable
internal class MutablePermissionState(
    override val permission: IPermission,
    private val context: Context,
    private val activity: Activity = context.findActivity(),
) : PermissionState {

    override var status: PermissionStatus by mutableStateOf(getPermissionStatus())

    override fun launchPermissionRequest() {
        XXPermissions.with(context)
            .permission(permission)
            .request { grantedList, deniedList ->
                refreshPermissionStatus()
            }
    }

    internal fun refreshPermissionStatus() {
        status = getPermissionStatus()
    }

    private fun getPermissionStatus(): PermissionStatus {
        val hasPermission = XXPermissions.isGrantedPermissions(context, listOf(permission))
        return if (hasPermission) {
            PermissionStatus.Granted
        } else {
            PermissionStatus.Denied(activity.shouldShowRationale(permission.permissionName))
        }
    }
}