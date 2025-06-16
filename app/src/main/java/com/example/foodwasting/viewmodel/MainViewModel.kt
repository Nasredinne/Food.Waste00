package com.example.foodwasting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodwasting.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
):ViewModel() {



    fun makeRequest(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.makeRequest()
        }

    }
}