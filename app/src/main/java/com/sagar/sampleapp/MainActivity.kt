package com.sagar.sampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sagar.sampleapp.data.db.MovieEntity
import com.sagar.sampleapp.ui.theme.SampleAppTheme
import com.sagar.sampleapp.ui.vm.MoviesViewModel
import com.sagar.sampleapp.ui.vm.UiState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SampleAppTheme {
                val viewModel: MoviesViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                AppScreen(state = state, onUiAction = viewModel::onUiAction)
            }
        }
    }
}

@Composable
fun AppScreen(state: UiState, onUiAction: (UiAction) -> Unit) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .safeDrawingPadding()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable {
                            onUiAction(UiAction.IsExpandedChanged)
                        },
                    text = state.watchFilter.toWatchFilterText()
                )

                DropdownMenu(
                    expanded = state.isExpanded,
                    onDismissRequest = {
                        onUiAction(UiAction.IsExpandedChanged)
                    }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text("All movies")
                        },
                        onClick = {
                            onUiAction(UiAction.OnFilterChange(MovieEntity.WATCH_STATUS_UNWATCHED))
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Watchlist")
                        },
                        onClick = {
                            onUiAction(UiAction.OnFilterChange(MovieEntity.WATCH_STATUS_WATCHLIST))
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Watched movies")
                        },
                        onClick = {
                            onUiAction(UiAction.OnFilterChange(MovieEntity.WATCH_STATUS_WATCHED))
                        }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = state.moviesList,
                    key = {
                        it.id
                    }
                ) {
                    ListItem(
                        headlineContent = {
                            Text(text = it.movieName)
                        },
                        leadingContent = {
                            Button(
                                onClick = {
                                    onUiAction(
                                        when (it.watchStatus) {
                                            MovieEntity.WATCH_STATUS_UNWATCHED -> UiAction.MoveToWatchList(it)
                                            MovieEntity.WATCH_STATUS_WATCHLIST -> UiAction.MoveToWatched(it)
                                            MovieEntity.WATCH_STATUS_WATCHED -> UiAction.MoveToWatchList(it)
                                            else -> throw IllegalArgumentException("Unknown watch status")
                                        }
                                    )
                                }
                            ) {
                                Text(
                                    text = when (it.watchStatus) {
                                        MovieEntity.WATCH_STATUS_UNWATCHED -> "Add to watchlist"
                                        MovieEntity.WATCH_STATUS_WATCHLIST -> "Mark as watched"
                                        MovieEntity.WATCH_STATUS_WATCHED -> "Move to watchlist"
                                        else -> "Unknown action"
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}


sealed interface UiAction {
    data object IsExpandedChanged : UiAction
    data class OnFilterChange(val watchedStatus: Int) : UiAction
    data class MoveToWatchList(val movie: MovieEntity) : UiAction
    data class MoveToWatched(val movie: MovieEntity) : UiAction
}

fun Int.toWatchFilterText(): String {
    return when (this) {
        MovieEntity.WATCH_STATUS_UNWATCHED -> "All movies"
        MovieEntity.WATCH_STATUS_WATCHLIST -> "Watchlist"
        MovieEntity.WATCH_STATUS_WATCHED -> "Watched movies"
        else -> "Unknown filter"
    }
}