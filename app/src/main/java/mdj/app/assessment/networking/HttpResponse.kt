package mdj.app.assessment.networking

import mdj.app.assessment.models.PetResponse

class HttpResponse(
    var responseData: String,
) {

    companion object {

        fun getResponse(response: NetworkResult<PetResponse>): PetResponse? {
            return try {
                return response.data // get response
            } catch (e: Exception) {
                null
            }
        }
    }
}