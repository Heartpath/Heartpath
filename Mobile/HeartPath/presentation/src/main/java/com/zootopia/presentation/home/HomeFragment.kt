package com.zootopia.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
        initAnimation()
    }

    private fun initView() = with(binding) {

    }

    private fun initAnimation() {
        var anim_up_and_down: Animation =
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shaking_animation_up_and_down_2000
            )
        binding.imageNote1.startAnimation(anim_up_and_down)
        binding.imageNote2.startAnimation(anim_up_and_down)
        binding.imageHeart.startAnimation(anim_up_and_down)

        binding.buttonWriteLetter.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shaking_animation_down_and_up_2000
            )
        )
        binding.buttonLetterStorage.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shaking_animation_down_and_up_2400
            )
        )
        binding.buttonMap.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shaking_animation_down_and_up_2800
            )
        )
        binding.buttonLetterList.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shaking_animation_down_and_up_3200
            )
        )
    }
}
