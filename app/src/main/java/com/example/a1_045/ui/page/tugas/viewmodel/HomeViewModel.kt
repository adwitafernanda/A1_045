package com.example.a1_045.ui.page.tugas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1_045.model.Tugas
import com.example.a1_045.repository.TugasRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class  HomeUiState {
    data class Success(val tugas: List <Tugas>): HomeUiState()
    object  Error : HomeUiState()
    object  Loading : HomeUiState()
}

class HomeTugasViewModel (private val tgs: TugasRepository): ViewModel(){
    var tgsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init{
        getTgs()
    }

    fun getTgs(){
        viewModelScope.launch{
            tgsUIState = HomeUiState.Loading
            tgsUIState = try {
                HomeUiState.Success(tgs.getTugas().data)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }


    fun deleteTgs(id:String) {
        viewModelScope.launch {
            try {
                tgs.deleteTugas(id)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}