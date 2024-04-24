package ba.unsa.etf.rma.projekat

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

fun withImage(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description) {
        description.appendText("Drawable does not contain image with id: $id")
    }

    override fun matchesSafely(item: View): Boolean {
        val context: Context = item.context
        var bitmap: Bitmap? = context.getDrawable(id)?.toBitmap()
        if (item !is ImageView) return false
        val origBitmap = item.drawable.toBitmap()
        bitmap = bitmap!!.scale(origBitmap.width, origBitmap.height)
        return BitmapUtil.razlikaBitmapa(origBitmap, bitmap) < 0.01
    }
}