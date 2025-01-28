package com.example.a1_045.ui.page.tugas.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a1_045.R
import com.example.a1_045.model.Tugas
import com.example.a1_045.ui.costumewidget.CustomTopAppBar
import com.example.a1_045.ui.navigation.DestinasiNavigasi
import com.example.a1_045.ui.page.proyek.viewmodel.PenyediaViewModel
import com.example.a1_045.ui.page.tugas.viewmodel.HomeTugasViewModel
import com.example.a1_045.ui.page.tugas.viewmodel.HomeUiState


object DestinasiHomeTugas : DestinasiNavigasi {
    override val route = "HomeTugas"
    override val titleRes = "Home Tugas"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTugasScreen(
    navigateToItemEntry: () -> Unit,
    navigateToEdit: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onDetailClick:(String) -> Unit = {},
    viewModel: HomeTugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier =modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeTugas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTgs()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add,contentDescription = "Add Tugas")
            }
        },
    ) {innerPadding ->
        HomeStatus(
            homeUiState = viewModel.tgsUIState,
            retryAction = {viewModel.getTgs()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onEditClick = navigateToEdit,
            onDeleteClick ={
                viewModel.deleteTgs(it.idtugas )
                viewModel.getTgs()
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction:() -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit,
    onDeleteClick:(Tugas) -> Unit = {},
    onDetailClick: (String) -> Unit

){
    when (homeUiState){
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success ->
            if(homeUiState.tugas.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(),contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Tugas")
                }
            }else {
                TgsLayout(

                    tugas = homeUiState.tugas, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idtugas)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick = {
                        onEditClick(it)
                    }
                )
            }
        is HomeUiState.Error -> OnError(retryAction,modifier=modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.connectioneror), contentDescription = ""
        )
        Text(text = stringResource(id = R.string.loadingfailed),modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun TgsLayout(
    tugas: List<Tugas>,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    onDetailClick: (Tugas) -> Unit,
    onDeleteClick: (Tugas) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding =  PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tugas) { tugas ->
            TgsCard(
                tugas = tugas,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tugas) },
                onEditClick ={ onEditClick(it.idtugas)},
                onDeleteClick ={
                    onDeleteClick(tugas)
                }
            )
        }
    }
}

@Composable
fun TgsCard(
    tugas: Tugas,
    modifier: Modifier = Modifier,
    onEditClick: (Tugas) -> Unit = {},
    onDeleteClick: (Tugas) -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tugas.idtugas,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {deleteConfirmationRequired = true}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                IconButton(onClick = {onEditClick(tugas)}) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                    )
                }
                tugas.idProyek?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            tugas.idtim?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = tugas.namatugas,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = tugas.deskripsitugas,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = tugas.prioritas,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = tugas.statustugas,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick(tugas)
            },
            onDeleteCancel =  {
                deleteConfirmationRequired = false
            }, modifier = Modifier.padding(8.dp)
        )
    }
}
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDeleteCancel,
        title = {
            Text(
                "Hapus Data",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        text = {
            Text(
                "Apakah Anda yakin ingin menghapus data ini? Tindakan ini tidak dapat dibatalkan.",
                fontSize = 16.sp
            )
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Batal", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        confirmButton = {
            Button(
                onClick = onDeleteConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text("Hapus", color = Color.White)
            }
        }
    )
}