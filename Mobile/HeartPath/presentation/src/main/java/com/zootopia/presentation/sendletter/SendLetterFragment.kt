package com.zootopia.presentation.sendletter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentSendLetterBinding

class SendLetterFragment :
    BaseFragment<FragmentSendLetterBinding>(FragmentSendLetterBinding::bind, R.layout.fragment_send_letter) {
    
    private lateinit var mainActivity: MainActivity
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
    }
    
    private fun initView() = with(binding){
    
    }
}