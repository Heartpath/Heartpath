package com.zootopia.presentation.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentSignUpBinding

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::bind,
    R.layout.fragment_sign_up
) {
    private var originalImageColorFilter: Int = ContextCompat.getColor(requireContext(), R.color.Gray)
    private var isButtonPressed = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAnimation()
    }
    private fun initView() = with(binding){
        // 만약에 값 없으면 X 버튼 gone, 값 있으면 띄우기
    }
    private fun initAnimation() = with(binding) {   // 회원가입 새 애니메이션 적용
        val animDownUp : Animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.shaking_animation_down_and_up_3000
        )
        imageviewWelcomeBird.startAnimation(animDownUp)
    }
    private fun initClickEvent() = with(binding) {
        imagebuttonIdInputCancel.setOnClickListener {
            // TODO: 값 다 지우기
        }
        buttonIdCheck.setOnClickListener {
            // TODO: 아이디 중복 확인
        }
        buttonSignupAccept.setOnClickListener {
            // TODO: 회원 가입하기
        }
    }
}