package simple.library.xxpermissions.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalInspectionMode
import com.hjq.permissions.permission.base.IPermission

@Composable
fun rememberXXPermissionsState(
    permission: IPermission,
    onPermissionResult: (Boolean) -> Unit = {}
): PermissionState {
    return rememberXXPermissionsState(permission, onPermissionResult, PermissionStatus.Granted)
}

@Composable
fun rememberXXPermissionsState(
    permission: IPermission,
    onPermissionResult: (Boolean) -> Unit = {},
    previewPermissionStatus: PermissionStatus = PermissionStatus.Granted
): PermissionState {
    return when {
        LocalInspectionMode.current -> PreviewPermissionState(permission, previewPermissionStatus)
        else -> rememberMutablePermissionState(permission, onPermissionResult)
    }
}

@Stable
interface PermissionState {
    val permission: IPermission
    val status: PermissionStatus
    fun launchPermissionRequest(): Unit
}

@Immutable
internal class PreviewPermissionState(
    override val permission: IPermission,
    override val status: PermissionStatus
) : PermissionState {
    override fun launchPermissionRequest() {}
}


