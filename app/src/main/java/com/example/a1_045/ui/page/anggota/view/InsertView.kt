package com.example.a1_045.ui.page.anggota.view

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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.a1_045.model.Tim
import com.example.a1_045.ui.costumewidget.CustomTopAppBar
import com.example.a1_045.ui.navigation.DestinasiNavigasi
import com.example.a1_045.ui.page.anggota.viewmodel.AnggotaUiEvent
import com.example.a1_045.ui.page.anggota.viewmodel.AnggotaUiState
import com.example.a1_045.ui.page.anggota.viewmodel.InsertAnggotaViewModel
import com.example.a1_045.ui.page.proyek.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertAnggota : DestinasiNavigasi {
    override val route = "insertanggota"
    override val titleRes = "Tambah Anggota"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertAnggotaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = "Tambah Anggota",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        FormInput(
            anggotaUiState = viewModel.anggotaUiState,
            timList = viewModel.timList,
            onAnggotaValueChange = viewModel::UpdateInsertAnggotaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.InsertAnggota()
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicPeranTextField(
    selectedValue: String,
    listPeran: List<String>,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = "Peran") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listPeran.forEach { peran ->
                DropdownMenuItem(
                    onClick = {
                        onValueChangedEvent(peran)
                        expanded = false
                    },
                    text = { Text(text = peran) }
                )
            }
        }
    }
}


@Composable
fun FormInput(
    anggotaUiState: AnggotaUiState,
    timList: List<Tim>,
    onAnggotaValueChange: (AnggotaUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Field ID Anggota
        OutlinedTextField(
            value = anggotaUiState.anggotaUiEvent.idanggota,
            onValueChange = { onAnggotaValueChange(anggotaUiState.anggotaUiEvent.copy(idanggota = it)) },
            label = { Text("ID Anggota") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        

        // Dropdown untuk Nama Tim
        DynamicDropdownTextField(
            label = "Nama Tim",
            selectedValue = timList.find { it.idtim == anggotaUiState.anggotaUiEvent.idtim }?.namatim.orEmpty(),
            listItems = timList.map { it.namatim },
            onValueChanged = { selectedNamaTim ->
                val selectedTim = timList.find { tim -> tim.namatim == selectedNamaTim }
                onAnggotaValueChange(
                    anggotaUiState.anggotaUiEvent.copy(
                        idtim = selectedTim?.idtim.orEmpty()
                    )
                )
            }
        )

        // Field Nama Anggota
        OutlinedTextField(
            value = anggotaUiState.anggotaUiEvent.namaanggota,
            onValueChange = { onAnggotaValueChange(anggotaUiState.anggotaUiEvent.copy(namaanggota = it)) },
            label = { Text("Nama Anggota") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Dropdown untuk Peran Anggota
        DynamicDropdownTextField(
            label = "Peran Anggota",
            selectedValue = anggotaUiState.anggotaUiEvent.peran,
            listItems = listOf("Pemimpin", "Anggota"),
            onValueChanged = { newPeran ->
                onAnggotaValueChange(
                    anggotaUiState.anggotaUiEvent.copy(peran = newPeran)
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
