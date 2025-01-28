package com.example.a1_045.ui.page.team.viewmodel

import coil.network.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1_045.model.Tim
import com.example.a1_045.repository.TimRepository
import kotlinx.coroutines.launch
import java.io.IOException


sealed class  HomeUiState {
    data class Success(val tim: List<Tim>): HomeUiState()
    object  Error : HomeUiState()
    object  Loading : HomeUiState()
}

class HomeTimViewModel (private val tm: TimRepository): ViewModel(){
    var tmUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init{
        getTm()
    }

    fun getTm(){
        viewModelScope.launch{
            tmUIState = HomeUiState.Loading
            tmUIState = try {
                HomeUiState.Success(tm.getTim().data)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }


    fun deleteTm(idTim:String) {
        viewModelScope.launch {
            try {
                tm.deleteTim(idTim)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}