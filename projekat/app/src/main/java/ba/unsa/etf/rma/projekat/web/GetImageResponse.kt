package ba.unsa.etf.rma.projekat.web

import com.google.gson.annotations.SerializedName

data class GetImageResponse (
    @SerializedName("data") val data: List<PlantImage>
){
    data class PlantImage (
        @SerializedName("image_url") var link: String?
    )
}