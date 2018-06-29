# plugin_android_apk

A Flutter plugin for installing android apk.

## Getting Started
add in pubspec.yaml
```dart
plugin_android_apk: "^0.0.2"
```
### 1. import
```dart
import 'package:plugin_android_apk/plugin_android_apk.dart';
```
### 2. install apk
```dart
AndroidApkPlugin.installApk(toFile.path);
or
AndroidApkPlugin.installApk(toFile.path, authority: 'xx.xx.FileProvider');
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