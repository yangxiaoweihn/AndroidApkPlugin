package ws.dyt.plugin.androidapk.util

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.text.TextUtils
import android.util.Log
import ws.dyt.plugin.androidapk.entity.AppInfo
import java.lang.StringBuilder
import java.util.*
import android.app.ActivityManager


/**
 * Created by yangxiaowei
 */
object Apps {

    private const val TAG: String = "Apps"

    val markets = listOf(
            "com.xiaomi.market",
            "com.qihoo.appstore",
            "com.wandoujia.phoenix2",
            "com.tencent.android.qqdownloader",
            "com.taptap"
    )

    /**
     * search apps of support market
     */
    fun queryMarketAppsOnlyByFlag(context: Context): List<String> {

        val intent = Intent()
        intent.action = Intent.ACTION_MAIN
        intent.addCategory("android.intent.category.APP_MARKET")
        val pm = context.packageManager
        val info = pm.queryIntentActivities(intent, 0)
        if (info == null || info.size == 0) {
            return Collections.emptyList()
        }

        return info.map {
            it.activityInfo.packageName
        }.filter {
            !TextUtils.isEmpty(it)
        }.toList()
    }

    /**
     * query all installed apps
     */
    private fun queryAllInstalledApps(context: Context): List<PackageInfo>{

        return context.packageManager.getInstalledPackages(0)
    }

    /**
     * query installed market info from specify package
     * @param context
     * @param filter specify package collection
     * @return had installed market info
     */
    fun queryInstalledMarketAppsInfo(context: Context, filter: List<String>): List<AppInfo> {
        if (filter.isEmpty()) {
            return Collections.emptyList<AppInfo>()
        }

        var filter = filter.toSet().toList()
        val pm = context.packageManager
        val installed: List<PackageInfo> = queryAllInstalledApps(context)

        var queryAppInfo = ArrayList<AppInfo>()
        for (checkPkg in filter) {
            for (packageInfo in installed) {
                var installPkg = packageInfo.packageName
                if (TextUtils.isEmpty(installPkg)) {
                    continue
                }
                if (installPkg == checkPkg) {
                    // 如果非系统应用，则添加至appList,这个会过滤掉系统的应用商店，如果不需要过滤就不用这个判断

                    if (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                        //将应用相关信息缓存起来，用于自定义弹出应用列表信息相关用
                        val appInfo = AppInfo()
                        appInfo.appName = packageInfo.applicationInfo.loadLabel(pm).toString()
                        appInfo.packageName = packageInfo.packageName
                        appInfo.versionCode = packageInfo.versionCode
                        appInfo.versionName = packageInfo.versionName

                        val icon = packageInfo.applicationInfo.loadIcon(pm)
                        appInfo.appIcon = Drawables.bitmapToBase64(Drawables.drawable2Bitmap(icon))
                        appInfo.appIconWidth = icon?.intrinsicWidth
                        appInfo.appIconHeight = icon?.intrinsicHeight

                        queryAppInfo.add(appInfo)
                    }
                    break
                }
            }
        }

        return queryAppInfo
    }

    fun queryCurrentAppProcessName(context: Context): String {
        val pid = android.os.Process.myPid()
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var processName = ""
        for (info in manager.runningAppProcesses) {
            if (info.pid == pid) {
                processName = info.processName
                break
            }
        }
        return processName
    }

    private fun printPackage(context: Context, packages: List<PackageInfo>) {

        if (packages.isEmpty()) {
            return
        }

        var sb = StringBuilder()
        for (p in packages) {

            sb.setLength(0)
            sb.append("\n appName: ${p.applicationInfo.loadLabel(context.packageManager)}")
            sb.append(" - packageName: ${p.packageName}")
            sb.append(" - versionName: ${p.versionName}")
            sb.append(" - versionCode: ${p.versionCode}")
            Log.d(TAG, sb.toString())
        }
        sb.setLength(0)
    }
}