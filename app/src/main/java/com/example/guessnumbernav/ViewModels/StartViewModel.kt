package com.example.guessnumbernav.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guessnumbernav.R

class StartViewModel() : ViewModel() {
    private val _contentVisible = MutableLiveData<Boolean>()
    val contentVisible: LiveData<Boolean> = _contentVisible

    private val _greet = MutableLiveData<String>()
    val greet : LiveData<String> = _greet

    fun load(){
        _contentVisible.value = true
        _greet.value = R.string.choose_play.toString()
    }

}