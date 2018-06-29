package ws.dyt.plugin.androidapk

import android.text.TextUtils
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import org.json.JSONArray
import org.json.JSONObject
import ws.dyt.plugin.androidapk.log.LLog
import ws.dyt.plugin.androidapk.util.Apps
import ws.dyt.plugin.androidapk.util.Install
import ws.dyt.plugin.androidapk.util.Market
import java.util.*


/**
 * Created by yangxiaowei at 2018/06/12
 *
 * installApk ->
 *  Params:
 *      {
 *          "path": String,
 *          "authority": String,
 *      }
 *
 * toMarket ->
 *  Params:
 *      {
 *          "marketAppPackage": String,
 *      }
 *
 * queryMarketApp ->
 *  Params:
 *      {
 *          "marketAppPackages": List<String>
 *      }
 *  Response:
 *      List<AppInfo>
 *      [
 *          {
 *              "appName": String,
 *              "appIcon": String,//base64
 *              "appIconWidth": int,
 *              "appIconHeight": int,
 *              "packageName": String,
 *              "versionCode": Integer,
 *              "versionName": String
 *          }
 *      ]
 */
class AndroidApkPlugin(private val registrar: Registrar) : MethodChannel.MethodCallHandler {

    object Tag{
        const val TAG: String = "AndroidApkPlugin"
    }

    companion object {
        @JvmStatic
        private val CHANNEL = "ws.dyt.plugin/android_apk"
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), CHANNEL)
            channel.setMethodCallHandler(AndroidApkPlugin(registrar))
        }
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        val method = call.method
        val debugKey = "debug"
        val debug = call.hasArgument(debugKey) && call.argument(debugKey)
        LLog.setDebug(debug)
        LLog.d(Tag.TAG, "${call.arguments}")

        if (method == "installApk") {

            this.handleInstallApk(call, result)
        } else if(method == "queryMarketApp") {

            this.handleQueryMarketApp(call, result)
        } else if(method == "toMarket") {

            this.handleToMarket(call, result)
        } else {

            result.notImplemented()
        }
    }

    private fun handleInstallApk(call: MethodCall, result: Result) {
        var paramKey = "path"
        val path: String? = if (call.hasArgument(paramKey)) call.argument(paramKey) else null

        LLog.d(Tag.TAG, "apkPath: $path")
        if (TextUtils.isEmpty(path)) {
            result.error("error", "Apk path is null", null)
            return
        }

        paramKey = "authority"
        val authority: String? = if (call.hasArgument(paramKey)) call.argument(paramKey) else null
        LLog.d(Tag.TAG, "authority: $authority")
        try {

            result.success("Android start install")
            Install.startInstall(registrar.context(), path!!, authority)
        } catch (e: Exception) {
            e.printStackTrace()
            result.error("exception", e.message, null)
        }
    }

    private fun handleToMarket(call: MethodCall, result: Result) {

        var paramKey = "marketAppPackage"
        val marketAppPackage: String? = if (call.hasArgument(paramKey)) call.argument(paramKey) else null
        LLog.d(Tag.TAG, "marketAppPackage: $marketAppPackage")

        if (null == marketAppPackage || marketAppPackage.isEmpty()) {

            result.error("error", "nee param: $marketAppPackage", null)
        }else {

            result.success("ok")
            val context = registrar.context()
            Market.launchMarketAppToDetail(context, Apps.queryCurrentAppProcessName(context), marketAppPackage!!)
        }
    }

    private fun handleQueryMarketApp(call: MethodCall, result: Result) {

        val paramKey = "marketAppPackages"
        val inPackageParam: List<String>? = if (call.hasArgument(paramKey)) call.argument(paramKey) else null

        val filter = ArrayList<String>()
        if (null != inPackageParam) {
            filter.addAll(inPackageParam)
        }
        filter.addAll(Apps.queryMarketApps(registrar.context()))
        filter.addAll(Apps.markets)

        val apps = Apps.queryInstalledMarketAppsInfo(registrar.context(), filter)

        if (!apps.isEmpty()) {

            val data = JSONArray()
            apps.map {
                val obj = JSONObject()
                obj.put("appName", it.appName)
                obj.put("appIcon", it.appIcon)
                obj.put("appIconWidth", it.appIconWidth)
                obj.put("appIconHeight", it.appIconHeight)
                obj.put("packageName", it.packageName)
                obj.put("versionCode", it.versionCode)
                obj.put("versionName", it.versionName)

            }.toList<JSONObject>().forEach {
                data.put(it)
            }

            result.success(data.toString())
        }else{
            result.error("error", "market app not found", null)
        }
    }
}
