import 'package:flutter/material.dart';
import 'dart:convert';
import 'dart:typed_data';
import 'package:plugin_android_apk/plugin/market/AppInfo.dart';

/// Created by yangxiaowei at 2018/06/28
class MarketAppPanel extends StatelessWidget {

    /// NOTICE [context] should be scaffold context, otherwise no effection
    static void invokeMarketPanel(BuildContext context, List<AppInfo> apps) {

        showModalBottomSheet(
            context: context,
            builder: (context) => new MarketAppPanel(apps),
        );
    }

    final List<AppInfo> apps;
    MarketAppPanel(
        this.apps
    );

    double _pixelRatio = .0;
    @override
    Widget build(BuildContext context) {
        this._pixelRatio = this._pixelRatio == 0 ? MediaQuery.of(context).devicePixelRatio : this._pixelRatio;

        Widget con = Container(
            padding: new EdgeInsets.only(
                top: 20.0,
            ),
            // decoration: new BoxDecoration(
            //     borderRadius: new BorderRadius.only(
            //         topLeft: new Radius.circular(20.0),
            //         topRight: new Radius.circular(20.0),
            //     ),
            //     // color: Colors.red
            // ),
            // color: Colors.red,
            child: this._grid(),
        );

        // return new ClipRRect(
        //     borderRadius: new BorderRadius.only(
        //         topLeft: new Radius.circular(20.0),
        //         topRight: new Radius.circular(20.0),
        //     ),
        //     child: new Wrap(
        //         children: <Widget>[
        //             con
        //         ],
        //     ),
        // );

        return new Wrap(
        
            children: <Widget>[
                con
            ],
        );
    }

    Widget _grid() {

        Widget _itemWidget(AppInfo app) {
            var bytes = base64.decode(app.appIcon);
            Widget itemWidget = Column(
                children: <Widget>[
                    new FadeInImage(
                        width: (app.appIconWidth?.toDouble() ?? 64.0 * _pixelRatio) / _pixelRatio,
                        height: (app.appIconHeight?.toDouble() ?? 64.0 * _pixelRatio) / _pixelRatio,
                        placeholder: new MemoryImage(new Uint8List(0)),
                        image: new MemoryImage(bytes),
                    ),
                    new SizedBox(
                        height: 5.0,
                    ),
                    new Text('${app.appName}'),
                ],
                mainAxisSize: MainAxisSize.min,
            );

            return itemWidget;
        }


        return new GridView.builder(
            itemBuilder: (context, index) {

                return _itemWidget(apps[index]);
            },
            gridDelegate: new SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3
            ),
            itemCount: apps.length,
            shrinkWrap: true,
        );
    }
}