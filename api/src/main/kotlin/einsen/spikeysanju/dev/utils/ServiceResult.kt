package einsen.spikeysanju.dev.utils

sealed class ServiceResult {
    class Success(val message: String) : ServiceResult()
    class Error(val message: String) : ServiceResult()
}
