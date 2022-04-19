package land.map.feature.domain.model

import com.google.gson.annotations.SerializedName


data class PointListDto(
    @SerializedName("poiList")
    val pointList: List<CarLocationDto>,
)
data class CarLocationDto(
    @SerializedName("coordinate")
    val coordinateDto: CoordinateDto,
    @SerializedName("fleetType")
    val fleetType: String,
    @SerializedName("heading")
    val heading: Double,
    @SerializedName("id")
    val id: Int
)
data class CoordinateDto(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)




