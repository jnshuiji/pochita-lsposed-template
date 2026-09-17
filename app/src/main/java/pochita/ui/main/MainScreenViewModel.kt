package pochita.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import pochita.data.DataRepository
import pochita.data.DefaultDataRepository
import pochita.data.LsposedStatus

class MainScreenViewModel(
    dataRepository: DataRepository = DefaultDataRepository(),
) : ViewModel() {
    val status: StateFlow<LsposedStatus> = dataRepository.lsposedStatus
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LsposedStatus(),
        )
}
