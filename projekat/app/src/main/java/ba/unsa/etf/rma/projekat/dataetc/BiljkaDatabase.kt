package ba.unsa.etf.rma.projekat.dataetc

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Update
import ba.unsa.etf.rma.projekat.web.TrefleDAO

@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 1)
@TypeConverters(Converters::class)
abstract class BiljkaDatabase : RoomDatabase() {
    abstract fun biljkaDao(): BiljkaDAO
    companion object {
        private var INSTANCE: BiljkaDatabase? = null
        fun getInstance(context: Context): BiljkaDatabase {
            if (INSTANCE == null) {
                synchronized(BiljkaDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BiljkaDatabase::class.java,
                "biljke-db"
            ).build()
    }

    @Dao
    interface BiljkaDAO{
        //saveBiljka
        @Insert
        suspend fun insert(biljka: Biljka): Long

        suspend fun saveBiljka(biljka: Biljka): Boolean{
            return insert(biljka) > 0
        }

        //fixOfflineBiljka
        @Query("SELECT * FROM Biljka WHERE onlineChecked = false")
        suspend fun getUnchecked(): List<Biljka>

        @Update
        suspend fun updateAll(biljke: List<Biljka>): Int

        suspend fun fixOfflineBiljka(): Int{
            val biljke = getUnchecked()
            val biljkeToFix = mutableListOf<Biljka>()
            val trefleDAO = TrefleDAO()

            for(biljka in biljke){
                val fixedBiljka = trefleDAO.fixData(biljka)
                if(!biljka.isEqualToFixed(fixedBiljka))
                    biljkeToFix.add(fixedBiljka)
            }

            return updateAll(biljkeToFix)
        }

        //addImage
        @Query("SELECT * FROM Biljka WHERE id = :idBiljke")
        suspend fun getPlantById(idBiljke: Int): List<Biljka>

        @Query("SELECT * FROM BiljkaBitmap WHERE idBiljke = :idBiljke")
        suspend fun getBitmapById(idBiljke: Int): List<BiljkaBitmap>

        @Insert
        suspend fun insert(bitmap: BiljkaBitmap): Long

        suspend fun addImage(idBiljke: Int, bitmap: Bitmap): Boolean{
            if(getPlantById(idBiljke).isEmpty() || getBitmapById(idBiljke).isNotEmpty())
                return false

            return insert(BiljkaBitmap(idBiljke, bitmap)) > 0
        }

        //getAllBiljkas
        @Query("SELECT * FROM Biljka")
        suspend fun getAllBiljkas(): List<Biljka>

        //clearData
        @Query("DELETE FROM Biljka")
        suspend fun deleteAllPlants()

        @Query("DELETE FROM BiljkaBitmap")
        suspend fun deleteAllBitmaps()

        suspend fun clearData(){
            deleteAllPlants()
            deleteAllBitmaps()
        }

        //insertAll
        @Insert
        suspend fun insertAll(biljke: List<Biljka>)
    }
}