package com.example.guessnumbernav.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.guessnumbernav.R
import com.example.guessnumbernav.ViewModels.StartViewModel
import com.example.guessnumbernav.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    //bind
    private lateinit var _vb: FragmentStartBinding
    private val vb get() = _vb

    //model
    private val viewModel : StartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _vb = FragmentStartBinding.inflate(inflater, container, false)
        val view = vb.startRoot

        viewModel.load()

        //go to comp frag
        vb.btnComputer.setOnClickListener {
            view.findNavController().navigate(R.id.action_start_to_computer)
        }

        //go to friend frag
        vb.btnFriend.setOnClickListener {
            view.findNavController().navigate(R.id.action_start_to_friend)
        }

        viewModel.contentVisible.observe(viewLifecycleOwner){
            vb.btnComputer.isVisible = it
            vb.btnFriend.isVisible = it
            vb.choosePlayTxt.isVisible = it
        }

        viewModel.greet.observe(viewLifecycleOwner){
            vb.choosePlayTxt.text = it
        }

        return view
    }
}