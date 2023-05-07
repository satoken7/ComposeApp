package com.example.composeapp

import android.icu.text.SimpleDateFormat
import android.text.format.DateFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {
    private val _isAndroid = MutableStateFlow(true)
    val isAndroid : StateFlow<Boolean> = _isAndroid

    private val _inProgress = MutableStateFlow(false)
    val inProgress : StateFlow<Boolean> = _inProgress

    private val _dateText = MutableStateFlow("")
    val dateText : StateFlow<String> = _dateText

    private val _message = MutableStateFlow("")
    val message : StateFlow<String> = _message

    private val _login = MutableStateFlow("")
    val login : StateFlow<String> = _login

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                while (true) {
                    _dateText.value = getDataText()
                    delay(1000)
                }
            }.onFailure {
                Log.w(TAG,it)
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
    fun onDismiss() {
        _message.value = ""
    }
    fun onSelect(name: String) {
        _login.value = name
    }

    fun onGet() {
        if(_inProgress.value) {
            Log.d(TAG,"プログレス")
            return
        } else {
            _inProgress.value = true
        }
        viewModelScope.launch {
            kotlin.runCatching {
                val (request, response, result) = Fuel.get("https://api.github.com/users/${_login.value}")
                    .awaitStringResponseResult()
                Log.d(TAG,"request=$request")
                Log.d(TAG,"response=$response")
                Log.d(TAG,"result=$result")
                result.get()
            }.onSuccess {
                _message.value = it
            }.onFailure {
                Log.e(TAG,"onFailureですよ$it")
                _message.value = it.message.toString()
            }.also {
                _inProgress.value = false
            }
        }
    }
}