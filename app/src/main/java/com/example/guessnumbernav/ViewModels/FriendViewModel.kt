package com.example.guessnumbernav.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guessnumbernav.R
import java.lang.NumberFormatException

private const val MAX_NUMBER = 100

class FriendViewModel(application: Application) : AndroidViewModel(application) {

    private val _greetingVisible = MutableLiveData<Boolean>()
    private val _greetingText = MutableLiveData<String>()
    private val _friendNumberVisible = MutableLiveData<Boolean>()
    private val _friendNumberText = MutableLiveData<String>()
    private val _playBtnVisible = MutableLiveData<Boolean>()
    private val _linearVisible = MutableLiveData<Boolean>()
    private val _toast = MutableLiveData<Toasts>()
    private val _keyboardVisible = MutableLiveData<Boolean>()
    private val _nextFragment = MutableLiveData<Boolean>(false)

    val greetingVisible: LiveData<Boolean> = _greetingVisible
    val greetingText: LiveData<String> = _greetingText
    val friendNumberVisible: LiveData<Boolean> = _friendNumberVisible
    val friendNumberText: LiveData<String> = _friendNumberText
    val playBtnVisible: LiveData<Boolean> = _playBtnVisible
    val linearVisible: LiveData<Boolean> = _linearVisible
    val toast: LiveData<Toasts> = _toast
    val keyboardVisible: LiveData<Boolean> = _keyboardVisible
    val nextFragment: LiveData<Boolean> = _nextFragment

    fun load() {
        _linearVisible.value = true
        _greetingVisible.value = true
        _greetingText.value = getMessage(R.string.user_enter_number)
        _friendNumberVisible.value = true
        _playBtnVisible.value = true
        _keyboardVisible.value = true
    }

    fun startGame(number: String){
        try {
            when {
                number.isEmpty() || number.toInt() == 0 -> {
                    _friendNumberText.value = getMessage(R.string.empty)
                    _toast.value = Toasts.EMPTY
                    return
                }
                number.toInt() > MAX_NUMBER -> {
                    _toast.value = Toasts.BIGGER
                    _friendNumberText.value = getMessage(R.string.empty)
                    return
                }
                else -> {
                    _nextFragment.value = true
                }
            }
        } catch (e: NumberFormatException) {
            _toast.value = Toasts.ERROR
        }

    }

    private fun getMessage(message: Int): String? {
        return getApplication<Application>().resources.getString(message)
    }

}