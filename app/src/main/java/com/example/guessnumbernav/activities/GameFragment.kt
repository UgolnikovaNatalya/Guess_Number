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

//        val magicNumber = savedInstanceState?.getInt(MN)
        val attempts = savedInstanceState?.getInt(A)
        val magicNumber = arguments?.getInt("magicNumber")

        Log.d("VB", "getting from args $magicNumber. bundle: $arguments")

        // A -> B
        // Bundle

        // A <- B
        // + setFragmentResultListener

//        setFragmentResultListener(KEY_MAGIC_NUMBER) { key, bundle ->
//            val res = bundle.getInt(KEY_MAGIC_BUNDLE)
//            viewModel.magicNumber.value = res
//        }

//        setFragmentResultListener(KEY_USER_NUMBER) { key, bundle ->
//            val friendNumber = bundle.getString(KEY_USER_BUNDLE)
//            Log.d("VB", "magicnumber setting")
//            viewModel.magicNumber.value = friendNumber?.toInt()
//        }


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

        viewModel.load(magicNumber, attempts)
    }

    // 1. onSaveInstanceState - если твою апп прибьет система
    // 2. viewModel
    // 3. SP
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MN, viewModel.magicNumber.value?.toInt() ?: -1)
        outState.putInt(A, viewModel.attemptsField.value?.toInt() ?: -1)
    }
}