package com.example.a1_045.ui.page.proyek.view

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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a1_045.model.Proyek
import com.example.a1_045.repository.ProyekRepository
import com.example.a1_045.ui.costumewidget.CustomTopAppBar
import com.example.a1_045.ui.navigation.DestinasiNavigasi
import com.example.a1_045.ui.page.proyek.viewmodel.DetailProyekViewModel
import com.example.a1_045.ui.page.proyek.viewmodel.DetailProyekViewModelFactory


object DestinasiDetailProyek : DestinasiNavigasi {
    override val route = "detailproyek"
    override val titleRes = "Detail Proyek"
    const val ID = "idProyek"
    val routesWithArg = "$route/{$ID}"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProyekScreen(idProyek: String,
                     repository: ProyekRepository,
                     navigateBack: () -> Unit,
                     modifier: Modifier = Modifier
) {
    val viewModel: DetailProyekViewModel = viewModel(factory = DetailProyekViewModelFactory(repository))

    var proyek by remember { mutableStateOf<Proyek?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idProyek) {
        viewModel.getProyekbyid(idProyek) { result ->
            proyek = result
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailProyek.titleRes,
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
            if (proyek != null) {
                DetailContentModern(proyek = proyek!!, modifier = Modifier.padding(innerPadding))
            } else {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Text("Proyek tidak ditemukan")
                }
            }
        }
    }
}
@Composable
fun DetailContentModern(proyek: Proyek, modifier: Modifier = Modifier) {
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
            "ID Proyek" to proyek.idProyek,
            "Nama Proyek" to proyek.namaproyek,
            "Deskripsi" to proyek.deskripsiproyek,
            "Tanggal Mulai" to proyek.tanggalmulai,
            "Tanggal Berakhir" to proyek.tanggalberakhir,
            "Status" to proyek.statusproyek.toString()
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
                    text = value.toString(),
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
fun DetailContent(proyek: Proyek, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        // Tabel untuk menampilkan detail proyek
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
                    text = "ID Proyek",
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

            // Baris Detail Proyek
            Row(
                modifier = Modifier
                    .fillMaxWidth()

                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Detail Items
                Text(
                    text = proyek.idProyek.toString(),
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp
                )
                Text(
                    text = proyek.namaproyek,
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp
                )
                Text(
                    text = proyek.deskripsiproyek,
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp
                )
                Text(
                    text = proyek.tanggalmulai,
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp
                )
                Text(
                    text = proyek.tanggalberakhir,
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp
                )
                Text(
                    text = proyek.statusproyek.toString(), // Pastikan status adalah String
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp
                )
            }
        }
    }
}