package pochita.data

import io.github.libxposed.service.XposedService
import io.github.libxposed.service.XposedServiceHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class LsposedStatus(
    val isActivated: Boolean = false,
    val frameworkName: String = "",
    val frameworkVersion: String = "",
    val apiVersion: Int = 0,
)

interface DataRepository {
    val lsposedStatus: Flow<LsposedStatus>
}

class DefaultDataRepository : DataRepository {
    private val _status = MutableStateFlow(LsposedStatus())
    override val lsposedStatus: Flow<LsposedStatus> = _status.asStateFlow()

    init {
        XposedServiceHelper.registerListener(object : XposedServiceHelper.OnServiceListener {
            override fun onServiceBind(service: XposedService) {
                _status.value = LsposedStatus(
                    isActivated = true,
                    frameworkName = service.frameworkName,
                    frameworkVersion = service.frameworkVersion,
                    apiVersion = service.apiVersion,
                )
            }

            override fun onServiceDied(service: XposedService) {
                _status.value = LsposedStatus(isActivated = false)
            }
        })
    }
}
