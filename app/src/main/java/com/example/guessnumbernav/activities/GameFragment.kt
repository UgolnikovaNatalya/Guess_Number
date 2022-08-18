package com.example.guessnumbernav.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.guessnumbernav.ViewModels.SharedViewModel
import com.example.guessnumbernav.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var _vb: FragmentGameBinding
    private val vb get() = _vb

    private val viewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _vb = FragmentGameBinding.inflate(inflater, container, false)
        val view = vb.rootGame

        viewModel.loadPlay()

        vb.playBtnTry.setOnClickListener {
            viewModel.playGame()
        }
//----------------------------------------------------------------------
        viewModel.greetingVisible.observe(viewLifecycleOwner)
        {
            vb.playGreet.isVisible = it
        }

        viewModel.greetingText.observe(viewLifecycleOwner){
            vb.playGreet.text = it
        }

        viewModel.btnTryVisibility.observe(viewLifecycleOwner){
            vb.playBtnTry.isVisible = it
        }

        viewModel.againBtnVisibility.observe(viewLifecycleOwner) {
            vb.playBtnAgain.isVisible = it
        }

        viewModel.tryNumberFieldVisible.observe(viewLifecycleOwner){
            vb.playTryNumber.isVisible = it
        }

        viewModel.tryNumberFieldText.observe(viewLifecycleOwner){ number ->
            vb.playTryNumber.setText(number)
        }

        return view
    }
}