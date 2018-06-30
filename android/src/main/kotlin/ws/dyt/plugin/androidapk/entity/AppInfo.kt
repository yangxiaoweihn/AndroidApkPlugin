package ws.dyt.plugin.androidapk.entity

/**
 * Created by yangxiaowei
 */
data class AppInfo(
    var appName: String? = null,
    //bitmap base64
    var appIcon: String? = null,
    //px
    var appIconWidth: Int? = 0,
    //px
    var appIconHeight: Int? = 0,
    var packageName: String? = null,
    var versionCode: Int = 0,
    var versionName: String? = null
)