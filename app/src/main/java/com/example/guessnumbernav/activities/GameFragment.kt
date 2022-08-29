package com.example.guessnumbernav.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.guessnumbernav.R
import com.example.guessnumbernav.ViewModels.GameViewModel
import com.example.guessnumbernav.ViewModels.Smiles
import com.example.guessnumbernav.ViewModels.Toasts
import com.example.guessnumbernav.databinding.FragmentGameBinding
import java.lang.ClassCastException
import java.lang.NullPointerException

const val KEY_USER_NUMBER = "number"
const val KEY_MAGIC_NUM = "mn"

class GameFragment : Fragment() {

    //bind
    private lateinit var _vb: FragmentGameBinding
    private val vb get() = _vb

    //viewModel
    private val viewModel: GameViewModel by viewModels()

    // логика на фрагменте, поле убрать
    private var magicNumber = -11


    //for magic number state after rotation and pause


    // onCreate убрать

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel.getMagicNUmber(savedInstanceState?.getInt(KEY_MAGIC_NUM) ?: -33)
//
//        Log.e("m", "onCreate")
//
//    }

    //-------- o n C r e a t e V i e w -------------------
    // Здесь происходит ТОЛЬКО inflating view
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        magicNumber = savedInstanceState?.getInt(KEY_MAGIC_NUM) ?: -33

        Log.e("m", "onCreateView")

        _vb = FragmentGameBinding.inflate(inflater, container, false)

        return vb.rootGame
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val number = savedInstanceState?.getString(KEY_USER_NUMBER) ?: "225"

        vb.playTryNumber.setText(number)

        setFragmentResultListener(KEY_MAGIC_NUMBER) { key, bundle ->

            // Почему используются два одинаковых ключа?
            // Ключи должны быть разные
            val res = bundle.getInt(KEY_MAGIC_BUNDLE)
            val friendNumber = bundle.getString(KEY_MAGIC_BUNDLE)

            magicNumber = if (friendNumber != null) {
                viewModel.getMagicNUmber(friendNumber.toInt())
                friendNumber.toInt()
            } else {
                viewModel.getMagicNUmber(res)
                res
            }

            Log.e("m", "$magicNumber - magic number setFragRes")
            Log.e("m", "onCreateView")
        }

        //focusing
        vb.playTryNumber.requestFocus()

        viewModel.load()

        //* * * * * Game * * * *
        vb.playBtnTry.setOnClickListener {
            viewModel.compareNumbers(vb.playTryNumber.text.toString())
        }

        //* * * * * Again * * * * *
        vb.playBtnAgain.setOnClickListener {
            findNavController().popBackStack()
        }

//************************ O B S E R V E R S ****************************
        viewModel.greetingVisible.observe(viewLifecycleOwner) {
            vb.playGreet.isVisible = it
        }

        viewModel.greetingText.observe(viewLifecycleOwner) {
            vb.playGreet.text = it
        }

        viewModel.btnTryVisible.observe(viewLifecycleOwner) {
            vb.playBtnTry.isVisible = it
        }

        viewModel.btnAgainVisible.observe(viewLifecycleOwner) {
            vb.playBtnAgain.isVisible = it
        }

        viewModel.userNumberFieldVisible.observe(viewLifecycleOwner) {
            vb.playTryNumber.isVisible = it
        }

        viewModel.userNumberText.observe(viewLifecycleOwner) {
            vb.playTryNumber.setText(it)
        }

        // Обновил, сравни текущий вариант со старым
        viewModel.smilePicture.observe(viewLifecycleOwner) { picture ->
            val res = when (picture) {
                Smiles.WIN -> R.drawable.win
                Smiles.SMILE -> R.drawable.smile
                Smiles.SAD -> R.drawable.sad_smile
                Smiles.CRY -> R.drawable.cry
            }

            vb.playSmile.setImageResource(res)
        }

        viewModel.smileVisible.observe(viewLifecycleOwner) {
            vb.playSmile.isVisible = it
        }

        // Обновил, сравни текущий вариант со старым
        viewModel.toast.observe(viewLifecycleOwner) { toast ->
            val text = when (toast) {
                Toasts.EMPTY -> R.string.enter_number
                Toasts.BIGGER -> R.string.number_is_bigger
                Toasts.ERROR -> R.string.error_str_in_numb
            }

            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
        }

        viewModel.keyboardVisible.observe(viewLifecycleOwner) { state ->
            when (state) {
                true -> {
                    val imm =
                        view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(vb.playTryNumber, 0)
                }
                false -> {
                    val imm =
                        view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(vb.playTryNumber.windowToken, 0)
                }
            }
        }

        viewModel.scrollView.observe(viewLifecycleOwner) {
            vb.scroll.isVisible = it
        }

        Log.e(",", "${vb.playTryNumber.text} try number text")
        Log.e("m", "onViewCreated")
    }

    // Если поправить magicNumber в GameViewModel и сделать его LiveData не придется сохранять состояние
    // как итог меньше багов и логика разруливается в одном месте

    // Тоже самое касается KEY_USER_NUMBER
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_MAGIC_NUM, magicNumber)
        outState.putString(KEY_USER_NUMBER, vb.playTryNumber.text.toString())

        Log.e("m", "onSaveInstanceState")
        Log.e("m", "$magicNumber - mg onSaveInstanceState")
    }

}