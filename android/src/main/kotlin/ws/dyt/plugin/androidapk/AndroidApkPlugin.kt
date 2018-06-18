package ws.dyt.plugin.androidapk

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.os.Build
import android.support.v4.content.FileProvider
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import java.io.File


/**
 * Created by yangxiaowei at 2018/06/12
 */
class AndroidApkPlugin(private val registrar: Registrar) : MethodChannel.MethodCallHandler {

    companion object {
        @JvmStatic
        private val CHANNEL = "ws.dyt.plugin/android_apk";
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), CHANNEL)
            channel.setMethodCallHandler(AndroidApkPlugin(registrar))
        }
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "installApk") {

            val path: String? = if (call.hasArgument("path")) call.argument("path") else null
            if (TextUtils.isEmpty(path)) {
                result.error("error", "Apk path is null", null)
                return
            }
            val authority: String? = if (call.hasArgument("authority")) call.argument("authority") else null
            try {

                result.success("Android start install")
                this.startInstall(registrar.context(), path!!, authority)
            } catch (e: Exception) {
                e.printStackTrace()
                result.error("exception", e.message, null)
            }

        } else {
            result.notImplemented()
        }
    }

    private fun startInstall(context: Context, path: String, authority: String?) {

        try {

            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            var uri: Uri? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !TextUtils.isEmpty(authority)) {
                uri = FileProvider.getUriForFile(context, authority!!, File(path))
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                uri = Uri.fromFile(File(path))
            }
            intent.setDataAndType(uri!!, "application/vnd.android.package-archive")
            context.startActivity(intent)
        } catch (e: Exception) {

            throw e
        }

    }
}
