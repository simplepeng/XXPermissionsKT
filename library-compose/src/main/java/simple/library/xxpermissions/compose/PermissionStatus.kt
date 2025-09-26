package simple.library.xxpermissions.compose

import androidx.compose.runtime.Stable

@Stable
sealed interface PermissionStatus {
    object Granted : PermissionStatus
    data class Denied(
        val shouldShowRationale: Boolean
    ) : PermissionStatus
}

val PermissionStatus.isGranted: Boolean
    get() = this is PermissionStatus.Granted

val PermissionStatus.shouldShowRationale: Boolean
    get() = when (this) {
        PermissionStatus.Granted -> false
        is PermissionStatus.Denied -> shouldShowRationale
    }