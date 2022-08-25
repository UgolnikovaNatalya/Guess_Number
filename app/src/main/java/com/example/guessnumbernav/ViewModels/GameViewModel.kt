package com.example.guessnumbernav.ViewModels

import android.app.Application
import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guessnumbernav.R
import java.lang.NumberFormatException

private const val ATTEMPTS = 7

enum class Smiles {
    WIN,
    SMILE,
    SAD,
    CRY
}

enum class Toasts {
    EMPTY,
    BIGGER,
    ERROR
}

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val _greetingVisible = MutableLiveData<Boolean>()
    private val _greetingText = MutableLiveData<String>()
    private val _smileVisible = MutableLiveData<Boolean>()
    private val _smilePicture = MutableLiveData<Smiles>()
    private val _tryNumberFieldVisible = MutableLiveData<Boolean>()
    private val _tryNumberFieldText = MutableLiveData<String>()
    private val _btnTryVisible = MutableLiveData<Boolean>()
    private val _againBtnVisible = MutableLiveData<Boolean>()
    private val _keyBoardVisible = MutableLiveData<Boolean>(true)
    private val _toast = MutableLiveData<Toasts>()
    private val attempts = MutableLiveData<Int>(ATTEMPTS)
    private val _usNUmber = MutableLiveData<Int>(null)

    val greetingVisible: LiveData<Boolean> = _greetingVisible
    val greetingText: LiveData<String> = _greetingText
    val smileVisible: LiveData<Boolean> = _smileVisible
    val smilePicture: LiveData<Smiles> = _smilePicture
    val tryNumberFieldVisible: LiveData<Boolean> = _tryNumberFieldVisible
    val tryNumberFieldText: LiveData<String> = _tryNumberFieldText
    val btnTryVisible: LiveData<Boolean> = _btnTryVisible
    val againBtnVisible: LiveData<Boolean> = _againBtnVisible
    val toast: LiveData<Toasts> = _toast
    val keyboardVisible: LiveData<Boolean> = _keyBoardVisible
    val usNumber : LiveData<Int> = _usNUmber


    //get magic number
    private var magNumber = -1
    fun getMagicNUmber(number: Int) {
        magNumber = number
        Log.e("fr", "$magNumber - magNumber getMagicNumber fun")

    }

    //get user number
    var userNumber = ""
    fun getUserNumber(number: String) {
        userNumber = number
        Log.e("fr", "$userNumber - userNUmber getUserNumber fun")
    }

    // *************** L O A D ******************
    fun load() {
        _greetingVisible.value = true
        _greetingText.value =
            getMessageWithAttempts(R.string.play_greet, attempts.value ?: ATTEMPTS)
        _smileVisible.value = true
        _smilePicture.value = Smiles.SMILE
        _tryNumberFieldVisible.value = true
        _tryNumberFieldText.value = getMessage(R.string.empty)
        _btnTryVisible.value = true
        _againBtnVisible.value = false
        _keyBoardVisible.value = true
        try {
            when {
                attempts.value == ATTEMPTS -> {
                    _greetingVisible.value = true
                    _greetingText.value =
                        getMessageWithAttempts(R.string.play_greet, attempts.value)
                    _smileVisible.value = true
                    _smilePicture.value = Smiles.SMILE
                }
                (attempts.value!! > 0) -> {
                    if (magNumber == userNumber.toInt()) {
                        _greetingVisible.value = true
                        _greetingText.value = getMessageWithAttempts(R.string.win, attempts.value)
                        _btnTryVisible.value = false
                        _againBtnVisible.value = true
                        _tryNumberFieldVisible.value = false
                        _smileVisible.value = true
                        _smilePicture.value = Smiles.WIN
                    }
                    if (magNumber > userNumber.toInt()) {
                        _greetingVisible.value = true
                        _greetingText.value = getMessageWithAttempts(R.string.less, attempts.value)
                        _tryNumberFieldText.value = getMessage(R.string.empty)
                        _smileVisible.value = true
                        _smilePicture.value = Smiles.SAD
                        _keyBoardVisible.value = false
                    }
                    if (magNumber < userNumber.toInt()) {
                        _greetingVisible.value = true
                        _greetingText.value =
                            getMessageWithAttempts(R.string.bigger, attempts.value)
                        _tryNumberFieldText.value = getMessage(R.string.empty)

                        _smileVisible.value = true
                        _smilePicture.value = Smiles.SAD
                    }
                }
                attempts.value == 0 -> {
                    _greetingVisible.value = true
                    _greetingText.value = getMessageWithAttempts(R.string.loose, magNumber)
                    _btnTryVisible.value = false
                    _againBtnVisible.value = true
                    _tryNumberFieldVisible.value = false
                    _smileVisible.value = true
                    _smilePicture.value = Smiles.CRY
                    _keyBoardVisible.value = false
                }
            }
        } catch (e: NumberFormatException) {
        }
    }

    //************************* C O M P A R E ***********************************
    fun compareNumbers(userNumber: String) {
        //обрабатываем исключение
        try {
            if (userNumber.isEmpty() || userNumber.toIntOrNull() == 0) {
                Log.e("fr", "$userNumber - userNumber compare")
                _toast.value = Toasts.EMPTY
            }
            if (userNumber.toInt() == 0){
                Log.e("fr", "$userNumber - userNumber compare")
                _tryNumberFieldText.value = getMessage(R.string.empty)
                _toast.value = Toasts.EMPTY
            }
            if (userNumber.toInt() > 100 && userNumber.toInt() >= 1) {
                _toast.value = Toasts.BIGGER
                _tryNumberFieldText.value = getMessage(R.string.empty)


            } else {
                attempts.value = attempts.value?.minus(1) //-попытка

                if (attempts.value!! > 0) {
                    when {
                        magNumber == userNumber.toInt() -> {
                            _greetingVisible.value = true
                            _greetingText.value = getMessage(R.string.win)
                            _btnTryVisible.value = false
                            _againBtnVisible.value = true
                            _tryNumberFieldVisible.value = false
                            _smileVisible.value = true
                            _smilePicture.value = Smiles.WIN
                            _keyBoardVisible.value = false
                        }
                        magNumber > userNumber.toInt() -> {
                            _greetingVisible.value = true
                            _greetingText.value =
                                getMessageWithAttempts(R.string.less, attempts.value)
                            _tryNumberFieldText.value = getMessage(R.string.empty)
                            _smileVisible.value = true
                            _smilePicture.value = Smiles.SAD

                        }
                        magNumber < userNumber.toInt() -> {
                            _greetingVisible.value = true
                            _greetingText.value =
                                getMessageWithAttempts(R.string.bigger, attempts.value)
                            _tryNumberFieldText.value = getMessage(R.string.empty)
                            _smileVisible.value = true
                            _smilePicture.value = Smiles.SAD
                        }
                    }
                } else {
                    _greetingVisible.value = true
                    _greetingText.value = getMessageWithAttempts(R.string.loose, magNumber)
                    _tryNumberFieldVisible.value = false
                    _btnTryVisible.value = false
                    _againBtnVisible.value = true
                    _tryNumberFieldVisible.value = false
                    _smileVisible.value = true
                    _smilePicture.value = Smiles.CRY
                    _keyBoardVisible.value = false
                }
            }
        } catch (e: NumberFormatException) {
            Log.d("error", "Empty try number field")
        }
    }

    private fun getMessage(text: Int): String {
        return getApplication<Application>().resources.getString(text)
    }

    private fun getMessageWithAttempts(text: Int, attempts: Int?): String {
        return getApplication<Application>().resources.getString(text, attempts)
    }

}