package com.zootopia.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentHomeBinding

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {
    
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickEvent()
    }
    
    private fun initClickEvent() = with(binding) {
        buttonLetterList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_receiveLetterFragment)
        }
        
        buttonLetterStorage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sendLetterFragment)
        }
        
        buttonMap.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }
    
    }
}
