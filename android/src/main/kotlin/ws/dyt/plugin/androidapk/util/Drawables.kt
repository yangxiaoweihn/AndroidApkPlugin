package ws.dyt.plugin.androidapk.util

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream
import android.util.Base64
import ws.dyt.plugin.androidapk.log.LLog
import java.io.IOException


/**
 * Created by yangxiaowei at 2018/06/28
 */
class Drawables {
    companion object {
        @JvmStatic
        fun drawable2Bitmap(drawable: Drawable): Bitmap? {

            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }

            val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565

            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, config)

            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            drawable.draw(canvas)
            return bitmap
        }

        @JvmStatic
        fun bitmap2Bytes(bm: Bitmap): ByteArray {
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.WEBP, 100, baos)
            return baos.toByteArray()
        }

        @JvmStatic
        fun bytes2Bimap(b: ByteArray): Bitmap? {
            return if (b.isNotEmpty()) {
                BitmapFactory.decodeByteArray(b, 0, b.size)
            } else {
                null
            }
        }

        @JvmStatic
        fun bitmap2Drawable(resources: Resources, bitmap: Bitmap): Drawable {
            return BitmapDrawable(resources, bitmap)
        }

        @JvmStatic
        fun bitmapToBase64(bitmap: Bitmap?): String? {

            var result: String? = null
            var baos: ByteArrayOutputStream? = null
            try {
                if (bitmap != null) {
                    baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos)

                    baos.flush()
                    baos.close()

                    val bitmapBytes = baos.toByteArray()
                    result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    if (baos != null) {
                        baos.flush()
                        baos.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return result
        }

        @JvmStatic
        fun base64ToBitmap(base64Data: String): Bitmap {
            val bytes = Base64.decode(base64Data, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        @JvmStatic
        private fun bitmapTest() {
            val dd = Bitmap.createBitmap( 1, 1, Bitmap.Config.ARGB_4444)
            dd.eraseColor(Color.parseColor("#01FFFFFF"))
            LLog.d("::", ""+bitmapToBase64(dd))
        }
    }
}