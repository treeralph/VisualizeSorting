package com.example.visualizesorting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.visualizesorting.ui.theme.VisualizeSortingTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VisualizeSortingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainComposable(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainComposable(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItem = remember { mutableStateOf(viewModel.algorithmList[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(80.dp)
                                .padding(12.dp),
                            imageVector = Icons.Filled.Architecture,
                            contentDescription = ""
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }

                viewModel.algorithmList.forEach { algorithm ->
                    NavigationDrawerItem(
                        label = { Text(text = algorithm) },
                        selected = algorithm == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = algorithm
                            viewModel.onAlgorithmClickListener(algorithm)
                        }
                    )
                }
            }
        },
    ) {
        Scaffold(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.primary,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = viewModel.onPlayButtonClickListener
                ) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "")
                }
            },
            topBar = {
                MainTopAppBar(
                    title = viewModel.algorithmName.observeAsState(initial = "").value,
                    menuButtonClickListener = {
                        scope.launch { drawerState.open() }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(BOX_CONTAINER_HEIGHT.dp)
                        .padding(16.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    viewModel.array.forEach { element ->
                        key(element.value) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height((BOX_HEIGHT_COEFFICIENT * element.value).dp)
                                    .background(
                                        color = if (element.colorSwitch) {
                                            MaterialTheme.colorScheme.primaryContainer
                                        } else MaterialTheme.colorScheme.error
                                    )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(60.dp))
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    title: String = "SAMPLE TITLE",
    menuButtonClickListener: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = menuButtonClickListener) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
@Preview(showBackground = true)
fun TopAppBarPreview() {
    VisualizeSortingTheme {
        MainTopAppBar()
    }
}