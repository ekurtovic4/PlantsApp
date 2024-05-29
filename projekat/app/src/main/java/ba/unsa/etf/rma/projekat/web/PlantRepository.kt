package ba.unsa.etf.rma.projekat.web

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PlantRepository {
    suspend fun getPlantImage(
        latin: String
    ) : GetImageResponse?{
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getPlantImage(latin)
            return@withContext response.body()
        }
    }

    suspend fun getPlantId(
        latin: String
    ) : GetPlantIdResponse?{
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getPlantId(latin)
            return@withContext response.body()
        }
    }

    suspend fun getPlantData(
        id: Int
    ) : GetPlantDataResponse?{
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getPlantData(id)
            return@withContext response.body()
        }
    }

    suspend fun getPlantsByFlowerColor(
        color: String,
        substr: String
    ) : GetByFlowerColorResponse?{
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getPlantsByFlowerColor(color, substr)
            return@withContext response.body()
        }
    }
}