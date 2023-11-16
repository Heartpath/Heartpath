package com.zootopia.presentation.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentSignUpBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.log

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::bind,
    R.layout.fragment_sign_up
) {
    private lateinit var mainActivity: MainActivity
    private val loginViewModel: LoginViewModel by activityViewModels()
    private lateinit var toast: Toast
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClickEvent()
        initAnimation()
    }

    private fun initView() = with(binding) {
        // 중복확인 버튼 중복 여부 확인에 따라 UI 변경
        buttonIdCheck.apply {
            lifecycleScope.launch {
                loginViewModel.checkIdDone.collect { isCheckDone ->
                    if (isCheckDone) {
                        // 완료된 상태라면
                        text = CHECKDONE
                        setTextColor(ContextCompat.getColor(context, R.color.DarkPink))
                        background = ContextCompat.getDrawable(
                            context,
                            R.drawable.custom_signup_check_done_button_background
                        )
                    } else {
                        // 아직 확인 필요한 경우
                        text = CHECKUNDONE
                        setTextColor(ContextCompat.getColor(context, R.color.DarkGray))
                        background = ContextCompat.getDrawable(
                            context,
                            R.drawable.custom_signup_check_button_background
                        )
                    }
                }
            }
        }

        // 아이디 입력 edit text
        edittextNewId.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    imagebuttonIdInputCancel.visibility = View.GONE
                }

                override fun onTextChanged(
                    sequence: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (sequence != null) {
                        // 만약에 값 없으면 X 버튼 gone, 값 있으면 띄우기
                        if (sequence.isNotEmpty()) {
                            imagebuttonIdInputCancel.visibility = View.VISIBLE
                        } else {
                            imagebuttonIdInputCancel.visibility = View.GONE
                        }

                        // 입력 값 갱신
                        loginViewModel.setNewId(sequence.toString())
                        // 입력 값 수정하면 중복확인 false 처리
                        loginViewModel.setCheckIdDone(value = false)
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })

            // 입력한 키 확인 event
            setOnKeyListener { view, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    buttonIdCheck.performClick()
                    keyboardEvent() // 엔터 누르면 키보드 내리기
                    setCancelButton()// 키보드 내리면 x button도 gone 시키기
                    true
                }
                false
            }

            // focus 확인 event
            setOnFocusChangeListener { view, gainFocus ->
                if (gainFocus) {
                    // focus를 받았을 때 값 있으면 x 버튼 visible 처리
                    setButtonByFocus()
                }
            }
        }
    }

    private fun initAnimation() = with(binding) {   // 회원가입 새 애니메이션 적용
        val animDownUp: Animation = AnimationUtils.loadAnimation(
            mainActivity,
            R.anim.shaking_animation_down_and_up_3000
        )
        imageviewWelcomeBird.startAnimation(animDownUp)
    }

    private fun initClickEvent() = with(binding) {
        // X 버튼
        imagebuttonIdInputCancel.setOnClickListener {
            // x 버튼 누르면 입력한 값 지우기
            Log.d(TAG, "initClickEvent: delete button clicked")
            edittextNewId.setText("")
        }

        // 회원 가입 버튼
        buttonSignupAccept.setOnClickListener {
            Log.d(TAG, "initClickEvent: clicked accept")
            if (loginViewModel.newId.value.isNullOrEmpty()) signupToast(message = getString(R.string.toast_message_signup_check_id_plz))
            else {
                Log.d(TAG, "initClickEvent: clicked")
                val isCheckDone = loginViewModel.checkIdDone.value
                Log.d(TAG, "회원가입 버튼 $isCheckDone")
                if (isCheckDone) { // 아이디 중복 체크 확인 되었는 상태
                    viewLifecycleOwner.lifecycleScope.launch {
                        loginViewModel.signUp()
                        loginViewModel.signupResult.collectLatest { result ->
                            if (result.accessToken != "") {
                                signupToast(message = getString(R.string.toast_message_signup_done))
                                Log.d(TAG, "initClickEvent: settoken 재호출")
                                loginViewModel.setToken(result)
                                loginViewModel.storeToken()
                                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                    // 토큰 값 다 저장했으면 home으로 이동
                                    loginViewModel.setTokenResult.collectLatest { done ->
                                        if (done) findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                                    }
                                }
                            } else {
                                signupToast(message = getString(R.string.toast_message_signup_fail))
                            }
                        }
                    }
                } else {
                    // 아이디 중복 체크 요구
                    signupToast(message = getString(R.string.toast_message_signup_check_id_plz))
                }
            }
        }
        // 중복 확인 버튼
        buttonIdCheck.setOnClickListener {
            if (loginViewModel.newId.value == "") {
                // 빈 값은 처리하지 않음
                signupToast(message = getString(R.string.toast_message_signup_check_id_enter_letter))
            } else {
                loginViewModel.duplicateCheckId()
                setCancelButton()
                lifecycleScope.launch {
                    loginViewModel.checkIdResult.collectLatest { result ->
                        Log.d(TAG, "initClickEvent: 중복 확인 결과 $result")
                        if (!result) {
                            // 사용 가능
                            signupToast(message = getString(R.string.toast_message_signup_check_id_can_use))
                            loginViewModel.setCheckIdDone(value = true)
                        } else {
                            // 사용 불가능
                            signupToast(message = getString(R.string.toast_message_signup_check_id_cant_use))
                            loginViewModel.setCheckIdDone(value = false)
                        }
                    }
                }
            }

        }
    }

    private fun setCancelButton() = with(binding) {
        imagebuttonIdInputCancel.visibility = View.GONE
    }

    private fun keyboardEvent() = with(binding) {
        val inputMethodManager =
            mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(edittextNewId.windowToken, 0)
    }

    private fun setButtonByFocus() = with(binding) {
        // focus를 받았을 때 값 있으면 x 버튼 visible 처리
        lifecycleScope.launch {
            loginViewModel.newId.collect { value ->
                if (value.isNotEmpty()) imagebuttonIdInputCancel.visibility = View.VISIBLE
            }
        }
    }

    private fun signupToast(message: String) {
        toast = Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    companion object {
        private const val TAG = "SignUpFragment_HP"
        private const val CHECKDONE = "확인완료"
        private const val CHECKUNDONE = "중복확인"
    }
}