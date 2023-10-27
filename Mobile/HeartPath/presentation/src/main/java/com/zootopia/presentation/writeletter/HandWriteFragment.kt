package com.zootopia.presentation.writeletter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentHandWriteBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HandWriteFragment : BaseFragment<FragmentHandWriteBinding>(
    FragmentHandWriteBinding::bind,
    R.layout.fragment_hand_write
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

        initCollecter()
    }

    private fun initCollecter() = with(binding) {
        lifecycleScope.launch {
            writeLetterViewModel.selectedLetterPaperUrl.collectLatest {
                Glide.with(mainActivity).load(it).into(imageviewLetterPaper)
            }
        }

    }

}