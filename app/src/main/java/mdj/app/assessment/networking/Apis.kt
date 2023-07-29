package mdj.app.assessment.networking

import mdj.app.assessment.models.PetResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Apis {

    @GET("api/")
    suspend fun getPets(
        @Query("key") key: String, @Query("q") type: String,
        @Query("image_type") image_type: String,
        @Query("pretty") pretty: String
    ): Response<PetResponse>
}