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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    onLogoutSuccess: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {

    HomeScreen(
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
    onLogoutClick: () -> Unit
) {

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
                Modifier.height(12.dp)
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

        Text(
            text =
                "Your digital access pass is ready.",
            style =
                MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier =
                Modifier.height(32.dp)
        )

        Button(
            onClick = onLogoutClick
        ) {
            Text("Sign out")
        }
    }
}