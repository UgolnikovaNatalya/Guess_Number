package com.example.guessnumbernav.ViewModels

import android.app.Application
import android.util.Log
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
    private val _attempts = MutableLiveData<Int>()

    val greetingVisible: LiveData<Boolean> = _greetingVisible
    val greetingText: LiveData<String> = _greetingText
    val smileVisible: LiveData<Boolean> = _smileVisible
    val smilePicture: LiveData<Smiles> = _smilePicture
    val userNumberFieldVisible: LiveData<Boolean> = _userNumberFieldVisible
    val userNumberText: LiveData<String> = _userNumberText
    val btnTryVisible: LiveData<Boolean> = _btnTryVisible
    val btnAgainVisible: LiveData<Boolean> = _btnAgainVisible
    val toast: LiveData<Toasts> = _toast
    val keyboardVisible: LiveData<Boolean> = _keyBoardVisible
    val attemptsField: LiveData<Int> = _attempts


    val magicNumber: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val userNumber: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    // *************** L O A D ******************
    fun load(magicNumber: Int?, attempts: Int?) {

        _greetingVisible.value = true
        _smileVisible.value = true
        _keyBoardVisible.value = true

        this._attempts.value = attempts ?: ATTEMPTS_DEFAULT

        if (magicNumber != null) {
            this.magicNumber.value = magicNumber
        }

        updateState()
    }

    fun compareNumbers() {
        if (userNumber.value.toString().isEmpty() || userNumber.value.toString().toInt() == 0) {
            _userNumberText.value = ""
            _toast.value = Toasts.EMPTY
            return
        }

        _attempts.value = _attempts.value?.minus(1)

        updateState()

        Log.e("", "${magicNumber.value} - comp number")
        Log.e("", "${userNumberText.value} - text value")
    }

    val string: String? = null

    fun updateState() {

        when {
            magicNumber.value == userNumber.value.takeIf { it != null }?.toInt() -> {
                _greetingText.value = getMessage(R.string.win)
                _userNumberFieldVisible.value = false
                _btnTryVisible.value = false
                _btnAgainVisible.value = true
                _smilePicture.value = Smiles.WIN
                _keyBoardVisible.value = false
                _toast.value
            }
            _attempts.value!!.toInt() < 1 -> {
                    _greetingText.value = getMessage(R.string.loose, magicNumber.value)
                    _userNumberFieldVisible.value = false
                    _btnTryVisible.value = false
                    _btnAgainVisible.value = true
                    _smilePicture.value = Smiles.CRY
                    _keyBoardVisible.value = false
            }

            _attempts.value!!.toInt() == ATTEMPTS_DEFAULT -> {
                _greetingVisible.value = true
                _greetingText.value =
                    getMessage(R.string.play_greet, this._attempts.value)
                _userNumberFieldVisible.value = true
                _btnTryVisible.value = true
                _btnAgainVisible.value = false
                _smilePicture.value = Smiles.SMILE
            }
            _attempts.value!!.toInt() < ATTEMPTS_DEFAULT -> {
                if (magicNumber.value!! > (userNumber.value.takeIf { it != null }?.toInt() ?: 0)){
                    _greetingText.value =
                        getMessage(R.string.less, _attempts.value)

                    _userNumberFieldVisible.value = true
                    _userNumberText.value = ""
                    _btnTryVisible.value = true
                    _btnAgainVisible.value = false
                    _smilePicture.value = Smiles.SAD
                    return
                }
                else {
                    _greetingText.value =
                        getMessage(R.string.bigger, _attempts.value)
                    _userNumberFieldVisible.value = true
                    _userNumberText.value = ""
                    _btnTryVisible.value = true
                    _btnAgainVisible.value = false
                    _smilePicture.value = Smiles.SAD
                }
            }
            _attempts.value!!.toInt() > 0 -> {
                when {
                    userNumber.value.toString().isEmpty() || userNumber.value.toString() == "null" -> {
                        _userNumberText.value = ""
                        _toast.value = Toasts.EMPTY
                        _attempts.value = _attempts.value?.plus(1)
                    }
                }
            }
            userNumber.value.toString().toInt() > MAX_NUMBER -> {
                _userNumberText.value = ""
                _toast.value = Toasts.BIGGER
                _attempts.value = _attempts.value?.plus(1)
            }
            (magicNumber.value ?: 0) > userNumber.value.toString().toInt() -> {
                _greetingText.value =
                    getMessage(R.string.less, _attempts.value)
                _userNumberFieldVisible.value = true
                _userNumberText.value = ""
                _btnTryVisible.value = true
                _btnAgainVisible.value = false
                _smilePicture.value = Smiles.SAD
            }
            (magicNumber.value ?: 0) < userNumber.value.toString().toInt() -> {
                _greetingText.value =
                    getMessage(R.string.bigger, _attempts.value)
                _userNumberFieldVisible.value = true
                _userNumberText.value = ""
                _btnTryVisible.value = true
                _btnAgainVisible.value = false
                _smilePicture.value = Smiles.SAD
            }
        }
    }

    private fun getMessage(text: Int): String {
        return getApplication<Application>().resources.getString(text)
    }

    private fun getMessage(text: Int, attempts: Int?): String {
        return getApplication<Application>().resources.getString(text, attempts)
    }

}