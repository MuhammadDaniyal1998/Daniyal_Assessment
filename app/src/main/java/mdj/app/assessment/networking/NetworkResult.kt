package mdj.app.assessment.networking

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null, val tag: Int? = null) {

    class Success<T>(message: String?, data: T?, tag: Int?) : NetworkResult<T>(data, message, tag)
    class Error<T>(message: String?, data: T? = null, tag: Int? = null) : NetworkResult<T>(data, message, tag)
    class Loading<T> : NetworkResult<T>()
}