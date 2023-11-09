package com.zootopia.presentation.readletter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentReadLetterBinding

class ReadLetterFragment : BaseFragment<FragmentReadLetterBinding>(
    FragmentReadLetterBinding::bind,
    R.layout.fragment_read_letter,
) {
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        // toolbar 설정
        toolbarHeartpathReadLetter.apply {
            textviewCurrentPageTitle.text = getString(R.string.toolbar_read_letter_title)
            imageviewBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }


    }

}