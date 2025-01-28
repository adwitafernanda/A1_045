package com.example.a1_045.ui.page.tugas.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a1_045.model.Proyek
import com.example.a1_045.model.Tim
import com.example.a1_045.ui.costumewidget.CustomTopAppBar
import com.example.a1_045.ui.navigation.DestinasiNavigasi
import com.example.a1_045.ui.page.proyek.viewmodel.PenyediaViewModel
import com.example.a1_045.ui.page.tugas.viewmodel.InsertTugasViewModel
import com.example.a1_045.ui.page.tugas.viewmodel.TugasUiEvent
import com.example.a1_045.ui.page.tugas.viewmodel.TugasUiState
import kotlinx.coroutines.launch

object DestinasiInsertTugas : DestinasiNavigasi {
    override val route = "inserttugas"
    override val titleRes = "Tambah Tugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertTugasScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = "Tambah Tugas",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        FormInput(
            tugasUiState = viewModel.tugasUiState,
            proyekList = viewModel.proyekList,
            timList = viewModel.timList,
            onTugasValueChange = viewModel::UpdateInsertTugasState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.InsertTugas()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}


@Composable
fun FormInput(
    tugasUiState: TugasUiState,
    proyekList: List<Proyek>,
    timList: List<Tim>,
    onTugasValueChange: (TugasUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Field ID Tugas
        OutlinedTextField(
            value = tugasUiState.tugasUiEvent.idtugas,
            onValueChange = { onTugasValueChange(tugasUiState.tugasUiEvent.copy(idtugas = it)) },
            label = { Text("ID Tugas") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Dropdown untuk Proyek
        DynamicDropdownTextField(
            label = "Proyek",
            selectedValue = proyekList.find { it.idProyek == tugasUiState.tugasUiEvent.idProyek }?.namaproyek.orEmpty(),
            listItems = proyekList.map { it.namaproyek },
            onValueChanged = { selectedNamaProyek ->
                val selectedProyek = proyekList.find { proyek -> proyek.namaproyek == selectedNamaProyek }
                onTugasValueChange(
                    tugasUiState.tugasUiEvent.copy(
                        idProyek = selectedProyek?.idProyek.orEmpty(),

                    )
                )
                Log.d("idProyekSelected","$selectedProyek")
            }
        )

        // Dropdown untuk Nama Tim
        DynamicDropdownTextField(
            label = "Nama Tim",
            selectedValue = timList.find { it.idtim == tugasUiState.tugasUiEvent.idtim }?.namatim.orEmpty(),
            listItems = timList.map { it.namatim },
            onValueChanged = { selectedNamaTim ->
                val selectedTim = timList.find { tim -> tim.namatim == selectedNamaTim }
                onTugasValueChange(
                    tugasUiState.tugasUiEvent.copy(
                        idtim = selectedTim?.idtim.orEmpty()
                    )
                )
            }
        )

        // Field Nama Tugas
        OutlinedTextField(
            value = tugasUiState.tugasUiEvent.namatugas,
            onValueChange = { onTugasValueChange(tugasUiState.tugasUiEvent.copy(namatugas = it)) },
            label = { Text("Nama Tugas") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Field Deskripsi Tugas
        OutlinedTextField(
            value = tugasUiState.tugasUiEvent.deskripsitugas,
            onValueChange = { onTugasValueChange(tugasUiState.tugasUiEvent.copy(deskripsitugas = it)) },
            label = { Text("Deskripsi Tugas") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Dropdown untuk Prioritas
        DynamicDropdownTextField(
            label = "Prioritas",
            selectedValue = tugasUiState.tugasUiEvent.prioritas,
            listItems = listOf("Rendah", "Sedang", "Tinggi"),
            onValueChanged = { newPrioritas ->
                onTugasValueChange(
                    tugasUiState.tugasUiEvent.copy(prioritas = newPrioritas)
                )
            }
        )

        // Dropdown untuk Status Tugas
        DynamicDropdownTextField(
            label = "Status Tugas",
            selectedValue = tugasUiState.tugasUiEvent.statustugas,
            listItems = listOf("Belum Mulai", "Sedang Berlangsung", "Selesai"),
            onValueChanged = { newStatus ->
                onTugasValueChange(
                    tugasUiState.tugasUiEvent.copy(statustugas = newStatus)
                )
            }
        )

        // Tombol Simpan
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicDropdownTextField(
    label: String,
    selectedValue: String,
    listItems: List<String>,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            listItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        expanded = false
                        onValueChanged(item)
                    }
                )
            }
        }
    }
}
