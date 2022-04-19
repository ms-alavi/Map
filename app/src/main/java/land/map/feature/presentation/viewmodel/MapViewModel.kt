package land.map.feature.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import land.map.feature.domain.model.PointListDto
import land.map.common.NetworkResult
import land.map.feature.domain.usecase.GetPointListUseCase
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val getPointListUseCase: GetPointListUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val _responseCar: MutableLiveData<NetworkResult<PointListDto>> = MutableLiveData()
    val responseCar: LiveData<NetworkResult<PointListDto>> = _responseCar


    @InternalCoroutinesApi
    fun fetchCarResponse() = viewModelScope.launch {
        getPointListUseCase.invoke().collect {
            _responseCar.value = it
        }
    }


}



