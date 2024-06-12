package ba.unsa.etf.rma.projekat.dataetc

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converters {
    @TypeConverter
    fun fromMedicinskaKoristListToString(list: List<MedicinskaKorist>): String{
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun fromStringToMedicinskaKoristList(value: String): List<MedicinskaKorist>{
        return value.split(",").mapNotNull { MedicinskaKorist.valFromString(it.trim()) }
    }

    @TypeConverter
    fun fromKlimatskiTipListToString(list: List<KlimatskiTip>): String{
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun fromStringToKlimatskiTipList(value: String): List<KlimatskiTip>{
        return value.split(",").mapNotNull { KlimatskiTip.valFromString(it.trim()) }
    }

    @TypeConverter
    fun fromZemljisteListToString(list: List<Zemljiste>): String{
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun fromStringToZemljisteList(value: String): List<Zemljiste>{
        return value.split(",").mapNotNull { Zemljiste.valFromString(it.trim()) }
    }

    @TypeConverter
    fun fromListToString(list: List<String>): String{
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun fromStringToList(value: String): List<String>{
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    @TypeConverter
    fun toBitmap(encodedString: String): Bitmap {
        val byteArray = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}