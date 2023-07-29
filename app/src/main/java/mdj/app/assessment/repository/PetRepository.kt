package mdj.app.assessment.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mdj.app.assessment.AppClass.Companion.application
import mdj.app.assessment.R
import mdj.app.assessment.models.PetResponse
import mdj.app.assessment.networking.Apis
import mdj.app.assessment.networking.NetworkResult
import mdj.app.assessment.networking.Wrapper
import mdj.app.assessment.utils.Constants.TAG_RESPONSE
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class PetRepository @Inject constructor(private val apis: Apis) {

    private val liveData = MutableLiveData<Wrapper<NetworkResult<PetResponse>>>()
    val publicLiveData: LiveData<Wrapper<NetworkResult<PetResponse>>> get() = liveData

    suspend fun getPets(key: String, type: String, image_type: String, pretty: String, tag: Int) {

        // show loader
        liveData.postValue(Wrapper(NetworkResult.Loading()))

        // get response
        val response = apis.getPets(key, type, image_type, pretty)

        // print response
        Log.i(TAG_RESPONSE, "getPets: ${response.body().toString()}")

        // response handling
        handleResponse(response, tag)
    }

    private fun handleResponse(response: Response<PetResponse>, tag: Int) {
        if (response.isSuccessful && response.body() != null) {
            liveData.postValue(Wrapper(NetworkResult.Success("", response.body()!!, tag)))
        } else if (response.errorBody() != null) {
            liveData.postValue(Wrapper(NetworkResult.Error(response.code().toString(), null, tag)))
        } else {
            liveData.postValue(
                Wrapper(
                    NetworkResult.Error(
                        application.getString(R.string.error_something_went_wrong),
                        null,
                        tag
                    )
                )
            )
        }
    }
}