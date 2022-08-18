package com.example.guessnumbernav.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guessnumbernav.R
import java.lang.NumberFormatException

//private const val MAX_NUMBER = 100
//
//class FriendViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val _greetingVisible = MutableLiveData<Boolean>()
//    private val _greetingText = MutableLiveData<String>()
//    private val _friendNumberVisible = MutableLiveData<Boolean>()
//    private val _friendNumberText = MutableLiveData<String>()
//    private val _playBtnVisible = MutableLiveData<Boolean>()
//    private val _linearVisible = MutableLiveData<Boolean>()
//    private val _toast = MutableLiveData<String>()
//
//
//
//    val greetingVisible: LiveData<Boolean> = _greetingVisible
//    val greetingText : LiveData<String> = _greetingText
//    val friendNumberVisible: LiveData<Boolean> = _friendNumberVisible
//    val friendNumberText: LiveData<String> = _friendNumberText
//    val playBtnVisible: LiveData<Boolean> = _playBtnVisible
//    val linearVisible:LiveData<Boolean> = _linearVisible
//    val toast : LiveData<String> = _toast
//
//    fun load(){
//        _linearVisible.value = true
//        _greetingVisible.value = true
//        _greetingText.value = getMessage(R.string.user_enter_number)
//        _friendNumberVisible.value = true
//        _playBtnVisible.value = true
//    }
//
//
//
//    //по вводу числа и нажатию на кнопку, будет происходить сравнение чисел
//    //
//    fun startGame(number: String) {
//        _friendNumberText.value = number
//
//    }
//
//    private fun getMessage(message: Int): String? {
//        return getApplication<Application>().resources.getString(message)
//    }
//
//}