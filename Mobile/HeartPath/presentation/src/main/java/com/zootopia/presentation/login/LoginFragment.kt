package com.zootopia.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentLoginBinding
import com.zootopia.presentation.util.clickAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
            it.clickAnimation(lifeCycleOwner = viewLifecycleOwner)
//            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            lifecycleScope.launch {
                loginViewModel.loginByKakao(context = requireContext())
                // 로그인 결과에 따라 동작 TODO: 분기 다시 처리
                loginViewModel.loginResult.collect { result ->
                    if(result.accessToken != "") {
                        // 성공 -> home으로 이동
                        Log.d(TAG, "initClickEvent: ${result.accessToken}")
//                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        // 성공 못함 -> 회원가입 시키기
//                        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
                        Log.d(TAG, "initClickEvent: ${result.accessToken}")
                    }
                }
            }
        }
    }
    private fun initView() = with(binding) {
        Glide
            .with(this@LoginFragment)
            .load(R.drawable.animation_bird_on_earth)
            .into(imageviewBirdOnEarth)
    }

}