package ba.unsa.etf.rma.projekat.dataetc

import android.os.Parcel
import android.os.Parcelable

enum class KlimatskiTip(val opis: String?) : Parcelable {
    SREDOZEMNA("Mediteranska klima - suha, topla ljeta i blage zime"),
    TROPSKA("Tropska klima - topla i vlažna tokom cijele godine"),
    SUBTROPSKA("Subtropska klima - blage zime i topla do vruća ljeta"),
    UMJERENA("Umjerena klima - topla ljeta i hladne zime"),
    SUHA("Sušna klima - niske padavine i visoke temperature tokom cijele godine"),
    PLANINSKA("Planinska klima - hladne temperature i kratke sezone rasta");

    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KlimatskiTip> {
        override fun createFromParcel(source: Parcel): KlimatskiTip {
            return valueOf(source.readString()!!)
        }

        override fun newArray(size: Int): Array<KlimatskiTip?> {
            return arrayOfNulls(size)
        }

        fun fromString(value: String): KlimatskiTip? {
            return entries.find { it.opis == value }
        }

        fun valFromString(value: String): KlimatskiTip? {
            return entries.find { it.toString() == value.uppercase() }
        }
    }
}