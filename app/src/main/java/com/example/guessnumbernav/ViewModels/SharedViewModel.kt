package com.example.guessnumbernav.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guessnumbernav.R
import kotlin.concurrent.thread

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    //commons
    private val _greetingVisible = MutableLiveData<Boolean>(true)
    private val _greetingText = MutableLiveData<String>()
    private var _number = MutableLiveData<Int>()
    val greetingVisible : LiveData<Boolean> = _greetingVisible
    val greetingText : LiveData<String> = _greetingText
    var number : LiveData<Int> = _number

    //friend fragment
    private val _friendNumberVisible = MutableLiveData<Boolean>()
    private val _friendNumberText = MutableLiveData<String>()
    private val _playBtnVisible = MutableLiveData<Boolean>()
    private val _linearVisible = MutableLiveData<Boolean>(true)
    val friendNumberVisible: LiveData<Boolean> = _friendNumberVisible
    val friendNumberText: LiveData<String> = _friendNumberText
    val playBtnVisible: LiveData<Boolean> = _playBtnVisible
    val linearVisible:LiveData<Boolean> = _linearVisible

    //comp fragment
    private val _generateButtonVisible = MutableLiveData<Boolean>()
    private val _playButtonVisible = MutableLiveData<Boolean>()
    private val _progressbarVisible = MutableLiveData<Boolean>()
    val generateButtonVisible : LiveData<Boolean> = _generateButtonVisible
    val playButtonVisible : LiveData<Boolean> = _playButtonVisible
    val progressbarVisible : LiveData<Boolean> = _progressbarVisible

    //play fragment
    private val _smileVisibility = MutableLiveData<Boolean>()
    private val _btnTryVisibility = MutableLiveData<Boolean>()
    private val _againBtnVisibility = MutableLiveData<Boolean>()
    private val _tryNumberFieldVisible = MutableLiveData<Boolean>()
    private val _tryNumberFieldText = MutableLiveData<String>()
    val smileVisibility: LiveData<Boolean> = _smileVisibility
    val btnTryVisibility: LiveData<Boolean> = _btnTryVisibility
    val againBtnVisibility: LiveData<Boolean> = _againBtnVisibility
    val tryNumberFieldVisible : LiveData<Boolean> = _tryNumberFieldVisible
    val tryNumberFieldText : LiveData<String> = _tryNumberFieldText

    private var attempts = 7
    //создаю число, которое буду генерировать
    private var generatedNumber = -1

    //загрузка фрагмента игры с андроидом
    fun loadComp(){
        _greetingVisible.value
        _greetingText.value = getMessage(R.string.comp_greet)
        _generateButtonVisible.value = true
        _playButtonVisible.value = false
        _progressbarVisible.value = false
    }

    //загрузка фрагмента игры с другом
    fun loadFriend(){
        _linearVisible.value
        _greetingVisible.value
        _greetingText.value = getMessage(R.string.user_enter_number)
        _friendNumberVisible.value = true
        _playBtnVisible.value = true
    }

    //загрузка фрагмента игры
    fun loadPlay(){
        _greetingVisible.value
        _greetingText.value = getPlayMessage(R.string.play_greet, attempts)
        _tryNumberFieldVisible.value = true
        _tryNumberFieldText.value = "22"
        _btnTryVisibility.value = true
        _againBtnVisibility.value = false
        Log.e("num", "{${_number.value}} - number value gen")
    }

    //******** G a m e s ***********

    //сохранение введеного числа юзера
    fun friendStartGame(mNumber : String){
        _number.value = mNumber.toInt()
    }

    //генерация числа и его сохранение в liveData
    fun compGenerateNumber()  {
        //генерируем число
        generatedNumber = (1..100).random()
        //сохраняю в лайв дату
        _number.value = generatedNumber

        _progressbarVisible.value = true
        _generateButtonVisible.value = false
        thread {
            Thread.sleep(1_000)
            _progressbarVisible.postValue(false)
            _greetingText.postValue(getMessage(R.string.comp_generate))
            _playButtonVisible.postValue(true)
        }
        Log.e("num", "{${_number.value}} - number.value")
        Log.e("num", "$generatedNumber - generatedNumber")

    }

    //игра. Должно быть восстановление сгенерированного числа или числа пользователя
    //сравнение чисел и вывод результата
    fun playGame(){
        //тут надо сгенерированное число восстановить, чтобы дальше проводить проверки

        //после нажатия вычитаю попытку
        attempts--
        //проверка числа
        Log.e("num", "${_number.value} - playGame") // - число пустое
        //вывод сообщения
        _greetingText.value = getPlayMessage(R.string.play_greet, attempts)
    }


    //для изменения приветстенного сообщения у всех врагментов, кроме игрового
    private fun getMessage(message: Int): String? {
        return getApplication<Application>().resources.getString(message)
    }

    //изменение сообщений для игрового фрагмента
    private fun getPlayMessage(message: Int, attempts: Int): String? {
        return getApplication<Application>().resources.getString(message, attempts)
    }
}