package org.example.bnb.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.GppGood
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen


// Modelo de dados temporário para as opções
private data class SettingsOption(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

// Ponto de entrada da Voyager para a tela
object ProfileScreen : Screen {
    @Composable
    override fun Content() {
        // No futuro, pegaríamos o ViewModel aqui
        ProfileScreenContent(
            userName = "Rafael",
            onOptionClick = { println("Opção '$it' clicada") }
        )
    }
}


// O Composable que contém a UI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenContent(
    userName: String,
    onOptionClick: (String) -> Unit
) {
    // Lista de opções de configuração
    val settingsOptions = listOf(
        SettingsOption("Configurações do Aplicativo", Icons.Default.Settings) { onOptionClick("App Settings") },
        SettingsOption("Configurações da Conta", Icons.Default.AccountCircle) { onOptionClick("Account Settings") },
        SettingsOption("Privacidade e Segurança", Icons.Default.GppGood) { onOptionClick("Privacy") },
        SettingsOption("Notificações", Icons.Default.Notifications) { onOptionClick("Notifications") }
    )

    val helpOptions = listOf(
        SettingsOption("Termos de Serviço", Icons.AutoMirrored.Filled.HelpOutline) { onOptionClick("Terms") },
        SettingsOption("Sair (Logout)", Icons.AutoMirrored.Filled.ExitToApp) { onOptionClick("Logout") }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
        ) {
            // Seção do Cabeçalho do Perfil
            item {
                ProfileHeader(userName = userName)
            }

            // Seção de Configurações
            item {
                SettingsSection(
                    title = "Configurações",
                    options = settingsOptions
                )
            }

            // Seção de Ajuda e Logout
            item {
                SettingsSection(
                    title = "Ajuda",
                    options = helpOptions
                )
            }
        }
    }
}

// Composable para o cabeçalho do perfil
@Composable
private fun ProfileHeader(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Ícone/Avatar do usuário
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Avatar do usuário",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            tint = MaterialTheme.colorScheme.primary
        )
        // Nome do usuário
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

// Composable para uma seção de opções
@Composable
private fun SettingsSection(
    title: String,
    options: List<SettingsOption>
) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        options.forEach { option ->
            SettingsItem(
                title = option.title,
                icon = option.icon,
                onClick = option.onClick
            )
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp)) // Linha divisória
        }
    }
}

// Composable para um item da lista de opções
@Composable
private fun SettingsItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

//@Preview
//@Composable
//private fun ProfileScreenPreview() {
//    BnbTheme {
//        ProfileScreen.Content()
//    }
//}