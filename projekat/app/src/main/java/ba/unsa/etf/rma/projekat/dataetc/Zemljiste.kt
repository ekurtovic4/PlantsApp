package ba.unsa.etf.rma.projekat.dataetc

import android.os.Parcel
import android.os.Parcelable

enum class Zemljiste(val naziv: String?) : Parcelable {
    PJESKOVITO("Pjeskovito zemljište"),
    GLINENO("Glineno zemljište"),
    ILOVACA("Ilovača"),
    CRNICA("Crnica"),
    SLJUNOVITO("Šljunovito zemljište"),
    KRECNJACKO("Krečnjačko zemljište");

    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Zemljiste> {
        override fun createFromParcel(source: Parcel): Zemljiste {
            return valueOf(source.readString()!!)
        }

        override fun newArray(size: Int): Array<Zemljiste?> {
            return arrayOfNulls(size)
        }

        fun fromString(value: String): Zemljiste? {
            return entries.find { it.naziv == value }
        }

        fun valFromString(value: String): Zemljiste? {
            return entries.find { it.toString() == value.uppercase() }
        }
    }
}