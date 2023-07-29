package mdj.app.assessment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mdj.app.assessment.models.PetResponse
import mdj.app.assessment.networking.NetworkResult
import mdj.app.assessment.networking.Wrapper
import mdj.app.assessment.repository.PetRepository
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(private val petRepository: PetRepository) : ViewModel() {

    val liveData : LiveData<Wrapper<NetworkResult<PetResponse>>>
        get() = petRepository.publicLiveData

    fun getPets(key: String, type: String, image_type: String, pretty: String, tag: Int) {
        viewModelScope.launch {
            petRepository.getPets(key, type, image_type, pretty, tag)
        }
    }
}