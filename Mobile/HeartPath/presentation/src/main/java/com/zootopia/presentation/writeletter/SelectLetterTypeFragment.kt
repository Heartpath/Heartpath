package com.zootopia.presentation.writeletter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentSelectLetterTypeBinding
import com.zootopia.presentation.util.LetterType

class SelectLetterTypeFragment : BaseFragment<FragmentSelectLetterTypeBinding>(
    FragmentSelectLetterTypeBinding::bind,
    R.layout.fragment_select_letter_type
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val writeLetterViewModel: WriteLetterViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initClickListener()
    }

    fun initClickListener() = with(binding) {
        binding.linealayoutHandWrite.setOnClickListener {
            val action =
                SelectLetterTypeFragmentDirections.actionSelectLetterTypeFragmentToSelectLetterPaperFragment(
                    LetterType.HAND_WRITE
                )
            findNavController().navigate(action)
        }
        binding.linearlayoutTypingWrite.setOnClickListener {
            val action =
                SelectLetterTypeFragmentDirections.actionSelectLetterTypeFragmentToSelectLetterPaperFragment(
                    LetterType.TYPING_WRITE
                )
            findNavController().navigate(action)
        }
    }

}