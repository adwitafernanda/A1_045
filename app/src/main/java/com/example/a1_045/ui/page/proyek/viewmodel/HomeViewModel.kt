package com.example.a1_045.ui.page.proyek.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a1_045.model.Proyek
import com.example.a1_045.repository.ProyekRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class  HomeUiState {
    data class Success(val proyek: List <Proyek>): HomeUiState()
    object  Error : HomeUiState()
    object  Loading : HomeUiState()
}

class HomeViewModel (private val pyk: ProyekRepository): ViewModel(){
    var pykUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init{
        getPyk()
    }

    fun getPyk(){
        viewModelScope.launch{
            pykUIState = HomeUiState.Loading
            pykUIState = try {
                HomeUiState.Success(pyk.getProyek().data)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }


    fun deletePyk(id:String) {
        viewModelScope.launch {
            try {
                pyk.deleteProyek(id)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}