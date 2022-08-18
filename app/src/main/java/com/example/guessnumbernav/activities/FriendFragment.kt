package com.example.guessnumbernav.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.guessnumbernav.R
import com.example.guessnumbernav.ViewModels.SharedViewModel
import com.example.guessnumbernav.databinding.FragmentFriendBinding

class FriendFragment : Fragment() {

    private lateinit var vb: FragmentFriendBinding
    private val viewModel : SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        vb = FragmentFriendBinding.inflate(inflater, container, false)
        val view = vb.rootFriend

        viewModel.loadFriend()

        vb.friendStartGameBtn.setOnClickListener {
//            viewModel.startGame(vb.friendNumber.text.toString())
            findNavController().navigate(R.id.action_friend_to_game)
        }

        viewModel.greetingVisible.observe(viewLifecycleOwner){
            vb.friendGreeting.isVisible = it
        }

        viewModel.greetingText.observe(viewLifecycleOwner){
            vb.friendGreeting.text = it
        }

        viewModel.friendNumberVisible.observe(viewLifecycleOwner){
            vb.friendNumber.isVisible = it
        }

        viewModel.friendNumberText.observe(viewLifecycleOwner){ number ->
            vb.friendNumber.setText(number)
        }

        viewModel.playBtnVisible.observe(viewLifecycleOwner){
            vb.friendNumber.isVisible = it
        }

        viewModel.linearVisible.observe(viewLifecycleOwner){
            vb.friendLinear.isVisible = it
        }

//        viewModel.toast.observe(viewLifecycleOwner) {
//            it.let {
//                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
//            }
//        }

        return view
    }
}