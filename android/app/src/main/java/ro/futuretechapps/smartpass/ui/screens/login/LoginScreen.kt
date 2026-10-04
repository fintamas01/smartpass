package ro.futuretechapps.smartpass.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import android.util.Patterns

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var emailError by remember {
        mutableStateOf<String?>(null)
    }

    var passwordError by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "SmartPass",
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Sign in to continue",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it

                if (emailError != null) {
                    emailError = null
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Email")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = emailError != null,
            supportingText = {
                emailError?.let {
                    Text(it)
                }
            }
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it

                if (passwordError != null) {
                    passwordError = null
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Password")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = passwordError != null,
            supportingText = {
                passwordError?.let {
                    Text(it)
                }
            },
            trailingIcon = {
                TextButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Text(
                        text = if (passwordVisible) {
                            "Hide"
                        } else {
                            "Show"
                        }
                    )
                }
            }
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {

                val trimmedEmail = email.trim()

                emailError = when {
                    trimmedEmail.isEmpty() -> {
                        "Email is required"
                    }

                    !Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches() -> {
                        "Enter a valid email address"
                    }

                    else -> null
                }

                passwordError = when {
                    password.isEmpty() -> {
                        "Password is required"
                    }

                    password.length < 8 -> {
                        "Password must be at least 8 characters"
                    }

                    else -> null
                }

                if (emailError == null && passwordError == null) {
                    onLoginClick()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign in")
        }
    }
}