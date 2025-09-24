package simple.library.xxpermissions

import android.app.Activity
import android.app.Fragment
import android.content.Context
import com.hjq.permissions.XXPermissions

val Context.xxPermissions
    get() = XXPermissions.with(this)

val Fragment.xxPermissions
    get() = XXPermissions.with(this)

val android.support.v4.app.Fragment.xxPermissions
    get() = XXPermissions.with(this)

fun XXPermissions.request(
    activity: Activity,
    onDontAskAgain: (() -> Unit)? = null,
    onDenied: (() -> Unit)? = null,
    onGranted: () -> Unit,
) {
    this.request { grantedList, deniedList ->
        val allGranted = deniedList.isEmpty();
        if (!allGranted) {
            val doNotAskAgain = XXPermissions.isDoNotAskAgainPermissions(activity, deniedList)
            if (doNotAskAgain) {
                onDontAskAgain?.invoke()
            } else {
                onDenied?.invoke()
            }
        } else {
            onGranted()
        }
    }
}