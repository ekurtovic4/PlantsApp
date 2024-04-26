package ba.unsa.etf.rma.projekat

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import ba.unsa.etf.rma.projekat.activities.MainActivity
import ba.unsa.etf.rma.projekat.activities.NovaBiljkaActivity
import org.hamcrest.Matchers.hasToString
import org.junit.Test

class InstrumentedTestsS2 {
    @Test
    fun testETValidacija(){
        val pokreniNovuBiljku = Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniNovuBiljku)

        onView(withId(R.id.nazivET)).perform(typeText("x"))
        onView(withId(R.id.porodicaET)).perform(typeText("porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("abcdefghijklmnopqrstuvwxyz"))

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())

        onView(withId(R.id.nazivET)).perform(scrollTo())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Dužina mora biti između 2 i 20 karaktera")))
        onView(withId(R.id.nazivET)).perform(replaceText("naziv"))

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())

        onView(withId(R.id.medicinskoUpozorenjeET)).perform(scrollTo())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Dužina mora biti između 2 i 20 karaktera")))
    }

    @Test
    fun testETIstovremenaValidacija(){
        val pokreniNovuBiljku = Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniNovuBiljku)

        onView(withId(R.id.nazivET)).perform(typeText(""))
        onView(withId(R.id.porodicaET)).perform(typeText("abcdefghijklmnopqrstuvwxyz"))

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())

        onView(withId(R.id.nazivET)).perform(scrollTo())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Dužina mora biti između 2 i 20 karaktera")))
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Dužina mora biti između 2 i 20 karaktera")))
    }

    @Test
    fun testDodavanjeJelaValidacija(){
        val pokreniNovuBiljku = Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniNovuBiljku)

        onView(withId(R.id.jeloET)).perform(typeText("jelo"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        
        onView(withId(R.id.jeloET)).perform(scrollTo())
        onView(withId(R.id.jeloET)).perform(typeText("jelo"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())

        onView(withId(R.id.jeloET)).perform(scrollTo())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Jelo već postoji")))
    }

    @Test
    fun testDodavanjeJelaETValidacija(){
        val pokreniNovuBiljku = Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniNovuBiljku)

        onView(withId(R.id.jeloET)).perform(typeText("x"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())

        onView(withId(R.id.jeloET)).perform(scrollTo())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Dužina mora biti između 2 i 20 karaktera")))
    }

    @Test
    fun testIzmjenaJelaValidacija(){
        val pokreniNovuBiljku = Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniNovuBiljku)

        onView(withId(R.id.jeloET)).perform(typeText("jelo"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())

        onView(withId(R.id.jeloET)).perform(scrollTo())
        onView(withId(R.id.jeloET)).perform(typeText("jellouu"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())

        onData(hasToString("jelo")).inAdapterView(withId(R.id.jelaLV)).perform(click())
        onView(withId(R.id.jeloET)).perform(scrollTo())
        onView(withId(R.id.jeloET)).perform(replaceText("JellouU"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())

        onView(withId(R.id.jeloET)).perform(scrollTo())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Jelo već postoji")))
    }

    @Test
    fun odabirUListiValidacija(){
        val pokreniNovuBiljku = Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniNovuBiljku)

        onView(withId(R.id.nazivET)).perform(typeText("naziv"))
        onView(withId(R.id.porodicaET)).perform(typeText("porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("upozorenje"))
        onView(withId(R.id.jeloET)).perform(typeText("jelo"))

        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).check(matches(ButtonMatcher("Nije selektovan item u listi/ama")))
    }

    @Test
    fun odabirUListiProfilOkusaValidacija(){
        val pokreniNovuBiljku = Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniNovuBiljku)

        onView(withId(R.id.nazivET)).perform(typeText("naziv"))
        onView(withId(R.id.porodicaET)).perform(typeText("porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("upozorenje"))
        onView(withId(R.id.jeloET)).perform(typeText("jelo"))

        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo())
        onData(hasToString("Regulacija probave")).inAdapterView(withId(R.id.medicinskaKoristLV)).perform(click())
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo())
        onData(hasToString("Mediteranska klima - suha, topla ljeta i blage zime")).inAdapterView(withId(R.id.klimatskiTipLV)).perform(click())
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo())
        onData(hasToString("Crnica")).inAdapterView(withId(R.id.zemljisniTipLV)).perform(click())

        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).check(matches(ButtonMatcher("Nije selektovan item u listi/ama")))
    }

    @Test
    fun listaJelaValidacija(){
        val pokreniNovuBiljku = Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniNovuBiljku)

        onView(withId(R.id.nazivET)).perform(typeText("naziv"))
        onView(withId(R.id.porodicaET)).perform(typeText("porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("upozorenje"))

        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo())
        onData(hasToString("Regulacija probave")).inAdapterView(withId(R.id.medicinskaKoristLV)).perform(click())
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo())
        onData(hasToString("Mediteranska klima - suha, topla ljeta i blage zime")).inAdapterView(withId(R.id.klimatskiTipLV)).perform(click())
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo())
        onData(hasToString("Crnica")).inAdapterView(withId(R.id.zemljisniTipLV)).perform(click())
        onView(withId(R.id.profilOkusaLV)).perform(scrollTo())
        onData(hasToString("Sladak okus")).inAdapterView(withId(R.id.profilOkusaLV)).perform(click())

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Nije dodano nijedno jelo u listu")))
    }

    @Test
    fun testDodavanjeBiljke(){
        val pokreniMain = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        launchActivity<MainActivity>(pokreniMain)

        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        onView(withId(R.id.nazivET)).perform(typeText("naziv"))
        onView(withId(R.id.porodicaET)).perform(typeText("porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("upozorenje"))
        onView(withId(R.id.jeloET)).perform(typeText("jelo"))

        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo())
        onData(hasToString("Regulacija probave")).inAdapterView(withId(R.id.medicinskaKoristLV)).perform(click())
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo())
        onData(hasToString("Mediteranska klima - suha, topla ljeta i blage zime")).inAdapterView(withId(R.id.klimatskiTipLV)).perform(click())
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo())
        onData(hasToString("Crnica")).inAdapterView(withId(R.id.zemljisniTipLV)).perform(click())
        onView(withId(R.id.profilOkusaLV)).perform(scrollTo())
        onData(hasToString("Sladak okus")).inAdapterView(withId(R.id.profilOkusaLV)).perform(click())

        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())

        onView(withId(R.id.biljkeRV)).check(hasItemCount(13))
    }

    @Test
    fun testUslikajBiljku(){
        val pokreniNovuBiljku = Intent(ApplicationProvider.getApplicationContext(),NovaBiljkaActivity::class.java)
        launchActivity<NovaBiljkaActivity>(pokreniNovuBiljku)

        Intents.init()
        val bundle = Bundle()
        bundle.putParcelable("data", BitmapFactory.decodeResource(ApplicationProvider.getApplicationContext<Context>().resources, R.drawable.test))
        val result = Intent()
        result.putExtras(bundle)
        val imgResult = Instrumentation.ActivityResult(Activity.RESULT_OK, result)
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(imgResult)

        onView(withId(R.id.uslikajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.uslikajBiljkuBtn)).perform(click())
        onView(withId(R.id.slikaIV)).check(matches(withImage(R.drawable.test)))
        Intents.release()
    }
}
