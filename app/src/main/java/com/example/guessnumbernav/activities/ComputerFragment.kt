package com.example.guessnumbernav.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.guessnumbernav.R
import com.example.guessnumbernav.ViewModels.ComputerViewModel
import com.example.guessnumbernav.databinding.FragmentCompBinding

const val KEY_MAGIC_NUMBER = "key"
const val KEY_MAGIC_BUNDLE = "bundle"
const val KEY_ATTEMPT = "att"
const val KEY_ATTEMPT_BUNDLE = "attempt"
const val DEFAULT_ATTEMPTS = 7


class ComputerFragment() : Fragment(){

    //bind
    private lateinit var vb : FragmentCompBinding
    private val viewModel : ComputerViewModel by viewModels()

    private var number = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //binding
        vb = FragmentCompBinding.inflate(inflater, container, false)
        val view = vb.root

        viewModel.load()

        //generate number
        vb.compBtnGenerate.setOnClickListener {
            number = viewModel.generateNumber()

            setFragmentResult(KEY_MAGIC_NUMBER, bundleOf(KEY_MAGIC_BUNDLE to number))
            setFragmentResult(KEY_ATTEMPT, bundleOf(KEY_ATTEMPT_BUNDLE to DEFAULT_ATTEMPTS))
        }

        //start game
        vb.compStartGameBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_computer_to_game)
        }

//  ********************* O B S E R V E R S**************************
        viewModel.greetingText.observe(viewLifecycleOwner){
            vb.compGreeting.text = it
        }

        viewModel.greetingVisible.observe(viewLifecycleOwner){
            vb.compGreeting.isVisible = it
        }

        viewModel.generateButtonVisible.observe(viewLifecycleOwner){
            vb.compBtnGenerate.isVisible = it
        }

        viewModel.playButtonVisible.observe(viewLifecycleOwner){
            vb.compStartGameBtn.isVisible = it
        }

        viewModel.progressbarVisible.observe(viewLifecycleOwner){
            vb.compProgBar.isVisible = it
        }
        return view
    }

}