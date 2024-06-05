package ba.unsa.etf.rma.projekat.web

import com.google.gson.annotations.SerializedName

data class ShortPlantData(
    @SerializedName("common_name") val name: String?,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("family") val family: String?
)