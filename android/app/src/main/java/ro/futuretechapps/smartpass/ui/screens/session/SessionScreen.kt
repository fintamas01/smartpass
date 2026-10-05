package ro.futuretechapps.smartpass.ui.screens.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SessionRoute(
    onAuthenticated: () -> Unit,
    onUnauthenticated: () -> Unit,
    viewModel: SessionViewModel = viewModel()
) {

    val uiState by
    viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.status) {

        when (uiState.status) {

            SessionStatus.AUTHENTICATED -> {
                onAuthenticated()
            }

            SessionStatus.UNAUTHENTICATED -> {
                onUnauthenticated()
            }

            else -> Unit
        }
    }

    SessionScreen(
        uiState = uiState,
        onRetryClick =
            viewModel::checkSession
    )
}

@Composable
fun SessionScreen(
    uiState: SessionUiState,
    onRetryClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement =
            Arrangement.Center,
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        when (uiState.status) {

            SessionStatus.CHECKING -> {

                CircularProgressIndicator()

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                Text(
                    text =
                        "Checking SmartPass session..."
                )
            }

            SessionStatus.ERROR -> {

                Text(
                    text =
                        uiState.errorMessage
                            ?: "Session check failed"
                )

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                Button(
                    onClick = onRetryClick
                ) {
                    Text("Retry")
                }
            }

            else -> Unit
        }
    }
}