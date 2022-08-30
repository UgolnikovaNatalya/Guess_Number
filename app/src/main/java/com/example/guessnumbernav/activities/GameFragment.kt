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

class GameFragment : Fragment() {

    //bind
    private lateinit var _vb: FragmentGameBinding
    private val vb get() = _vb

    //viewModel
    private val viewModel: GameViewModel by viewModels()

    //restore magicNumber
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val t = savedInstanceState?.getInt("l")
        viewModel.magicNumber.value = t
    }

    //-------- o n C r e a t e V i e w -------------------
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.e("", "onCreateView f")

        _vb = FragmentGameBinding.inflate(inflater, container, false)
        val view = vb.rootGame

        setFragmentResultListener(KEY_MAGIC_NUMBER) { key, bundle ->
            val res = bundle.getInt(KEY_MAGIC_BUNDLE)
            viewModel.magicNumber.value = res
        }

        setFragmentResultListener(KEY_USER_NUMBER) { key, bundle ->
            val friendNumber = bundle.getString(KEY_USER_BUNDLE)
            viewModel.magicNumber.value = friendNumber?.toInt()
        }

        //focusing
        vb.playTryNumber.requestFocus()

        viewModel.load()

        //* * * * * Game * * * *
        vb.playBtnTry.setOnClickListener {
            viewModel.userNumber.value = vb.playTryNumber.text.toString()
            viewModel.compareNumbers()
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

        viewModel.smilePicture.observe(viewLifecycleOwner) { picture ->
            val smile = when (picture) {
                Smiles.WIN -> R.drawable.win
                Smiles.SMILE -> R.drawable.smile
                Smiles.SAD -> R.drawable.sad_smile
                Smiles.CRY -> R.drawable.cry
            }
            vb.playSmile.setImageResource(smile)
        }

        viewModel.smileVisible.observe(viewLifecycleOwner) {
            vb.playSmile.isVisible = it
        }

        viewModel.toast.observe(viewLifecycleOwner) { toast ->
            val message = when (toast) {
                Toasts.EMPTY -> R.string.enter_number
                Toasts.BIGGER -> R.string.number_is_bigger
                Toasts.ERROR -> R.string.error_str_in_numb
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
                .show()
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
        return view
    }

    //save magicNumber
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("l", viewModel.magicNumber.value?.toInt() ?: -1)
    }
}