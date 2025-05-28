package com.sagar.sampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sagar.sampleapp.data.db.MovieEntity
import com.sagar.sampleapp.ui.theme.SampleAppTheme
import com.sagar.sampleapp.ui.vm.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SampleAppTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen(modifier: Modifier = Modifier) {
    val viewModel: MoviesViewModel = hiltViewModel()
    val isExpanded by viewModel.isExpanded.collectAsStateWithLifecycle()
    val moviesList by viewModel.moviesList.collectAsStateWithLifecycle()
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .safeDrawingPadding()
        ) {
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    viewModel.onUiAction(UiAction.IsExpandedChanged)
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text("All movies")
                    },
                    onClick = {
                        viewModel.onUiAction(UiAction.OnFilterChange(MovieEntity.WATCH_STATUS_UNWATCHED))
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("Watchlist")
                    },
                    onClick = {
                        viewModel.onUiAction(UiAction.OnFilterChange(MovieEntity.WATCH_STATUS_WATCHLIST))
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("Watched movies")
                    },
                    onClick = {
                        viewModel.onUiAction(UiAction.OnFilterChange(MovieEntity.WATCH_STATUS_WATCHED))
                    }
                )
            }

            LazyColumn {
                items(
                    items = moviesList,
                    key = {
                        it.id
                    }
                ) {
                    Text(text = it.movieName)
                }
            }
        }
    }
}


sealed interface UiAction {
    data object IsExpandedChanged : UiAction
    data class OnFilterChange(val watchedStatus: Int) : UiAction
}