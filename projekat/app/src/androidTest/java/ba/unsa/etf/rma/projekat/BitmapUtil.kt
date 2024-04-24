package ba.unsa.etf.rma.projekat

import android.graphics.Bitmap

class BitmapUtil {
    companion object {
        fun pixelDiff(rgb1: Int, rgb2: Int): Int {
            val r1 = rgb1 shr 16 and 0xff
            val r2 = rgb2 shr 16 and 0xff
            return Math.abs(r1 - r2)
        }

        fun razlikaBitmapa(b1: Bitmap, b2: Bitmap): Float {
            val pikseli1 = IntArray(b1.width * b1.height)
            val pikseli2 = IntArray(b2.width * b2.height)
            b1.getPixels(pikseli1, 0, b1.width, 0, 0, b1.width, b1.height)
            b2.getPixels(pikseli2, 0, b2.width, 0, 0, b2.width, b2.height)
            if (pikseli1.size != pikseli2.size) return 1.0f
            var razlika: Long = 0
            for (i in pikseli1.indices) {
                razlika += pixelDiff(pikseli1[i], pikseli2[i]).toLong()
            }
            return razlika.toFloat() / (255 * pikseli1.size).toFloat()
        }
    }
}