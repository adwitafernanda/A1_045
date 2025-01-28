package com.example.a1_045.ui.page.team.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a1_045.ui.costumewidget.CustomTopAppBar
import com.example.a1_045.ui.navigation.DestinasiNavigasi
import com.example.a1_045.ui.page.proyek.view.EntryBody
import com.example.a1_045.ui.page.proyek.viewmodel.PenyediaViewModel
import com.example.a1_045.ui.page.team.viewmodel.UpdateTimViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DestinasiUpdateTim: DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Tm"
    const val IDTim = "idtim"
    val routesWithArg = "$route/{$IDTim}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTimScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdateTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateTim.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updateUiState,
            onProyekValueChange = viewModel::updateInsertTmState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTm()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            }
        )
    }
}