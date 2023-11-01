package com.zootopia.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
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
    private val loginViewModel : LoginViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        initView()
        initClickEvent()
    }

    private fun initClickEvent() = with(binding) {
        imagebuttonLogin.setOnClickListener {
            loginViewModel.loginByKakao(context = requireContext())
        }
    }
    private fun initView() = with(binding) {
        Glide
            .with(this@LoginFragment)
            .load(R.drawable.animation_bird_on_earth)
            .into(binding.imageviewBirdOnEarth)
    }

}