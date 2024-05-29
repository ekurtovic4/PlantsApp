package ba.unsa.etf.rma.projekat.web

import com.google.gson.annotations.SerializedName

data class GetByFlowerColorResponse(
    @SerializedName("data") val data: List<ShortPlantData>
)