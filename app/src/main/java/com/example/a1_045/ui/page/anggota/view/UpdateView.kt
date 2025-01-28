package com.example.a1_045.ui.page.anggota.view

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
import com.example.a1_045.ui.page.anggota.viewmodel.UpdateAnggotaViewModel
import com.example.a1_045.ui.page.proyek.viewmodel.PenyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateAnggota: DestinasiNavigasi {
    override val route = "updateanggota"
    override val titleRes = "Update Agt"
    const val IDAnggota = "idanggota"
    val routesWithArg = "$route/{$IDAnggota}"

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAnggotaScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdateAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateAnggota.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        com.example.a1_045.ui.page.anggota.view.FormInput(
            anggotaUiState = viewModel.updateUiState,
            timList = viewModel.timList,
            onAnggotaValueChange = viewModel::updateInsertAnggotaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateAgt()
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