package com.example.a1_045.ui.page.anggota.view

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
import com.example.a1_045.model.Anggota
import com.example.a1_045.repository.AnggotaRepository
import com.example.a1_045.ui.costumewidget.CustomTopAppBar
import com.example.a1_045.ui.navigation.DestinasiNavigasi
import com.example.a1_045.ui.page.anggota.viewmodel.DetailAnggotaViewModel
import com.example.a1_045.ui.page.anggota.viewmodel.DetailAnggotaViewModelFactory

object DestinasiDetailAnggota : DestinasiNavigasi {
    override val route = "detailanggota"
    override val titleRes = "Detail Anggota"
    const val IDAnggota = "idanggota"
    val routesWithArg = "$route/{$IDAnggota}"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAnggotaScreen(idanggota: String,
                        repository: AnggotaRepository,
                        navigateBack: () -> Unit,
                        modifier: Modifier = Modifier
) {
    val viewModel: DetailAnggotaViewModel = viewModel(factory = DetailAnggotaViewModelFactory(repository))

    var anggota by remember { mutableStateOf<Anggota?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idanggota) {
        viewModel.getAnggotabyid(idanggota) { result ->
            anggota = result
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailAnggota.titleRes,
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
            if (anggota != null) {
                DetailContentModern(anggota = anggota!!, modifier = Modifier.padding(innerPadding))
            } else {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Text("Anggota tidak ditemukan")
                }
            }
        }
    }
}
@Composable
fun DetailContentModern(anggota: Anggota, modifier: Modifier = Modifier) {
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
            "ID Anggota" to anggota.idanggota,
            "ID Tim" to anggota.idtim,
            "Nama Anggota" to anggota.namaanggota,
            "Peran Anggota" to anggota.peran
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
fun DetailContent(anggota: Anggota, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        // Tabel untuk menampilkan detail anggota
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
                    text = "ID Anggota",
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp,
                    color = Color.White
                )
                Text(
                    text = "ID Tim",
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp,
                    color = Color.White
                )
                Text(
                    text = "Nama Anggota",
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp,
                    color = Color.White
                )
                Text(
                    text = "Peran Anggota",
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp,
                    color = Color.White
                )
            }

            // Baris Detail Anggota
            Row(
                modifier = Modifier
                    .fillMaxWidth()

                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Detail Items
                Text(
                    text = anggota.idanggota,
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp
                )
                anggota.idtim?.let {
                    Text(
                        text = it,
                        modifier = Modifier.weight(1f).padding(8.dp),
                        fontSize = 9.sp
                    )
                }
                Text(
                    text = anggota.namaanggota,
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp
                )
                Text(
                    text = anggota.peran.toString(), // Pastikan status adalah String
                    modifier = Modifier.weight(1f).padding(8.dp),
                    fontSize = 9.sp
                )
            }
        }
    }
}