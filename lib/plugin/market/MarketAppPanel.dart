import 'package:flutter/material.dart';
import 'dart:convert';
import 'dart:typed_data';
import 'package:plugin_android_apk/plugin/market/AppInfo.dart';

// webp 1px*1px 01ffffff ARGB_4444
const String _placeHolder = "UklGRkIAAABXRUJQVlA4WAoAAAAQAAAAAAAAAAAAQUxQSAIAAAAAAVZQOCAaAAAAMAEAnQEqAQABAAAAACWkAANwAP7/3tzwAAA=";

/// Created by yangxiaowei at 2018/06/28
class MarketAppPanel extends StatelessWidget {

    /// NOTICE [context] should be scaffold context, otherwise no effection
    static void invokeMarketPanel(BuildContext context, List<AppInfo> apps) {

        showModalBottomSheet(
            context: context,
            builder: (context) => new MarketAppPanel(apps, context: context,),
        );
    }

    final List<AppInfo> apps;
    final double _pixelRatio;
    final Uint8List _placeHolderByts;
    MarketAppPanel(
        this.apps,
        {
            BuildContext context,
        }
    ): 
        this._pixelRatio = MediaQuery.of(context).devicePixelRatio,
        this._placeHolderByts = base64.decode(_placeHolder)
    ;

    @override
    Widget build(BuildContext context) {

        Widget con = Container(
            color: Colors.white,
            // padding: new EdgeInsets.symmetric(
            //     vertical: 20.0,
            // ),
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

        Widget _cancel = new FlatButton(
            onPressed: () {
                Navigator.maybePop(context);
            },
            color: Colors.white,
            child: new SizedBox(
                width: double.infinity,
                height: 48.0,
                child: new Align(
                    alignment: Alignment.center,
                    child: new Text('取消', style: new TextStyle(
                        fontSize: 16.0,
                    ),),
                ),
            ),
        );

        return new Wrap(
            children: <Widget>[
                con,
                _cancel,
            ],
        );
    }

    Widget _grid() {

        Widget _itemWidget(AppInfo app) {
            Widget itemWidget = Column(
                children: <Widget>[
                    new FadeInImage(
                        width: (app.appIconWidth?.toDouble() ?? 64.0 * _pixelRatio) / _pixelRatio,
                        height: (app.appIconHeight?.toDouble() ?? 64.0 * _pixelRatio) / _pixelRatio,
                        placeholder: new MemoryImage(this._placeHolderByts),
                        image: new MemoryImage(base64.decode(app.appIcon)),
                    ),
                    new SizedBox(
                        height: 5.0,
                    ),
                    new Text(
                        '${app.appName}', 
                        style: new TextStyle(
                            fontSize: 14.0,
                        ),
                        maxLines: 1,
                        softWrap: true,
                        overflow: TextOverflow.ellipsis,
                    ),
                ],
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.center,
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