package ba.unsa.etf.rma.projekat.dataetc

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Biljka(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "naziv") val naziv: String,
    @ColumnInfo(name = "family") val porodica: String,
    @ColumnInfo(name = "medicinskoUpozorenje") val medicinskoUpozorenje: String,
    @ColumnInfo(name = "medicinskeKoristi") val medicinskeKoristi: List<MedicinskaKorist>,
    @ColumnInfo(name = "profilOkusa") val profilOkusa: ProfilOkusaBiljke?,
    @ColumnInfo(name = "jela") val jela: List<String>,
    @ColumnInfo(name = "klimatskiTipovi") val klimatskiTipovi: List<KlimatskiTip>,
    @ColumnInfo(name = "zemljisniTipovi") val zemljisniTipovi: List<Zemljiste>,
    @ColumnInfo(name = "onlineChecked") val onlineChecked: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createTypedArrayList(MedicinskaKorist) as List<MedicinskaKorist>,
        parcel.readParcelable(ProfilOkusaBiljke::class.java.classLoader),
        parcel.createStringArrayList() as List<String>,
        parcel.createTypedArrayList(KlimatskiTip) as List<KlimatskiTip>,
        parcel.createTypedArrayList(Zemljiste) as List<Zemljiste>,
        parcel.readBoolean()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id ?: -1L)
        dest.writeString(naziv)
        dest.writeString(porodica)
        dest.writeString(medicinskoUpozorenje)
        dest.writeTypedList(medicinskeKoristi)
        dest.writeParcelable(profilOkusa, flags)
        dest.writeStringList(jela)
        dest.writeTypedList(klimatskiTipovi)
        dest.writeTypedList(zemljisniTipovi)
        dest.writeBoolean(onlineChecked)
    }

    companion object CREATOR : Parcelable.Creator<Biljka> {
        override fun createFromParcel(parcel: Parcel): Biljka {
            return Biljka(
                parcel.readLong().takeIf { it > 0 },
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.createTypedArrayList(MedicinskaKorist) ?: ArrayList(),
                parcel.readParcelable(ProfilOkusaBiljke::class.java.classLoader),
                parcel.createStringArrayList() ?: ArrayList(),
                parcel.createTypedArrayList(KlimatskiTip) ?: ArrayList(),
                parcel.createTypedArrayList(Zemljiste) ?: ArrayList(),
                parcel.readBoolean()
            )
        }

        override fun newArray(size: Int): Array<Biljka?> {
            return arrayOfNulls(size)
        }
    }

    fun getLatinName(): String{
        val startIndex = naziv.indexOf('(')
        val endIndex = naziv.indexOf(')')
        return naziv.substring(startIndex + 1, endIndex)
    }

    fun isEqualToFixed(fixedBiljka: Biljka): Boolean{
        return porodica == fixedBiljka.porodica
                && medicinskoUpozorenje == fixedBiljka.medicinskoUpozorenje
                && jela == fixedBiljka.jela
                && klimatskiTipovi == fixedBiljka.klimatskiTipovi
                && zemljisniTipovi == fixedBiljka.zemljisniTipovi
    }

    fun isEqualWithoutId(biljka: Biljka): Boolean{
        return naziv == biljka.naziv
                && porodica == biljka.porodica
                && medicinskoUpozorenje == biljka.medicinskoUpozorenje
                && medicinskeKoristi == biljka.medicinskeKoristi
                && profilOkusa == biljka.profilOkusa
                && jela == biljka.jela
                && klimatskiTipovi == biljka.klimatskiTipovi
                && zemljisniTipovi == biljka.zemljisniTipovi
                && onlineChecked == biljka.onlineChecked
    }
}
