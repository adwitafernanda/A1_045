package com.example.a1_045.ui.page.tugas.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.a1_045.ui.page.proyek.viewmodel.PenyediaViewModel
import com.example.a1_045.ui.page.tugas.viewmodel.UpdateTugasViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DestinasiUpdateTugas: DestinasiNavigasi {
    override val route = "updatetugas"
    override val titleRes = "Update Tgs"
    const val IDTugas = "idtugas"
    val routesWithArg = "$route/{$IDTugas}"

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTugasScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdateTugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateTugas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        FormInput(
            tugasUiState = viewModel.updateUiState,
            proyekList = viewModel.proyekList,
            timList = viewModel.timList,
            onTugasValueChange = viewModel::updateInsertTugasState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTgs()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            },
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}