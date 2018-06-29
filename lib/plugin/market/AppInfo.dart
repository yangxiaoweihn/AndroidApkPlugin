import 'dart:convert';

/// Created by yangxiaowei
class AppInfo {

    String appName;
    //bitmap base64
    String appIcon;
    //px
    int appIconWidth;
    //px
    int appIconHeight;
    String packageName;
    int versionCode;
    String versionName;

    AppInfo.fromMap(Map map) {
        this.appName = map['appName'];
        this.appIcon = map['appIcon'];
        this.appIconWidth = map['appIconWidth'];
        this.appIconHeight = map['appIconHeight'];
        this.packageName = map['packageName'];
        this.versionCode = map['versionCode'];
        this.versionName = map['versionName'];
    }

    static List<AppInfo> transfer(String gson) {
        List<dynamic> list = json.decode(gson);

        return list.map((item) {
            return new AppInfo.fromMap(item);
        }).toList();
    }
}