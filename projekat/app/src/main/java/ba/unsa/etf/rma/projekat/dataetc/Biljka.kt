package ba.unsa.etf.rma.projekat.dataetc

import android.os.Parcel
import android.os.Parcelable

data class Biljka(
    val naziv: String,
    val porodica: String,
    val medicinskoUpozorenje: String,
    val medicinskeKoristi: List<MedicinskaKorist>,
    val profilOkusa: ProfilOkusaBiljke?,
    val jela: List<String>,
    val klimatskiTipovi: List<KlimatskiTip>,
    val zemljisniTipovi: List<Zemljiste>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createTypedArrayList(MedicinskaKorist) as List<MedicinskaKorist>,
        parcel.readParcelable(ProfilOkusaBiljke::class.java.classLoader),
        parcel.createStringArrayList() as List<String>,
        parcel.createTypedArrayList(KlimatskiTip) as List<KlimatskiTip>,
        parcel.createTypedArrayList(Zemljiste) as List<Zemljiste>
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(naziv)
        dest.writeString(porodica)
        dest.writeString(medicinskoUpozorenje)
        dest.writeTypedList(medicinskeKoristi)
        dest.writeParcelable(profilOkusa, flags)
        dest.writeStringList(jela)
        dest.writeTypedList(klimatskiTipovi)
        dest.writeTypedList(zemljisniTipovi)
    }

    companion object CREATOR : Parcelable.Creator<Biljka> {
        override fun createFromParcel(parcel: Parcel): Biljka {
            return Biljka(parcel)
        }

        override fun newArray(size: Int): Array<Biljka?> {
            return arrayOfNulls(size)
        }
    }
}
