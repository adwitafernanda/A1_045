package com.example.a1_045.ui.page.tugas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1_045.model.Proyek
import com.example.a1_045.model.Tim
import com.example.a1_045.model.Tugas
import com.example.a1_045.repository.ProyekRepository
import com.example.a1_045.repository.TimRepository
import com.example.a1_045.repository.TugasRepository
import kotlinx.coroutines.launch


class InsertTugasViewModel(
    private val tugas: TugasRepository,
    private val proyek: ProyekRepository,
    private val tim: TimRepository
): ViewModel(){
    var tugasUiState by mutableStateOf(TugasUiState())
        private set

    var proyekList by mutableStateOf(listOf<Proyek>())
        private set

    var timList by mutableStateOf(listOf<Tim>())
        private set

    init {
        loadData()
    }

    fun UpdateInsertTugasState(tugasUiEvent: TugasUiEvent){
        tugasUiState = TugasUiState(tugasUiEvent = tugasUiEvent)
    }

    suspend fun InsertTugas(){
        viewModelScope.launch {
            try {
                tugas.insertTugas(tugasUiState.tugasUiEvent.toTugas())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun loadData(){
        viewModelScope.launch {
            try {
                proyekList = proyek.getProyek().data
                timList = tim.getTim().data
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }




}

data class TugasUiState(
    val tugasUiEvent: TugasUiEvent = TugasUiEvent()
)

data class TugasUiEvent(
    val idtugas: String = "",
    val idProyek: String = "",
    val idtim: String = "",
    val namatugas: String = "",
    val deskripsitugas: String = "",
    val prioritas: String = "",
    val statustugas: String = "",

    val status: String =""
)


fun TugasUiEvent.toTugas(): Tugas = Tugas(
    idtugas =  idtugas,
    idProyek = idProyek,
    idtim = idtim,
    namatugas = namatugas,
    deskripsitugas = deskripsitugas,
    prioritas = prioritas,
    statustugas = statustugas
)

fun Tugas.toUiStateTugas(): TugasUiState = TugasUiState(
    tugasUiEvent = toTugasUiEvent()
)

fun Tugas.toTugasUiEvent(): TugasUiEvent = TugasUiEvent(
    idtugas =  idtugas,
    idProyek = idProyek.toString(),
    idtim = idtim.toString(),
    namatugas = namatugas,
    deskripsitugas = deskripsitugas,
    prioritas = prioritas,
    statustugas = statustugas
)