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
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ba.unsa.etf.rma.projekat.web.TrefleDAO

@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 4)
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
            ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build()
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
        suspend fun getPlantById(idBiljke: Long): List<Biljka>

        @Query("SELECT * FROM BiljkaBitmap WHERE idBiljke = :idBiljke")
        suspend fun getBitmapByIdBiljke(idBiljke: Long): List<BiljkaBitmap>

        @Insert
        suspend fun insert(bitmap: BiljkaBitmap): Long

        suspend fun addImage(idBiljke: Long, bitmap: Bitmap): Boolean{
            if(getPlantById(idBiljke).isEmpty() || getBitmapByIdBiljke(idBiljke).isNotEmpty())
                return false

            return insert(BiljkaBitmap(null, idBiljke, bitmap)) > 0
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

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS Biljka")
        db.execSQL("DROP TABLE IF EXISTS BiljkaBitmap")

        db.execSQL("""
            CREATE TABLE Biljka (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                naziv TEXT NOT NULL,
                porodica TEXT NOT NULL,
                medicinskoUpozorenje TEXT NOT NULL,
                medicinskeKoristi TEXT NOT NULL,
                profilOkusa TEXT,
                jela TEXT NOT NULL,
                klimatskiTipovi TEXT NOT NULL,
                zemljisniTipovi TEXT NOT NULL,
                onlineChecked INTEGER NOT NULL
            )
        """)

        db.execSQL("""
            CREATE TABLE BiljkaBitmap (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                idBiljke INTEGER NOT NULL,
                bitmap BLOB NOT NULL,
                FOREIGN KEY(idBiljke) REFERENCES Biljka(id) ON DELETE CASCADE
            )
        """)
    }
}

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE Biljka RENAME COLUMN porodica TO family")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS BiljkaBitmap")

        db.execSQL("""
            CREATE TABLE BiljkaBitmap (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                idBiljke INTEGER NOT NULL,
                bitmap TEXT NOT NULL,
                FOREIGN KEY(idBiljke) REFERENCES Biljka(id) ON DELETE CASCADE
            )
        """)
    }
}


