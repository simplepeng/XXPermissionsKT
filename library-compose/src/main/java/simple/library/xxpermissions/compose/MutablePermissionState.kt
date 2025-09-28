package simple.library.xxpermissions.compose

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.hjq.permissions.XXPermissions
import com.hjq.permissions.permission.base.IPermission

@Composable
internal fun rememberMutablePermissionState(
    permission: List<IPermission>,
    onPermissionResult: (PermissionStatus) -> Unit = {}
): MutablePermissionState {
    val context = LocalContext.current
    val permissionState = remember(permission) {
        MutablePermissionState(permission, context, context.findActivity(), onPermissionResult)
    }

    // Refresh the permission status when the lifecycle is resumed
    PermissionLifecycleCheckerEffect(permissionState)

    return permissionState
}

@Stable
internal class MutablePermissionState(
    override val permission: List<IPermission>,
    private val context: Context,
    private val activity: Activity = context.findActivity(),
    private val onPermissionResult: (PermissionStatus) -> Unit = {}
) : PermissionState {

    override var status: PermissionStatus by mutableStateOf(getPermissionStatus())

    override fun launchPermissionRequest() {
        XXPermissions.with(context)
            .permissions(permission)
            .request { grantedList, deniedList ->
                refreshPermissionStatus()
                onPermissionResult.invoke(status)
            }
    }

    internal fun refreshPermissionStatus() {
        status = getPermissionStatus()
    }

    private fun getPermissionStatus(): PermissionStatus {
        val hasPermission = XXPermissions.isGrantedPermissions(context, permission)
        return if (hasPermission) {
            PermissionStatus.Granted
        } else {
            PermissionStatus.Denied(
                shouldShowRationale = permission.any { activity.shouldShowRationale(it.permissionName) },
                isDoNotAskAgain = XXPermissions.isDoNotAskAgainPermissions(activity, permission)
            )
        }
    }
}

@Composable
internal fun PermissionLifecycleCheckerEffect(
    permissionState: MutablePermissionState,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_RESUME
) {
    val permissionCheckerObserver = remember(permissionState) {
        LifecycleEventObserver { _, event ->
            if (event == lifecycleEvent) {
                if (permissionState.status != PermissionStatus.Granted) {
                    permissionState.refreshPermissionStatus()
                }
            }
        }
    }
    val lifecycle = androidx.lifecycle.compose.LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle, permissionCheckerObserver) {
        lifecycle.addObserver(permissionCheckerObserver)
        onDispose { lifecycle.removeObserver(permissionCheckerObserver) }
    }
}
