package land.map.repository

import land.map.common.NetworkResult
import land.map.feature.domain.model.PointListDto

class FakeGetPointListUseCase(private val fakeMapRepository: FakeMapRepository) {
   suspend fun invoke(): NetworkResult<PointListDto> {
       return fakeMapRepository.getPointList()
    }
}