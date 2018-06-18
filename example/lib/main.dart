import 'package:flutter/material.dart';
import 'package:plugin_android_apk/plugin_android_apk.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
    @override
    _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {

    @override
    initState() {
        super.initState();
    }

    @override
    Widget build(BuildContext context) {
        return new MaterialApp(
            home: new Scaffold(
                appBar: new AppBar(
                    title: new Text('Android apk example'),
                ),
                floatingActionButton: new FloatingActionButton(onPressed: () {
                    AndroidApkPlugin.installApk('........');
                }),
            ),
        );
    }
}
