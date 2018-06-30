package ws.dyt.plugin.androidapk.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils

/**
 * Created by yangxiaowei at 2018/06/28
 */
class Market {

    companion object {
        @JvmStatic
        fun launchMarketAppByDetail(context: Context, appPackage: String, marketAppPackage: String?) {
            if (TextUtils.isEmpty(appPackage)) {
                return
            }
            try {
                val uri: Uri = Uri.parse("market://details?id=$appPackage")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                if (!TextUtils.isEmpty(marketAppPackage)) {
                    intent.`package` = marketAppPackage
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @JvmStatic
        fun launchMarketAppBySearch(context: Context, appPackage: String) {
            if (TextUtils.isEmpty(appPackage)) {
                return
            }
            try {
                val uri: Uri = Uri.parse("market://search?q=$appPackage")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}