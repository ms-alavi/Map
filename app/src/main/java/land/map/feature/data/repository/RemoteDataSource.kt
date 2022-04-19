package land.map.feature.data.repository

import land.map.feature.data.remote.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getPointList() =
        apiService.getPointList()
}