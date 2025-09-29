# XXPermissionsKT

迁移[XXPermissions](https://github.com/getActivity/XXPermissions)到`androidx`，且适配`Kotlin`的写法，并支持在`Jetpack Compose`中使用。

## 添加jitpack依赖

```kotlin
 maven(url = "https://jitpack.io")
```

```kotlin
implementation("com.github.simplepeng:XXPermissions:26.5")
```

```kotlin
val version = "1.0.0"
```

## KTX

```kotlin
implementation("com.github.simplepeng.XXPermissionsKT:library:$version")
```

```kotlin
XXPermissions.with(context)
    .permission(PermissionLists.getRecordAudioPermission())
    .request(context as Activity, onDontAskAgain = {
        context.toast("isDoNotAskAgain")
    }, onDenied = {
        context.toast("shouldShowRationale")
    }, onGranted = {
        hasRecordAudioPermission = true
    })
```

## Jetpack Compose

```kotlin
val permissionsState = rememberXXPermissionsState(PermissionLists.getCameraPermission(), onPermissionResult = { status ->
    if (status.shouldShowRationale) {
        context.toast("shouldShowRationale")
    }
    if (status.isDoNotAskAgain) {
        context.toast("isDoNotAskAgain")
    }
})

Button(onClick = {
    permissionsState.launchPermissionRequest()
}) {
    Text(
        text = buttonText
    )
}

if (permissionsState.status.shouldShowRationale) {
    context.toast("shouldShowRationale")
}
if (permissionsState.status.isDoNotAskAgain) {
    context.toast("isDoNotAskAgain")
}
```

## 版本迭代

* 1.0.0：首次发布