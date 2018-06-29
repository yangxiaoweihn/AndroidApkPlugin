package ws.dyt.plugin.androidapk.entity

/**
 * Created by yangxiaowei
 */
class AppInfo {
    var appName: String? = null
    //bitmap base64
    var appIcon: String? = null
    //px
    var appIconWidth: Int? = 0
    //px
    var appIconHeight: Int? = 0
    var packageName: String? = null
    var versionCode: Int = 0
    var versionName: String? = null

    override fun toString(): String {
        return "AppInfo(appName=$appName, appIcon=$appIcon, appIconWidth=$appIconWidth, appIconHeight=$appIconHeight, packageName=$packageName, versionCode=$versionCode, versionName=$versionName)"
    }
}