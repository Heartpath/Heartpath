package com.zootopia.presentation.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentLoginBinding
import com.zootopia.presentation.util.clickAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind,
    R.layout.fragment_login
) {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        initCheck()
        initView()
        initClickEvent()
        initCallback()
    }

    private fun initClickEvent() = with(binding) {
        imagebuttonLogin.setOnClickListener {
            it.clickAnimation(lifeCycleOwner = viewLifecycleOwner)
//            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            viewLifecycleOwner.lifecycleScope.launch {
                loginByKakao()
                // 로그인 결과에 따라 동작
                loginViewModel.loginResult.collectLatest { result ->
                    Log.d(TAG, "initClickEvent: $result")
                    if (result.accessToken != "default") {
                        Log.d(TAG, "initClickEvent: 여기 로그인 또 호출")
                        // 성공 -> home으로 이동
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            Log.d(TAG, "initClickEvent: settoken 재호출")
                            loginViewModel.setToken(result)
                            // 토큰 값 다 저장했으면 home으로 이동
                            loginViewModel.setTokenResult.collectLatest { done ->
                                if (done) {
                                    Log.d(TAG, "initClickEvent: 왜 또 호출")
                                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                                }
                            }
                        }
                    } else {
                        // 성공 못함 -> 회원가입 시키기
                        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
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

    private fun loginByKakao(context: Context = mainActivity) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.d(TAG, "loginByKakao: here is callback")
            if (error != null) {
                Log.d(TAG, "로그인 실패 ${error.message}")
            } else if (token != null) {
                Log.d(TAG, "웹으로 카카오 로그인 성공 kakao access token: ${token.accessToken}")
                // Firebase FCM token 가지고 오기
                FirebaseMessaging.getInstance().token.addOnCompleteListener { fcmTask ->
                    Log.d(TAG, "FCM token result: ${fcmTask.result}")
                    if (fcmTask.isSuccessful) {
                        // fcm 토큰 받아오기 성공
                        viewLifecycleOwner.lifecycleScope.launch {
                            // 카카오 access token 값 넣어주기
                            loginViewModel.setKakaoAccessToken(token.accessToken)
                            loginViewModel.setFCMToken(fcmTask.result)
                            loginViewModel.login()
                        }
                    } else {
                        // fcm 토큰 받아 오기 실패
                        return@addOnCompleteListener
                    }
                }

            }
        }
        // 카카오톡 설치 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context = context)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(context = context) { token, error ->
                // 로그인 실패 부분
                if (error != null) {
                    Log.e(TAG, "로그인 실패 $error")
                    // 사용자가 취소
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(
                            context = context,
                            callback = callback
                        ) // 카카오 이메일 로그인
                    }
                }
                // 로그인 성공 부분
                else if (token != null) {
                    Log.d(TAG, "카카오 앱으로 로그인 성공 kakao access token: ${token.accessToken}")
                    FirebaseMessaging.getInstance().token.addOnCompleteListener { fcmTask ->
                        Log.d(TAG, "FCM token result: ${fcmTask.result}")
                        if (fcmTask.isSuccessful) {
                            // fcm 토큰 가지고 오기 성공
                            viewLifecycleOwner.lifecycleScope.launch {
                                // 카카오 access token 값 넣어주기
                                loginViewModel.setKakaoAccessToken(token.accessToken)
                                loginViewModel.setFCMToken(fcmTask.result)
                                loginViewModel.login()
                            }
                        } else {
                            // fcm 토큰 가지고 오기 실패
                            return@addOnCompleteListener
                        }
                    }

                }
            }
        } else {
            Log.d(TAG, "loginByKakao: 웹으로 로그인 시도 시작 ")
            UserApiClient.instance.loginWithKakaoAccount(
                context = context,
                callback = callback
            ) // 카카오 이메일 로그인
            Log.d(TAG, "loginByKakao: 웹으로 로그인 시도 끝")
        }
    }

    private fun initCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로가기로 앱 종료
                activity?.finish()
            }
        }
        mainActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initCheck() {
        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.checkAutoLogin()
            loginViewModel.checkAutoLogin.collect {
                Log.d(TAG, "initCheck: 여기 몇번 불려")
                Log.d(TAG, "initCheck: 값 $it")
                if (it != "") {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: 지금 destroy view 입니다")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: 지금은 destroy입니다")
    }

    companion object {
        private const val TAG = "LoginFragment_HP"
    }
}