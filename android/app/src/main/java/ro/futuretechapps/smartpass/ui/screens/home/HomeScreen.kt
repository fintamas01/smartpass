package ro.futuretechapps.smartpass.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    onLogoutSuccess: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {

    val uiState by
    viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onRefreshNfcClick =
            viewModel::refreshNfcStatus,
        onLogoutClick = {
            viewModel.logout(
                onLogoutComplete =
                    onLogoutSuccess
            )
        }
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRefreshNfcClick: () -> Unit,
    onLogoutClick: () -> Unit
) {

    val nfc =
        uiState.nfcCapabilities

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement =
            Arrangement.Center,
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text = "SmartPass",
            style =
                MaterialTheme.typography.displaySmall
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Text(
            text = "Mobile Access Control",
            style =
                MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier =
                Modifier.height(32.dp)
        )

        if (nfc != null) {

            StatusRow(
                label = "NFC supported",
                available =
                    nfc.isNfcSupported
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            StatusRow(
                label = "NFC enabled",
                available =
                    nfc.isNfcEnabled
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            StatusRow(
                label = "Card emulation",
                available =
                    nfc.isHceSupported
            )

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )

            Text(
                text =
                    if (nfc.isSmartPassReady) {
                        "Your device is ready for SmartPass."
                    } else {
                        "SmartPass NFC is not ready."
                    },
                style =
                    MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        Button(
            onClick = onRefreshNfcClick
        ) {
            Text("Refresh NFC status")
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        Button(
            onClick = onLogoutClick,
            enabled =
                !uiState.isLoggingOut
        ) {

            Text(
                text =
                    if (uiState.isLoggingOut) {
                        "Signing out..."
                    } else {
                        "Sign out"
                    }
            )
        }
    }
}

@Composable
private fun StatusRow(
    label: String,
    available: Boolean
) {

    Text(
        text =
            "$label    ${
                if (available) {
                    "✓"
                } else {
                    "✕"
                }
            }",
        style =
            MaterialTheme.typography.bodyLarge
    )
}