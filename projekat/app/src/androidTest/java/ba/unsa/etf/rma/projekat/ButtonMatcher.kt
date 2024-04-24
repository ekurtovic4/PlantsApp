package ba.unsa.etf.rma.projekat

import android.view.View
import android.widget.Button
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class ButtonMatcher(private val errorText: String) : BoundedMatcher<View, Button>(Button::class.java){
    override fun describeTo(description: Description?) {
        description?.appendText("has text error")
    }

    override fun matchesSafely(item: Button?): Boolean {
        return item?.error == errorText
    }

}