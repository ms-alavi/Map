package land.map.feature.data.remote

import land.map.feature.domain.model.PointListDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("taxi.json")
    suspend fun getPointList(): Response<PointListDto>
}