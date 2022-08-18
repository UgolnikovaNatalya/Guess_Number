package com.example.guessnumbernav.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.guessnumbernav.R
import com.example.guessnumbernav.ViewModels.SharedViewModel
import com.example.guessnumbernav.databinding.FragmentCompBinding

class ComputerFragment : Fragment(){

    //bind
    private lateinit var vb : FragmentCompBinding
    private val viewModel : SharedViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //model

        vb = FragmentCompBinding.inflate(inflater, container, false)
        val view = vb.root

        viewModel.loadComp()

        vb.compBtnGenerate.setOnClickListener {
            viewModel.compGenerateNumber()
        }

        vb.compStartGameBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_computer_to_game)
        }

        //greet text and visibility
        viewModel.greetingText.observe(viewLifecycleOwner){
            vb.compGreeting.text = it
        }

        viewModel.greetingVisible.observe(viewLifecycleOwner){
            vb.compGreeting.isVisible = it
        }

        //generate button visibility
        viewModel.generateButtonVisible.observe(viewLifecycleOwner){
            vb.compBtnGenerate.isVisible = it
        }
        //play button visibility
        viewModel.playButtonVisible.observe(viewLifecycleOwner){
            vb.compStartGameBtn.isVisible = it
        }
        //progressbar button visibility
        viewModel.progressbarVisible.observe(viewLifecycleOwner){
            vb.compProgBar.isVisible = it
        }
        return view
    }
}