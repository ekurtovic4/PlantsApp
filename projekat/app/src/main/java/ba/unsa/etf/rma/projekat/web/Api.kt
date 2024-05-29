package ba.unsa.etf.rma.projekat.web

import ba.unsa.etf.rma.projekat.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("plants/search")
    suspend fun getPlantImage(
        @Query("q") latin: String,
        @Query("token") token: String = BuildConfig.ACCESS_TOKEN
    ): Response<GetImageResponse>

    @GET("plants/search")
    suspend fun getPlantId(
        @Query("q") latin: String,
        @Query("token") token: String = BuildConfig.ACCESS_TOKEN
    ): Response<GetPlantIdResponse>

    @GET("species/{id}")
    suspend fun getPlantData(
        @Path("id") id: Int,
        @Query("token") token: String = BuildConfig.ACCESS_TOKEN
    ): Response<GetPlantDataResponse>

    @GET("plants/search")
    suspend fun getPlantsByFlowerColor(
        @Query("filter[flower_color]") color: String,
        @Query("q") substring: String,
        @Query("token") token: String = BuildConfig.ACCESS_TOKEN
    ): Response<GetByFlowerColorResponse>
}