package land.map.repository


import land.map.common.BaseApiResponse
import land.map.common.NetworkResult
import land.map.feature.domain.model.PointListDto
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response


class FakeMapRepository : BaseApiResponse() {
    private var shouldReturnNetworkError = false



     private fun fakePointList(): Response<PointListDto> {
        val response = PointListDto(pointList = listOf())

         if (shouldReturnNetworkError){
            return Response.error(405, ResponseBody.create(MediaType.get("test"),""))
        }else{
            return Response.success(response)
        }
    }

     suspend fun getPointList(): NetworkResult<PointListDto> {
         val response = PointListDto(pointList = listOf())
         return safeApiCall { fakePointList() }
     }

}