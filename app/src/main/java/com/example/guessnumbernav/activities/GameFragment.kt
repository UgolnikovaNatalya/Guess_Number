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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.guessnumbernav.R
import com.example.guessnumbernav.ViewModels.GameViewModel
import com.example.guessnumbernav.ViewModels.Smiles
import com.example.guessnumbernav.ViewModels.Toasts
import com.example.guessnumbernav.databinding.FragmentGameBinding

private const val A = "A"
private const val MN = "MN"
private const val UN = "UN"

class GameFragment : Fragment() {

    //bind
    private lateinit var _vb: FragmentGameBinding
    private val vb get() = _vb

    //viewModel
    private val viewModel: GameViewModel by viewModels()

    //-------- o n C r e a t e V i e w -------------------
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.e("", "onCreateView f")

        _vb = FragmentGameBinding.inflate(inflater, container, false)

        return vb.rootGame
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val attempts = savedInstanceState?.getInt(A)
        val usrNumber = savedInstanceState?.getInt(UN)
        val magicNumber = when {
            arguments?.getInt(KEY_FRIEND_NUMBER) == 0 -> arguments?.getInt(KEY_COMP_NUMBER)
            else -> arguments?.getInt(KEY_FRIEND_NUMBER)
        }


        Log.e("VB", "getting from args $magicNumber. bundle: $arguments")

        //focusing
        vb.playTryNumber.requestFocus()

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
            val message = when (toast!!) {
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

        viewModel.load(magicNumber, attempts, usrNumber)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MN, viewModel.magicNumber.value?.toInt() ?: -1)
        outState.putInt(A, viewModel.attemptsField.value?.toInt() ?: -1)
        viewModel.userNumber.value.takeIf { it != null }?.toInt()?.let { outState.putInt(UN, it) }
    }
}