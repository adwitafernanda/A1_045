package com.example.a1_045.ui.page.proyek.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a1_045.model.Proyek
import com.example.a1_045.repository.ProyekRepository
import com.example.a1_045.ui.page.proyek.view.DestinasiDetailProyek
import kotlinx.coroutines.launch
import java.io.IOException

class DetailProyekViewModelFactory(private val repository: ProyekRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailProyekViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailProyekViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class DetailProyekViewModel(private val proyek:ProyekRepository) : ViewModel() {

    fun getProyekbyid(idProyek: String, onResult: (Proyek?) -> Unit) {
        viewModelScope.launch {
            val proyek = try {
                proyek.getProyekbyid(idProyek)
            } catch (e: IOException) {
                null // Tangani error jika ada masalah koneksi
            } catch (e: HttpException) {
                null // Tangani error jika respons dari server salah
            }
            onResult(proyek)
        }
    }


}