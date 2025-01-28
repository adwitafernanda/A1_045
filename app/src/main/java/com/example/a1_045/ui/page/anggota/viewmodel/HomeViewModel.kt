package com.example.a1_045.ui.page.anggota.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1_045.model.Anggota
import com.example.a1_045.repository.AnggotaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class  HomeUiState {
    data class Success(val anggota: List <Anggota>): HomeUiState()
    object  Error : HomeUiState()
    object  Loading : HomeUiState()
}

class HomeAnggotaViewModel (private val agt: AnggotaRepository): ViewModel(){
    var agtUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init{
        getAgt()
    }

    fun getAgt(){
        viewModelScope.launch{
            agtUIState = HomeUiState.Loading
            agtUIState = try {
                HomeUiState.Success(agt.getAnggota().data)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }


    fun deleteAgt(id:String) {
        viewModelScope.launch {
            try {
                agt.deleteAnggota(id)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}