package ba.unsa.etf.rma.projekat.web

import com.google.gson.annotations.SerializedName

data class GetPlantIdResponse (
    @SerializedName("data") val data: List<PlantId>
){
    data class PlantId (
        @SerializedName("id") var id: Int
    )
}