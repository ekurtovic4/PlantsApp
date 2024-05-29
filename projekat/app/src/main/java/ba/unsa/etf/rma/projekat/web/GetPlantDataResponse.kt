package ba.unsa.etf.rma.projekat.web

import com.google.gson.annotations.SerializedName

data class GetPlantDataResponse(
    @SerializedName("data") val data: PlantData
)