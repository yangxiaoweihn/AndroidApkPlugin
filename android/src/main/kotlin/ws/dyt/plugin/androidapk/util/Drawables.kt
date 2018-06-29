package ws.dyt.plugin.androidapk.util

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.NinePatchDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream
import android.util.Base64
import java.io.IOException


/**
 * Created by yangxiaowei at 2018/06/28
 */
object Drawables {
    fun drawable2Bitmap(drawable: Drawable): Bitmap? {

        bitmapTest()
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        if (drawable is NinePatchDrawable) {

            val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565

            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, config)

            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            drawable.draw(canvas)
            return bitmap
        }

        return null
    }

    fun Bitmap2Bytes(bm: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.WEBP, 100, baos)
        return baos.toByteArray()
    }

    fun Bytes2Bimap(b: ByteArray): Bitmap? {
        return if (b.isNotEmpty()) {
            BitmapFactory.decodeByteArray(b, 0, b.size)
        } else {
            null
        }
    }

    fun bitmap2Drawable(resources: Resources, bitmap: Bitmap): Drawable {
        return BitmapDrawable(resources, bitmap)
    }

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

    fun base64ToBitmap(base64Data: String): Bitmap {
        val bytes = Base64.decode(base64Data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun bitmapTest() {
        val dd = Bitmap.createBitmap( 1, 1, Bitmap.Config.ALPHA_8)
        dd.eraseColor(Color.TRANSPARENT)
    }
}