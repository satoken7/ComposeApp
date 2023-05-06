package com.example.composeapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _isAndroid = MutableStateFlow(true)
    val isAndroid : StateFlow<Boolean> = _isAndroid

    init {

    }

    fun onClick() {
        _isAndroid.value = !_isAndroid.value
    }


}