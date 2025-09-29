# XXPermissionsKT

迁移[XXPermissions](https://github.com/getActivity/XXPermissions)到`androidx`，且适配`Kotlin`的写法，并支持在`Jetpack Compose`中使用。

## 添加jitpack依赖

```kotlin
maven(url = "https://jitpack.io")
```

## 添加androidx版本的XXPermissions依赖

```kotlin
implementation("com.github.simplepeng:XXPermissions:26.5")
```

## 本库版本

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
implementation("com.github.simplepeng.XXPermissionsKT:library-compose:$version")
```

```kotlin
val permissionsState = rememberXXPermissionsState(PermissionLists.getCameraPermission(), onPermissionResult = { status ->
    if (status.shouldShowRationale) {
        context.toast("shouldShowRationale")
    }
    if (status.isDoNotAskAgain) {
        context.toast("isDoNotAskAgain")
    }
})
val buttonText = if (permissionsState.status.isGranted) "Permissions isGranted" else "Request Permissions - Compose"

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

## 感谢各位大佬打赏🙇🙇🙇！

您的支持是作者努力更新的动力。万水千山总是情，10.24我看行！

| ![](https://raw.githubusercontent.com/simplepeng/merge_pay_code/refs/heads/master/qrcode_alipay.jpg) | ![](https://raw.githubusercontent.com/simplepeng/merge_pay_code/refs/heads/master/qrcode_wxpay.png) | ![](https://raw.githubusercontent.com/simplepeng/merge_pay_code/refs/heads/master/qrcode_qqpay.png) |
| ------------------------------------------------------------ | ----- | ----- |

[打赏链接](https://simplepeng.com/merge_pay_code/) | [赞助列表](https://simplepeng.com/Sponsor/)

## 版本迭代

* 1.0.0：首次发布