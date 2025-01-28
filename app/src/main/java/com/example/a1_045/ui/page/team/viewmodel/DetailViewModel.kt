package com.example.a1_045.ui.page.team.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a1_045.model.Tim
import com.example.a1_045.repository.TimRepository
import kotlinx.coroutines.launch
import java.io.IOException

class DetailTimViewModelFactory(private val repository: TimRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailTimViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailTimViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class DetailTimViewModel(private val tim: TimRepository) : ViewModel() {

    fun getTimbyid(idTim: String, onResult: (Tim?) -> Unit) {
        viewModelScope.launch {
            val tim = try {
                tim.getTimbyid(idTim)
            } catch (e: IOException) {
                null // Tangani error jika ada masalah koneksi
            } catch (e: HttpException) {
                null // Tangani error jika respons dari server salah
            }
            onResult(tim)
        }
    }


}