package com.zootopia.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val homeViewModel: HomeViewModel by activityViewModels()
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickEvent()
        initAnimation()
        initCollecter()
        initData()
    }

    private fun initData(){
        homeViewModel.getMainCharacter()
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
        buttonMyPage.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_myPageFragment)
        }

        buttonWriteLetter.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_selectUserFragment)
        }

        buttonCharacterEncyclopedia.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_characterEncyclopediaFragment)
        }
    }

    private fun initView() = with(binding) {
    
    }

    private fun initAnimation() = with(binding) {
        var anim_up_and_down: Animation =
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shaking_animation_up_and_down_2000
            )
        imageNote1.startAnimation(anim_up_and_down)
        imageNote2.startAnimation(anim_up_and_down)
        imageHeart.startAnimation(anim_up_and_down)

        buttonWriteLetter.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shaking_animation_down_and_up_5000
            )
        )
        buttonLetterStorage.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shaking_animation_down_and_up_5200
            )
        )
        buttonMap.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shaking_animation_down_and_up_5400
            )
        )
        buttonLetterList.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shaking_animation_down_and_up_5800
            )
        )
    }

    private fun initCollecter() = with(binding) {
        lifecycleScope.launch {
            homeViewModel.mainCharacter.collectLatest {
                if(it != null){
                    Glide
                        .with(root)
                        .load(it.imagePath)
                        .error(R.drawable.image_bird_silhouette)
                        .into(buttonCharacterEncyclopedia)
                }
            }
        }
    }
}
