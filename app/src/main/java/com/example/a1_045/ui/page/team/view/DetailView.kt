package com.example.a1_045.ui.page.team.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a1_045.model.Tim
import com.example.a1_045.repository.TimRepository
import com.example.a1_045.ui.costumewidget.CustomTopAppBar
import com.example.a1_045.ui.navigation.DestinasiNavigasi
import com.example.a1_045.ui.page.team.viewmodel.DetailTimViewModel
import com.example.a1_045.ui.page.team.viewmodel.DetailTimViewModelFactory


object DestinasiDetailTim : DestinasiNavigasi {
    override val route = "detailtim"
    override val titleRes = "Detail Tim"
    const val IDTim = "idtim"
    val routesWithArg = "$route/{$IDTim}"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTimScreen(idtim: String,
                    repository: TimRepository,
                    navigateBack: () -> Unit,
                    modifier: Modifier = Modifier
) {
    val viewModel: DetailTimViewModel = viewModel(factory = DetailTimViewModelFactory(repository))

    var tim by remember { mutableStateOf<Tim?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idtim) {
        viewModel.getTimbyid(idtim) { result ->
            tim = result
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailTim.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (tim != null) {
                DetailContentModern(tim = tim!!, modifier = Modifier.padding(innerPadding))
            } else {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Text("Tim tidak ditemukan")
                }
            }
        }
    }
}
@Composable
fun DetailContentModern(tim: Tim, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFE8D1FA)) // Background utama
            .padding(16.dp),

        ) {
        // Header Section
        Text(
            text = "",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
        )

        // Table Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFB771E5),
                            Color(0xFF8B5DFF)
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Field",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = Color.White
            )
            Text(
                text = "Detail",
                modifier = Modifier.weight(2f),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = Color.White
            )
        }

        // Table Content
        listOf(
            "ID Tim" to tim.idtim,
            "Nama Tim" to tim.namatim,
            "Deskripsi" to tim.deskripsitim
        ).forEachIndexed { index, (field, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (index % 2 == 0) Color(0xFFF7F7F7) else Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = field,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = value,
                    modifier = Modifier.weight(2f),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}



@Composable
fun DetailRow(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(0.4f),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = ":",
            modifier = Modifier.padding(horizontal = 4.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            modifier = Modifier.weight(0.6f),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun DetailContent(tim: Tim, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        // Tabel untuk menampilkan detail tim
        Column(
            modifier = Modifier.fillMaxWidth(),

            ) {
            // Header Tabel
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp)
                    .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(8.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFB771E5),
                                Color(0xFF8B5DFF)
                            )
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Header Items
                Text(
                    text = "ID Tim",
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp,
                    color = Color.White
                )
                Text(
                    text = "Judul",
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp,
                    color = Color.White
                )
                Text(
                    text = "Penulis",
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp,
                    color = Color.White
                )
                Text(
                    text = "Kategori",
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp,
                    color = Color.White
                )
                Text(
                    text = "Status",
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp,
                    color = Color.White
                )
            }

            // Baris Detail Tim
            Row(
                modifier = Modifier
                    .fillMaxWidth()

                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Detail Items
                Text(
                    text = tim.idtim,
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp
                )
                Text(
                    text = tim.namatim,
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp
                )
                Text(
                    text = tim.deskripsitim,
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp
                )
            }
        }
    }
}