package land.map.feature.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import land.map.feature.domain.model.PointListDto
import land.map.common.BaseApiResponse
import land.map.common.NetworkResult
import land.map.feature.domain.repository.MapRepository
import javax.inject.Inject

@ActivityRetainedScoped
class MapRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : BaseApiResponse(), MapRepository {


     override suspend fun getPointList(): Flow<NetworkResult<PointListDto>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getPointList() })
        }.flowOn(Dispatchers.IO)
    }



}