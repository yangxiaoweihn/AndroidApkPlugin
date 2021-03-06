# plugin_android_apk

A Flutter plugin for apk installation and selecting markets.

## Getting Started
add in pubspec.yaml
```dart
plugin_android_apk: "^1.1.0"
```
### 1. import
```dart
import 'package:plugin_android_apk/plugin_android_apk.dart';
```
### 2. install apk
```dart
AndroidApkPlugin.installApk(toFile.path);
权限描述在xml/aap_apk_cache_filepath.xml文件中，默认apk缓存目录为
context.getExternalCacheDir().getPath()/apk;
context.getCacheDir().getPath()/apk;

可以通过重写该资源文件进行自定义目录
```
### 3. query market apps
```dart
AndroidApkPlugin.queryMarketApps();
```
```json
Response: json string

[
    {
        "appName": String,
        "appIcon": String,//base64
        "appIconWidth": int,
        "appIconHeight": int,
        "packageName": String,
        "versionCode": Integer,
        "versionName": String
    }
]
```

### 4. push market apps panel
```dart
AndroidApkPlugin.pushMarketAppsPanel(_key.currentContext, extra: null);
```

### 5. to market
```dart
AndroidApkPlugin.invokeToMarket();
```

### 6. to market by system
```dart
AndroidApkPlugin.invokeToMarketByDetailsWithSys()
or
AndroidApkPlugin.invokeToMarketBySearchWithSys()
```