package com.example.a1_045.ui.page.proyek.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a1_045.ProyekApplications
import com.example.a1_045.ui.page.anggota.view.HomeAnggotaScreen
import com.example.a1_045.ui.page.anggota.view.InsertAnggotaScreen
import com.example.a1_045.ui.page.anggota.viewmodel.DetailAnggotaViewModel
import com.example.a1_045.ui.page.anggota.viewmodel.DetailAnggotaViewModelFactory
import com.example.a1_045.ui.page.anggota.viewmodel.HomeAnggotaViewModel
import com.example.a1_045.ui.page.anggota.viewmodel.InsertAnggotaViewModel
import com.example.a1_045.ui.page.anggota.viewmodel.UpdateAnggotaViewModel
import com.example.a1_045.ui.page.team.viewmodel.DetailTimViewModel
import com.example.a1_045.ui.page.team.viewmodel.HomeTimViewModel
import com.example.a1_045.ui.page.team.viewmodel.InsertTimViewModel
import com.example.a1_045.ui.page.team.viewmodel.UpdateTimViewModel
import com.example.a1_045.ui.page.tugas.viewmodel.DetailTugasViewModel
import com.example.a1_045.ui.page.tugas.viewmodel.HomeTugasViewModel
import com.example.a1_045.ui.page.tugas.viewmodel.InsertTugasViewModel
import com.example.a1_045.ui.page.tugas.viewmodel.UpdateTugasViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiProyek().containerproyek.proyekRepository)}
        initializer { DetailProyekViewModel(aplikasiProyek().containerproyek.proyekRepository)}
        initializer { InsertViewModel(aplikasiProyek().containerproyek.proyekRepository)}
        initializer { UpdateViewModel(createSavedStateHandle(), aplikasiProyek().containerproyek.proyekRepository) }

        initializer { HomeTimViewModel(aplikasiProyek().containerproyek.timRepository) }
        initializer { DetailTimViewModel(aplikasiProyek().containerproyek.timRepository) }
        initializer { InsertTimViewModel(aplikasiProyek().containerproyek.timRepository) }
        initializer { UpdateTimViewModel(createSavedStateHandle(),aplikasiProyek().containerproyek.timRepository) }

        initializer { HomeTugasViewModel(aplikasiProyek().containerproyek.tugasRepository) }
        initializer { DetailTugasViewModel(aplikasiProyek().containerproyek.tugasRepository) }
        initializer { InsertTugasViewModel(aplikasiProyek().containerproyek.tugasRepository,
            proyek = aplikasiProyek().containerproyek.proyekRepository,
            tim = aplikasiProyek().containerproyek.timRepository
        ) }
        initializer { UpdateTugasViewModel(createSavedStateHandle(),aplikasiProyek().containerproyek.tugasRepository,
         proyek = aplikasiProyek().containerproyek.proyekRepository,
            tim = aplikasiProyek().containerproyek.timRepository )}


        initializer { HomeAnggotaViewModel(aplikasiProyek().containerproyek.anggotaRepository) }
        initializer { DetailAnggotaViewModel(aplikasiProyek().containerproyek.anggotaRepository)}
        initializer { InsertAnggotaViewModel(aplikasiProyek().containerproyek.anggotaRepository,
            tim = aplikasiProyek().containerproyek.timRepository) }
        initializer { UpdateAnggotaViewModel(createSavedStateHandle(),aplikasiProyek().containerproyek.anggotaRepository,
            tim = aplikasiProyek().containerproyek.timRepository) }
    }
}

fun CreationExtras.aplikasiProyek(): ProyekApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as ProyekApplications)