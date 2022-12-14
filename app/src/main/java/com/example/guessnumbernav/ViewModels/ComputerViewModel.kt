package com.example.guessnumbernav.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guessnumbernav.R
import kotlin.concurrent.thread

class ComputerViewModel(application: Application) : AndroidViewModel(application) {

    private val _greetingVisible = MutableLiveData<Boolean>()
    private val _greetingText = MutableLiveData<String>()
    private val _generateButtonVisible = MutableLiveData<Boolean>()
    private val _playButtonVisible = MutableLiveData<Boolean>()
    private val _progressbarVisible = MutableLiveData<Boolean>()
    //для сохранения числа
    private val _savedNumber = MutableLiveData<Int>()

    val greetingText : LiveData<String> = _greetingText
    val greetingVisible : LiveData<Boolean> = _greetingVisible
    val generateButtonVisible : LiveData<Boolean> = _generateButtonVisible
    val playButtonVisible : LiveData<Boolean> = _playButtonVisible
    val progressbarVisible : LiveData<Boolean> = _progressbarVisible

    //создаю переменную для генерации числа
    var generatedNumber = 0


    fun load(){
        _greetingVisible.value = true
        _greetingText.value = getMessage(R.string.comp_greet)
        _generateButtonVisible.value = true
        _playButtonVisible.value = false
        _progressbarVisible.value = false

    }

    //по нажатию на кнопку, генерируется число

    fun generateNumber() : Int {
        //генерируем число
        generatedNumber = (1..100).random()
        Log.e("fr", "$generatedNumber - gen number")
        //и его надо сохранить, чтобы передать в другой фрагмент для сравнения.
        _savedNumber.value = generatedNumber


        _progressbarVisible.value = true
        _generateButtonVisible.value = false
        thread {
            Thread.sleep(1_000)
            _progressbarVisible.postValue(false)
            _greetingText.postValue(getMessage(R.string.comp_generate))
            _playButtonVisible.postValue(true)
        }

        return generatedNumber
    }

    private fun getMessage(message: Int): String? {
        return getApplication<Application>().resources.getString(message)
    }

}