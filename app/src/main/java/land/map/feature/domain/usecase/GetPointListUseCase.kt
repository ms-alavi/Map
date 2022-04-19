package land.map.feature.domain.usecase

import kotlinx.coroutines.flow.Flow
import land.map.common.NetworkResult
import land.map.feature.data.repository.MapRepositoryImpl
import land.map.feature.domain.model.PointListDto
import javax.inject.Inject

class GetPointListUseCase  @Inject constructor(
    private val repositoryImpl: MapRepositoryImpl,
) {
    suspend fun invoke(): Flow<NetworkResult<PointListDto>>  {
     return repositoryImpl.getPointList()
    }
}