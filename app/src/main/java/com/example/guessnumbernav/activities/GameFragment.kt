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

    //--------onCreateView-------------------
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _vb = FragmentGameBinding.inflate(inflater, container, false)
        val view = vb.rootGame

        //get magic number
        setFragmentResultListener(KEY_MAGIC_NUMBER) { key, bundle ->
            val res = bundle.getInt(KEY_MAGIC_BUNDLE)
            val friendNumber = bundle.getString(KEY_MAGIC_BUNDLE)
            if (friendNumber != null) {
                viewModel.getMagicNUmber(friendNumber.toInt())
            } else {
                viewModel.getMagicNUmber(res)
            }
            Log.e("fr", "$res - res GameFragment setFRL")
            Log.e("fr", "$friendNumber - friendNumber GameFragment setFRL")
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

        viewModel.againBtnVisible.observe(viewLifecycleOwner) {
            vb.playBtnAgain.isVisible = it
        }

        viewModel.userNumberFieldVisible.observe(viewLifecycleOwner) {
            vb.playTryNumber.isVisible = it
        }

        viewModel.userNumberText.observe(viewLifecycleOwner) {
            vb.playTryNumber.setText(it)
        }

        viewModel.smilePicture.observe(viewLifecycleOwner) { picture ->
            when (picture) {
                Smiles.WIN -> {
                    vb.playSmile.setImageResource(R.drawable.win)
                }
                Smiles.SMILE -> {
                    vb.playSmile.setImageResource(R.drawable.smile)
                }
                Smiles.SAD -> {
                    vb.playSmile.setImageResource(R.drawable.sad_smile)
                }
                Smiles.CRY -> {
                    vb.playSmile.setImageResource(R.drawable.cry)
                }
            }
        }

        viewModel.smileVisible.observe(viewLifecycleOwner) {
            vb.playSmile.isVisible = it
        }

        viewModel.toast.observe(viewLifecycleOwner) { toast ->
            when (toast) {
                Toasts.EMPTY -> {
                    Toast.makeText(requireContext(), R.string.enter_number, Toast.LENGTH_SHORT)
                        .show()
                }
                Toasts.BIGGER -> {
                    Toast.makeText(requireContext(), R.string.number_is_bigger, Toast.LENGTH_SHORT)
                        .show()
                }
                Toasts.ERROR -> {
                    Toast.makeText(requireContext(), R.string.error_str_in_numb, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        viewModel.keyboardVisible.observe(viewLifecycleOwner) { state ->
            when (state) {
                true -> {
                    val imm =
                        view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
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

//        viewModel.usNumber.observe(viewLifecycleOwner){ number ->
//
//            viewModel.getUserNumber(vb.playTryNumber.text.toString())
//        }

        return view
    }

}