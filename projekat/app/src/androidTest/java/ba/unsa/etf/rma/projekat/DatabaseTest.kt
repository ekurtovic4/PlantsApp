package ba.unsa.etf.rma.projekat

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.unsa.etf.rma.projekat.dataetc.Biljka
import ba.unsa.etf.rma.projekat.dataetc.BiljkaDatabase
import ba.unsa.etf.rma.projekat.dataetc.KlimatskiTip
import ba.unsa.etf.rma.projekat.dataetc.MedicinskaKorist
import ba.unsa.etf.rma.projekat.dataetc.ProfilOkusaBiljke
import ba.unsa.etf.rma.projekat.dataetc.Zemljiste
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var db: BiljkaDatabase
    private lateinit var biljkaDao: BiljkaDatabase.BiljkaDAO

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BiljkaDatabase::class.java).build()
        biljkaDao = db.biljkaDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun saveBiljkaTest() = runBlocking{
        val biljka = Biljka(
            naziv = "Bosiljak (Ocimum basilicum)",
            porodica = "Lamiaceae (usnate)",
            medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
            jela = listOf("Salata od paradajza", "Punjene tikvice"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
        )
        biljkaDao.saveBiljka(biljka)
        val plants = biljkaDao.getAllBiljkas()

        assert(plants.any { it.isEqualWithoutId(biljka) })
    }

    @Test
    @Throws(Exception::class)
    fun clearDataTest() = runBlocking(){
        biljkaDao.clearData()
        val plants = biljkaDao.getAllBiljkas()

        assert(plants.isEmpty())
    }
}