package com.example.guessnumbernav.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guessnumbernav.R

class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val _contentVisible = MutableLiveData<Boolean>()
    private val _greet = MutableLiveData<String>()

    val contentVisible: LiveData<Boolean> = _contentVisible
    val greet : LiveData<String> = _greet

    fun load(){
        _contentVisible.value = true
        _greet.value = getMessage(R.string.choose_play)
    }

    private fun getMessage(text: Int): String {
        return getApplication<Application>().resources.getString(text)
    }

}