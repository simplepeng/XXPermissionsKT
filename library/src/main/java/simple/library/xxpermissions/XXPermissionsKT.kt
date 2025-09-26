package simple.library.xxpermissions

import android.app.Activity
import android.app.Fragment
import android.content.Context
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.OnPermissionDescription
import com.hjq.permissions.OnPermissionInterceptor
import com.hjq.permissions.XXPermissions
import com.hjq.permissions.fragment.factory.PermissionFragmentFactory
import com.hjq.permissions.permission.base.IPermission

val Context.xxPermissions
    get() = XXPermissions.with(this)

val Fragment.xxPermissions
    get() = XXPermissions.with(this)

//val android.support.v4.app.Fragment.xxPermissions
//    get() = XXPermissions.with(this)

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

fun XXPermissions.interceptor(
    onRequestPermissionStart: ((
        activity: Activity,
        requestList: List<IPermission>,
        fragmentFactory: PermissionFragmentFactory<*, *>,
        permissionDescription: OnPermissionDescription,
        callback: OnPermissionCallback?
    ) -> Unit)? = null,
    onRequestPermissionEnd: ((
        activity: Activity,
        skipRequest: Boolean,
        requestList: List<IPermission>,
        grantedList: List<IPermission>,
        deniedList: List<IPermission>,
        callback: OnPermissionCallback?
    ) -> Unit)? = null,
    dispatchPermissionRequest: ((
        activity: Activity,
        requestList: List<IPermission>,
        fragmentFactory: PermissionFragmentFactory<*, *>,
        permissionDescription: OnPermissionDescription,
        callback: OnPermissionCallback?
    ) -> Unit)? = null,
) {
    this.interceptor(object : OnPermissionInterceptor {
        override fun onRequestPermissionStart(
            activity: Activity,
            requestList: List<IPermission>,
            fragmentFactory: PermissionFragmentFactory<*, *>,
            permissionDescription: OnPermissionDescription,
            callback: OnPermissionCallback?
        ) {
            super.onRequestPermissionStart(activity, requestList, fragmentFactory, permissionDescription, callback)
            onRequestPermissionStart?.invoke(activity, requestList, fragmentFactory, permissionDescription, callback)
        }

        override fun onRequestPermissionEnd(
            activity: Activity,
            skipRequest: Boolean,
            requestList: List<IPermission>,
            grantedList: List<IPermission>,
            deniedList: List<IPermission>,
            callback: OnPermissionCallback?
        ) {
            super.onRequestPermissionEnd(activity, skipRequest, requestList, grantedList, deniedList, callback)
            onRequestPermissionEnd?.invoke(activity, skipRequest, requestList, grantedList, deniedList, callback)
        }

        override fun dispatchPermissionRequest(
            activity: Activity,
            requestList: List<IPermission>,
            fragmentFactory: PermissionFragmentFactory<*, *>,
            permissionDescription: OnPermissionDescription,
            callback: OnPermissionCallback?
        ) {
            super.dispatchPermissionRequest(activity, requestList, fragmentFactory, permissionDescription, callback)
            dispatchPermissionRequest?.invoke(activity, requestList, fragmentFactory, permissionDescription, callback)
        }
    })
}

fun XXPermissions.description(
    askWhetherRequestPermission: ((
        activity: Activity,
        requestList: List<IPermission?>,
        continueRequestRunnable: Runnable,
        breakRequestRunnable: Runnable
    ) -> Unit)? = null,
    onRequestPermissionStart: ((
        activity: Activity,
        requestList: List<IPermission>
    ) -> Unit)? = null,
    onRequestPermissionEnd: ((
        activity: Activity,
        requestList: List<IPermission>
    ) -> Unit)? = null,
) {
    this.description(object : OnPermissionDescription {
        override fun askWhetherRequestPermission(
            activity: Activity,
            requestList: List<IPermission?>,
            continueRequestRunnable: Runnable,
            breakRequestRunnable: Runnable
        ) {
            askWhetherRequestPermission?.invoke(activity, requestList, continueRequestRunnable, breakRequestRunnable)
        }

        override fun onRequestPermissionStart(
            activity: Activity,
            requestList: List<IPermission>
        ) {
            onRequestPermissionStart?.invoke(activity, requestList)
        }

        override fun onRequestPermissionEnd(
            activity: Activity,
            requestList: List<IPermission>
        ) {
            onRequestPermissionEnd?.invoke(activity, requestList)
        }
    })
}