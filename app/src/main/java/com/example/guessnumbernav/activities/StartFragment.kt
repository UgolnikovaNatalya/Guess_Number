package com.example.guessnumbernav.activities

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.guessnumbernav.R
import com.example.guessnumbernav.ViewModels.StartViewModel
import com.example.guessnumbernav.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    //bind
    private var _vb: FragmentStartBinding? = null
    private val vb get() = _vb

    //model
    private val provider : StartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //inflating + bind
        _vb = FragmentStartBinding.inflate(inflater, container, false)
        val view = vb!!.startRoot

        provider.load()

        provider.contentVisible.observe(viewLifecycleOwner){
            vb!!.btnComputer.isVisible = it
            vb!!.btnFriend.isVisible = it
            vb!!.choosePlayTxt.isVisible = it
        }

        //go to comp frag
        vb!!.btnComputer.setOnClickListener {
            view.findNavController().navigate(R.id.action_start_to_computer)
        }

        //go to friend frag
        vb!!.btnFriend.setOnClickListener {
            view.findNavController().navigate(R.id.action_start_to_friend)
        }
        vb!!.choosePlayTxt.text = getText(R.string.choose_play)

        return view
    }
}