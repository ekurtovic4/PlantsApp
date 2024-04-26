package ba.unsa.etf.rma.projekat

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAssertion
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertTrue

//preuzeto iz laboratorijske vjezbe 4

fun hasItemCount(n: Int) = ViewAssertion { view, noViewFoundException ->
    if (noViewFoundException != null) {
        throw noViewFoundException
    }
    assertTrue("View nije tipa RecyclerView", view is RecyclerView)
    val rv: RecyclerView = view as RecyclerView
    assertThat(
        "GetItemCount RecyclerView broj elementa: ",
        rv.adapter?.itemCount,
        `is`(n)
    )
}