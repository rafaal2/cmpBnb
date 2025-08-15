package org.example.bnb.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel

// --- Função Principal da Tela ---
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    // (Opcional) Callback para quando o botão "Criar conta" for clicado
    onCreateAccountClick: () -> Unit = {},
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            onLoginSuccess()
        }
    }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            snackbarHostState.showSnackbar(
                message = state.error!!,
                duration = SnackbarDuration.Short
            )
            viewModel.onEvent(LoginEvent.DismissError)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LoginScreenContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = viewModel::onEvent,
            onCreateAccountClick = onCreateAccountClick
        )
    }
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onCreateAccountClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Colocamos o formulário dentro de uma Column rolável para evitar que
        // o teclado esconda os campos em telas pequenas.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título e Subtítulo
            Text(
                text = "Bem-vindo de Volta!",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Faça login para continuar",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            LoginTextField(
                value = state.email,
                onValueChange = { onEvent(LoginEvent.OnEmailChange(it)) },
                labelText = "E-mail",
                leadingIcon = Icons.Default.Person,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                isError = state.emailError != null,
                errorMessage = state.emailError
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoginTextField(
                value = state.password,
                onValueChange = { onEvent(LoginEvent.OnPasswordChange(it)) },
                labelText = "Senha",
                leadingIcon = Icons.Default.Lock,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onEvent(LoginEvent.OnLoginClick)
                    }
                ),
                isPassword = true,
                isError = state.passwordError != null,
                errorMessage = state.passwordError
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botão de Login Principal
            Button(
                onClick = { onEvent(LoginEvent.OnLoginClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
//                enabled = state.isLoginButtonEnabled
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Entrar", fontSize = 16.sp)
                }
            }

            // Botão "Criar conta"
            TextButton(
                onClick = onCreateAccountClick,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Não tem uma conta? Crie uma")
            }
        }
    }
}

// --- Composable Reutilizável para os Campos de Texto ---
@Composable
private fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    leadingIcon: ImageVector,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    isError: Boolean,
    errorMessage: String?,
    isPassword: Boolean = false
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(labelText) },
            leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true,
            isError = isError
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

// --- Preview  ---
//@Preview(showBackground = true)
//@Composable
//private fun LoginScreenPreview() {
//    BnbTheme {
//        LoginScreenContent(
//            state = LoginState(
//                isLoginButtonEnabled = true,
//                emailError = "E-mail inválido"
//            ),
//            onEvent = {},
//            onCreateAccountClick = {}
//        )
//    }
//}