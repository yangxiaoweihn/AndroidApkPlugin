import 'dart:async';

import 'package:flutter/services.dart';

/// Created by yangxiaowei
class AndroidApkPlugin {
    static const MethodChannel _channel = const MethodChannel('ws.dyt.plugin/android_apk');

    static const String _TAG = 'AndroidApk';
    static Future<dynamic> installApk(String apkPath, {String authority}) async{
        print('$_TAG  apk-path: $apkPath , authority: $authority');
        return _channel.invokeMethod('installApk', {'path': apkPath, 'authority': authority});
    }
}
