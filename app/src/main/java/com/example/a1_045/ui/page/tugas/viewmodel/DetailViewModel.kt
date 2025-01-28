package com.example.a1_045.ui.page.tugas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a1_045.model.Tugas
import com.example.a1_045.repository.TugasRepository
import kotlinx.coroutines.launch
import java.io.IOException

class DetailTugasViewModelFactory(private val repository: TugasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailTugasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailTugasViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class DetailTugasViewModel(private val tugas: TugasRepository) : ViewModel() {

    fun getTugasbyid(idtugas: String, onResult: (Tugas?) -> Unit) {
        viewModelScope.launch {
            val tugas = try {
                tugas.getTugasbyid(idtugas)
            } catch (e: IOException) {
                null // Tangani error jika ada masalah koneksi
            } catch (e: HttpException) {
                null // Tangani error jika respons dari server salah
            }
            onResult(tugas)
        }
    }


}