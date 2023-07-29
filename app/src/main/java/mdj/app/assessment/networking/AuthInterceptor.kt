package mdj.app.assessment.networking

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
        request.addHeader("Accept", "application/json")

        /*if (LoggedInUserDB(application).getInstance()?.isLoggedIn()) {
            request.addHeader(
                "Authorization",
                "Bearer ${LoggedInUserDB(application).getInstance()?.getUserFromLocalDb()?.token}"
            )
            Log.i(TAG, "token: ${LoggedInUserDB(application).getInstance()?.getUserFromLocalDb()?.token}")
        }*/ // this app don't need bearer token (Authorization)

        return chain.proceed(request.build())
    }
}