package pochita.ui.main

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import pochita.data.DataRepository
import pochita.data.LsposedStatus

class MainScreenViewModelTest {
  @Test
  fun status_initialState() = runTest {
    val viewModel = MainScreenViewModel(FakeDataRepository())
    assertEquals(false, viewModel.status.first().isActivated)
  }

  @Test
  fun status_activatedState() = runTest {
    val fakeRepo = FakeDataRepository(LsposedStatus(isActivated = true, frameworkName = "LSPosed", apiVersion = 102))
    val viewModel = MainScreenViewModel(fakeRepo)
    val state = viewModel.status.filter { it.isActivated }.first()
    assertEquals(true, state.isActivated)
    assertEquals("LSPosed", state.frameworkName)
    assertEquals(102, state.apiVersion)
  }
}

private class FakeDataRepository(
  private val initialStatus: LsposedStatus = LsposedStatus(),
) : DataRepository {
  override val lsposedStatus: Flow<LsposedStatus> = flowOf(initialStatus)
}
