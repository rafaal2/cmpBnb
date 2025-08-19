package org.example.bnb.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.bnb.search.domain.model.SearchResult // Importe o modelo de domínio
import org.koin.compose.viewmodel.koinViewModel

// O objeto Screen da Voyager agora injeta o ViewModel
object SearchScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: SearchViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()

        SearchScreenContent(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigateBack = { navigator.pop() },
            onResultClick = { /* Lógica futura */ }
        )
    }
}

// O Composable de UI agora é "burro", apenas recebe o estado e os eventos
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onResultClick: (itemId: String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = state.searchQuery,
                        onValueChange = { onEvent(SearchEvent.OnQueryChange(it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        placeholder = { Text("Busque por acomodações...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (state.searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onEvent(SearchEvent.OnQueryChange("")) }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Limpar busca")
                                }
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        val listModifier = Modifier.fillMaxSize().padding(paddingValues)

        Box(modifier = listModifier, contentAlignment = Alignment.Center) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.error != null) {
                Text(state.error, textAlign = TextAlign.Center)
            } else if (state.searchQuery.length < 3) {
                Text("Digite pelo menos 3 caracteres para buscar.", textAlign = TextAlign.Center)
            } else if (state.searchResults.isEmpty()) {
                Text("Nenhum resultado encontrado.", textAlign = TextAlign.Center)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(state.searchResults) { result ->
                        SearchResultCard(
                            result = result,
                            onClick = { onResultClick(result.id) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

// O Card de resultado agora usa o modelo de domínio 'SearchResult'
@Composable
private fun SearchResultCard(
    result: SearchResult,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Aqui entraria a imagem (AsyncImage(model = result.imageUrl, ...))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(result.title, style = MaterialTheme.typography.titleMedium)
                Text(result.subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

// Preview para testar a UI com diferentes estados
//@Preview
//@Composable
//private fun SearchScreenPreview() {
//    BnbTheme {
//        val fakeResults = listOf(
//            SearchResult("1", "Cabana Aconchegante", "R$ 420.00 / noite", ""),
//            SearchResult("2", "Loft Moderno", "R$ 310.50 / noite", ""),
//        )
//        SearchScreenContent(
//            state = SearchState(searchQuery = "cabana", searchResults = fakeResults),
//            onEvent = {},
//            onNavigateBack = {},
//            onResultClick = {}
//        )
//    }
//}