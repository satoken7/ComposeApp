package com.example.composeapp

import android.icu.text.SimpleDateFormat
import android.text.format.DateFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {
    private val _isAndroid = MutableStateFlow(true)
    val isAndroid : StateFlow<Boolean> = _isAndroid

    private val _dateText = MutableStateFlow("")
    val dateText : StateFlow<String> = _dateText

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                while (true) {
                    _dateText.value = getDataText()
                    delay(1000)
                }
            }.onFailure {
                Log.w("tag",it)
            }
        }

    }

    private fun getDataText() :String {
        val date = Date()
        val local = Locale.getDefault()
        val pattern = DateFormat.getBestDateTimePattern(local,"yyyyMMddHHmmss")
        return SimpleDateFormat(pattern,local).format(date)
    }


    fun onClick() {
        _isAndroid.value = !_isAndroid.value
    }


}