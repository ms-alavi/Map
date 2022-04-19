package land.map.feature.presentation.ui.fragmentes

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.InternalCoroutinesApi
import land.map.amir.R
import land.map.common.NetworkResult
import land.map.common.showLocationOfCar
import land.map.feature.presentation.viewmodel.MapViewModel

class ShowAllCarOnMapFragment : Fragment(), OnMapReadyCallback {
    private val mapViewModel: MapViewModel by hiltNavGraphViewModels(R.id.nav_graph_xml)
    private val zoomLevel = 12.0f //This goes up to 21
    private lateinit var mMap: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_all_car_on_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fetchData()

    }
    @OptIn(InternalCoroutinesApi::class)
    private fun fetchData() {
        mapViewModel.fetchCarResponse()

        mapViewModel.responseCar.observe(this) { res ->
            when (res) {
                is NetworkResult.Success -> {
                    res.data?.pointList?.forEach {

                            val simplePoint =
                                LatLng(it.coordinateDto.latitude, it.coordinateDto.longitude)

                            context?.let { it1 ->
                                showLocationOfCar(it, simplePoint,mMap,
                                    it1,zoomLevel)

                        }
                    }
                }
                is NetworkResult.Error -> {
                    // show error message
                }
                is NetworkResult.Loading -> {
                    // show a progress bar
                }

            }
        }
    }

}