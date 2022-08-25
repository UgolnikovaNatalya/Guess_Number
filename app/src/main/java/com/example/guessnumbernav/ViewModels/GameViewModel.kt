package com.example.guessnumbernav.ViewModels

import android.app.Application
import android.util.Log
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guessnumbernav.R
import java.lang.NumberFormatException

private const val ATTEMPTS_DEFAULT = 7
private const val MAX_NUMBER = 100

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

    private val _scrollVisible = MutableLiveData<Boolean>()
    private val _greetingVisible = MutableLiveData<Boolean>()
    private val _userNumberFieldVisible = MutableLiveData<Boolean>()
    private val _smileVisible = MutableLiveData<Boolean>()
    private val _btnTryVisible = MutableLiveData<Boolean>()
    private val _btnAgainVisible = MutableLiveData<Boolean>()
    private val _keyBoardVisible = MutableLiveData<Boolean>()

    private val _greetingText = MutableLiveData<String>()
    private val _smilePicture = MutableLiveData<Smiles>()
    private val _userNumberText = MutableLiveData<String>()
    private val _toast = MutableLiveData<Toasts>()
    private val attempts = MutableLiveData<Int>(ATTEMPTS_DEFAULT)

    val greetingVisible: LiveData<Boolean> = _greetingVisible
    val greetingText: LiveData<String> = _greetingText
    val smileVisible: LiveData<Boolean> = _smileVisible
    val smilePicture: LiveData<Smiles> = _smilePicture
    val userNumberFieldVisible: LiveData<Boolean> = _userNumberFieldVisible
    val userNumberText: LiveData<String> = _userNumberText
    val btnTryVisible: LiveData<Boolean> = _btnTryVisible
    val againBtnVisible: LiveData<Boolean> = _btnAgainVisible
    val toast: LiveData<Toasts> = _toast
    val keyboardVisible: LiveData<Boolean> = _keyBoardVisible
    val scrollView: LiveData<Boolean> = _scrollVisible


    //get magic number
    private var magNumber = -1
    fun getMagicNUmber(number: Int) {
        magNumber = number
        Log.e("fr", "$magNumber - magNumber getMagicNumber fun")

    }

    //get user number
    private var userNumber = _userNumberText.value

    // *************** L O A D ******************
    fun load() {
        _scrollVisible.value = true
        _greetingVisible.value = true
        _smileVisible.value = true
        Log.e("se", "${attempts.value} - attempts load()")
        when {
            attempts.value == ATTEMPTS_DEFAULT -> {
                _greetingVisible.value = true
                _greetingText.value =
                    getMessage(R.string.play_greet, attempts.value)
                _userNumberFieldVisible.value = true
                _btnTryVisible.value = true
                _btnAgainVisible.value = false
                _smilePicture.value = Smiles.SMILE
            }

        }
    }

    fun compareNumbers(usNum: String) {
        Log.e("se", "${attempts.value} attempts compare")
        try {
            when {
                usNum.isEmpty() || usNum.toInt() == 0 -> {
                    _userNumberText.value = ""
                    _toast.value = Toasts.EMPTY
                }
                usNum.toInt() > MAX_NUMBER -> {
                    _userNumberText.value = ""
                    _toast.value = Toasts.BIGGER
                }
                else -> {
                    Log.e("se", "${attempts.value}  attempts -compare")
                    if (magNumber == usNum.toInt()) {
                        _greetingText.value = getMessage(R.string.win)
                        _userNumberFieldVisible.value = false
                        _btnTryVisible.value = false
                        _btnAgainVisible.value = true
                        _smilePicture.value = Smiles.WIN
                    }
                    if (magNumber > usNum.toInt()) {
                        _greetingText.value =
                            getMessage(R.string.less, attempts.value)
                        _userNumberFieldVisible.value = true
                        _userNumberText.value = ""
                        _btnTryVisible.value = true
                        _btnAgainVisible.value = false
                        _smilePicture.value = Smiles.SAD
                    }
                    if (magNumber < usNum.toInt()) {
                        _greetingText.value =
                            getMessage(R.string.bigger, attempts.value)
                        _userNumberFieldVisible.value = true
                        _userNumberText.value = ""
                        _btnTryVisible.value = true
                        _btnAgainVisible.value = false
                        _smilePicture.value = Smiles.SAD
                    }

                    if (attempts.value?.toInt() == 0) {
                        _greetingText.value = getMessage(R.string.loose, magNumber)
                        _userNumberFieldVisible.value = false
                        _btnTryVisible.value = false
                        _btnAgainVisible.value = true
                        _smilePicture.value = Smiles.CRY
                    }

                    Log.e("se", "${attempts.value} - attempts def -compare")
                }
            }

        } catch (e: NumberFormatException) {
            Log.e("error", "Fail format")
        }
        attempts.value = attempts.value?.minus(1)
    }


    private fun getMessage(text: Int): String {
        return getApplication<Application>().resources.getString(text)
    }

    private fun getMessage(text: Int, attempts: Int?): String {
        return getApplication<Application>().resources.getString(text, attempts)
    }

}