# plugin_android_apk

A Flutter plugin for installing android apk.

## Getting Started
add in pubspec.yaml
```dart
plugin_android_apk:0.0.1
```

```dart
import 'package:plugin_android_apk/plugin_android_apk.dart';

AndroidApkPlugin.installApk(toFile.path);
or
AndroidApkPlugin.installApk(toFile.path, authority: 'xx.xx.FileProvider');
```