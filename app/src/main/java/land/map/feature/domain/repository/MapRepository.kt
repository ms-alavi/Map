package land.map.feature.domain.repository

import kotlinx.coroutines.flow.Flow
import land.map.common.NetworkResult
import land.map.feature.domain.model.PointListDto

interface MapRepository {
    suspend fun getPointList(): Flow<NetworkResult<PointListDto>>
}