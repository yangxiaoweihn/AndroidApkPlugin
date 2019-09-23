import 'package:flutter/material.dart';
import 'package:plugin_android_apk/plugin_android_apk.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
    @override
    _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {

    GlobalKey<ScaffoldState> _key = new GlobalKey<ScaffoldState>();
    @override
    initState() {
        super.initState();
    }

    @override
    Widget build(BuildContext context) {
        return new MaterialApp(
            home: new Scaffold(
                key: _key,
                appBar: new AppBar(
                    title: new Text('Android apk example'),
                ),

                body: this._body(),
            ),
        );
    }

    String _log = '';
    void _printLog(dynamic log) {

        setState(() {
            _log = '$log';
        });
    }
    Widget _body() {

        ListView listView = new ListView(
            children: <Widget>[
                new ListTile(
                    title: new Text('install apk'),
                    onTap: () {
                        String path = '/data/user/0/ws.dyt.pluginandroidapkexample/cache/apk/app.apk';
                        this._printLog('invoke install: $path');
                        AndroidApkPlugin.installApk(path).then((result) {
                            this._printLog('invoke install callback: \n$result');
                        });
                    },
                ),

                new ListTile(
                    title: new Text('query market'),
                    onTap: () {
                        this._printLog('invoke query market apps');
                        AndroidApkPlugin.queryMarketApps().then((result) {
                            this._printLog('query market callback: \n$result');
                        });
                    },
                ),

                new ListTile(
                    title: new Text('push market panel'),
                    onTap: () {
                        this._printLog('invoke push market panel');
                        AndroidApkPlugin.pushMarketAppsPanel(_key.currentContext, extra: null).then((_) {
                            this._printLog('invoke push market panel: end');
                        });
                    },
                ),

                new ListTile(
                    title: new Text('to market'),
                    onTap: () {
                        this._printLog('invoke to market');
                        AndroidApkPlugin.invokeToMarket().then((result) {
                            this._printLog('invoke to market callback: \n$result');
                        });
                    },
                ),

                new ListTile(
                    title: new Text('to market detail by sys'),
                    onTap: () {
                        this._printLog('invoke to market detail by sys');
                        AndroidApkPlugin.invokeToMarketByDetailsWithSys();
                    },
                ),

                new ListTile(
                    title: new Text('to market search by sys'),
                    onTap: () {
                        this._printLog('invoke to market search by sys');
                        AndroidApkPlugin.invokeToMarketBySearchWithSys();
                    },
                )
            ],
        );

        Widget log = new Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
                new FlatButton(
                    onPressed: () {
                        setState(() {
                            _log = '';
                        });
                    },
                    child: new Align(
                        alignment: Alignment.topRight,
                        child: new Text('clear'),
                    ),
                ),
                new Text(
                    '$_log',
                    maxLines: 10,
                    softWrap: true,
                    overflow: TextOverflow.ellipsis,
                ),
            ],
        );

        return new Stack(
            children: <Widget>[
                listView,
                new Align(
                    child: new Container(
                        // height: 200.0,
                        constraints: new BoxConstraints(
                            maxHeight: 300.0,
                            minHeight: 50.0,
                        ),
                        padding: new EdgeInsets.only(
                            left: 10.0,
                            right: 10.0,
                            bottom: 20.0,
                        ),
                        width: double.infinity,
                        color: Colors.grey[200],
                        child: log,
                    ),
                    alignment: Alignment.bottomCenter,
                ),
            ],
        );
    }
}
