package pochita.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import pochita.data.LsposedStatus
import pochita.theme.PochitaTheme

@Composable
fun MainScreen(
    onItemClick: (NavKey) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = viewModel(),
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    MainScreenContent(status = status, modifier = modifier)
}

@Composable
internal fun MainScreenContent(status: LsposedStatus, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LsposedStatusCard(status = status)
    }
}

@Composable
fun LsposedStatusCard(status: LsposedStatus, modifier: Modifier = Modifier) {
    val containerColor = if (status.isActivated) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.errorContainer
    }
    val contentColor = if (status.isActivated) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onErrorContainer
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (status.isActivated) "● 已激活" else "○ 未激活",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
            Text(
                text = if (status.isActivated) {
                    "${status.frameworkName} ${status.frameworkVersion} (API ${status.apiVersion})"
                } else {
                    "请在 LSPosed 管理器中启用模块并勾选作用域"
                },
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenActivatedPreview() {
    PochitaTheme {
        MainScreenContent(
            status = LsposedStatus(
                isActivated = true,
                frameworkName = "LSPosed",
                frameworkVersion = "1.9.3",
                apiVersion = 102,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenInactivatedPreview() {
    PochitaTheme {
        MainScreenContent(status = LsposedStatus(isActivated = false))
    }
}
