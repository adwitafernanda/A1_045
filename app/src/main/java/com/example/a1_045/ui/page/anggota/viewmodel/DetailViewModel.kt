package com.example.a1_045.ui.page.anggota.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a1_045.model.Anggota
import com.example.a1_045.repository.AnggotaRepository
import kotlinx.coroutines.launch
import java.io.IOException

class DetailAnggotaViewModelFactory(private val repository: AnggotaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailAnggotaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailAnggotaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class DetailAnggotaViewModel(private val anggota: AnggotaRepository) : ViewModel() {

    fun getAnggotabyid(idanggota: String, onResult: (Anggota?) -> Unit) {
        viewModelScope.launch {
            val anggota = try {
                anggota.getAnggotabyid(idanggota)
            } catch (e: IOException) {
                null // Tangani error jika ada masalah koneksi
            } catch (e: HttpException) {
                null // Tangani error jika respons dari server salah
            }
            onResult(anggota)
        }
    }


}