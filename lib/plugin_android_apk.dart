import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:plugin_android_apk/plugin/market/AppInfo.dart';
import 'package:plugin_android_apk/plugin/market/MarketAppPanel.dart';
import 'plugin/apk/LLog.dart';

/// Created by yangxiaowei
class AndroidApkPlugin {
    static const MethodChannel _channel = const MethodChannel('ws.dyt.plugin/android_apk');

    static const String _TAG = 'AndroidApk';

    //install apk
    static Future<String> installApk(String apkPath, {String authority}) async{
        print('$_TAG  apk-path: $apkPath , authority: $authority');

        Map<String, dynamic> data = {
            'debug': LLog.debug,
            'path': apkPath, 
            'authority': authority,
        };
        return await _channel.invokeMethod('installApk', data);
    }

    //query market
    ///[extra] extra want to query market packages
    static Future<String> queryMarketApps({List<String> extra}) async{

        Map<String, dynamic> data = {
            'debug': LLog.debug,
            'marketAppPackages': extra,
        };
        return await _channel.invokeMethod('queryMarketApp', data);
    }

    static Future<Null> pushMarketAppsPanel(BuildContext context, {List<String> extra}) async{

        String marketsJson = await queryMarketApps(extra: extra);
        MarketAppPanel.invokeMarketPanel(context, AppInfo.transfer(marketsJson));
    }

    //to market
    /// [marketPackage] if null, will jump to default market
    static Future<String> invokeToMarket({String marketPackage}) async{

        Map<String, dynamic> data = {
            'debug': LLog.debug,
            'marketAppPackage': marketPackage,
        };
        return await _channel.invokeMethod('toMarket', data);
    }
}
