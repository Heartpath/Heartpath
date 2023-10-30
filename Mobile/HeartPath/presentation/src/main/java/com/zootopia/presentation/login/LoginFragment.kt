package com.zootopia.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "LoginFragment_HeartPath"
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind,
    R.layout.fragment_login
){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        Glide
            .with(this)
            .load(R.drawable.animation_bird_on_earth)
            .into(binding.imageviewBirdOnEarth)
    }

}