package org.example.bnb.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    // koinViewModel() vai usar o 'factory' que definimos no LoginModule
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Efeito para navegar para a próxima tela quando o login for bem-sucedido.
    // A lógica de navegação real dependeria da sua biblioteca de navegação.
    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            // navigator.navigateToHomeScreen()
            println("Login bem-sucedido!")
        }
    }

    // Efeito para mostrar um Snackbar ou Toast em caso de erro.
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.error) {
        if (state.error != null) {
            snackbarHostState.showSnackbar(
                message = state.error!!,
                duration = SnackbarDuration.Short
            )
            viewModel.onEvent(LoginEvent.DismissError) // Limpa o erro após mostrar
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Login", style = MaterialTheme.typography.headlineLarge)

                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { viewModel.onEvent(LoginEvent.OnEmailChange(it)) },
                        label = { Text("E-mail") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { viewModel.onEvent(LoginEvent.OnPasswordChange(it)) },
                        label = { Text("Senha") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = { viewModel.onEvent(LoginEvent.OnLoginClick) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isLoading
                    ) {
                        Text("Entrar")
                    }
                }
            }
        }
    }
}