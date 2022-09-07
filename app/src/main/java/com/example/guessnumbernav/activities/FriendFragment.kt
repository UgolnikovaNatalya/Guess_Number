package com.example.guessnumbernav.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
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
import com.example.guessnumbernav.ViewModels.FriendViewModel
import com.example.guessnumbernav.ViewModels.Toasts
import com.example.guessnumbernav.databinding.FragmentFriendBinding
const val KEY_FRIEND_NUMBER = "KEY_USER_NUMBER"

class FriendFragment : Fragment() {

    private lateinit var vb: FragmentFriendBinding
    private val viewModel: FriendViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        //binding
        vb = FragmentFriendBinding.inflate(inflater, container, false)
        val view = vb.rootFriend

        val bundle = Bundle()

        //focus
        vb.friendNumber.requestFocus()

        //load fragment
        viewModel.load()

        //start game
        vb.friendStartGameBtn.setOnClickListener {
            val friendNumber = vb.friendNumber.text.toString()
            bundle.putInt(KEY_FRIEND_NUMBER, friendNumber.toInt())

            viewModel.startGame(friendNumber)
        }


//  ********** O B S E R V E R S ****************
        viewModel.greetingVisible.observe(viewLifecycleOwner) {
            vb.friendGreeting.isVisible = it
        }

        viewModel.greetingText.observe(viewLifecycleOwner) {
            vb.friendGreeting.text = it
        }

        viewModel.friendNumberVisible.observe(viewLifecycleOwner) {
            vb.friendNumber.isVisible = it
        }

        viewModel.friendNumberText.observe(viewLifecycleOwner) { number ->
            vb.friendNumber.setText(number)
        }

        viewModel.playBtnVisible.observe(viewLifecycleOwner) {
            vb.friendNumber.isVisible = it
        }

        viewModel.linearVisible.observe(viewLifecycleOwner) {
            vb.friendLinear.isVisible = it
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
                    imm.showSoftInput(vb.friendNumber, 0)
                }
                false -> {
                    val imm =
                        view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(vb.friendNumber.windowToken, 0)
                }
            }
        }

        viewModel.nextFragment.observe(viewLifecycleOwner) { state ->

            when (state) {
                true -> {
                    findNavController().navigate(R.id.action_friend_to_game, bundle)
                }
                false -> {}
            }
        }

        return view
    }
}