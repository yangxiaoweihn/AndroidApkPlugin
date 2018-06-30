package ws.dyt.plugin.androidapk.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.text.TextUtils
import java.io.File

/**
 * Craeted by yangxiaowei at 2018/06/28
 */
class Install {

    companion object {
        @JvmStatic
        fun startInstall(context: Context, path: String, authority: String?) {

            try {

                val intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                var uri: Uri?
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

}