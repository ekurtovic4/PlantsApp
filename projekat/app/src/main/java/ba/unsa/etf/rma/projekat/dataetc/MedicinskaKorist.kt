package ba.unsa.etf.rma.projekat.dataetc

import android.os.Parcel
import android.os.Parcelable
import java.util.Locale

enum class MedicinskaKorist(val opis: String?) : Parcelable{
    SMIRENJE("Smirenje - za smirenje i relaksaciju"),
    PROTUUPALNO("Protuupalno - za smanjenje upale"),
    PROTIVBOLOVA("Protivbolova - za smanjenje bolova"),
    REGULACIJAPRITISKA("Regulacija pritiska - za regulaciju visokog/niskog pritiska"),
    REGULACIJAPROBAVE("Regulacija probave"),
    PODRSKAIMUNITETU("Podrška imunitetu");

    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MedicinskaKorist> {
        override fun createFromParcel(source: Parcel): MedicinskaKorist {
            return valueOf(source.readString()!!)
        }

        override fun newArray(size: Int): Array<MedicinskaKorist?> {
            return arrayOfNulls(size)
        }

        fun fromString(value: String): MedicinskaKorist? {
            return entries.find { it.opis == value }
        }

        fun valFromString(value: String): MedicinskaKorist? {
            return entries.find { it.toString() == value.uppercase() }
        }
    }
}