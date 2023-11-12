package com.zootopia.presentation.readletter

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.DialogLetterAddFriendBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ReadLetterAddFriendDialog : DialogFragment() {
    private lateinit var binding: DialogLetterAddFriendBinding
    private val readLetterViewModel: ReadLetterViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true // 화면 밖에 클릭하면 dismiss 되도록
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogLetterAddFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonFriendAddCancel.setOnClickListener {
                dismiss()
            }
            buttonFriendAddAccept.setOnClickListener {
                readLetterViewModel.addAsFriend()
                dismiss()
            }
            lifecycleScope.launch {
                readLetterViewModel.readLetterResult.collect {letter ->
                    textviewAddFriendWhoIsNotFriend.text = letter.sender + "님은 친구가 아닙니다."
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val parentWidth = resources.displayMetrics.widthPixels
        val size = parentWidth - (parentWidth / 10)
        dialog?.let {dialog ->
            // dialog 크기 동적으로 주기
            dialog.window?.setLayout(size, ViewGroup.LayoutParams.WRAP_CONTENT)
            // dialog background 동적으로 주기
            dialog.window?.setBackgroundDrawableResource(R.drawable.custom_round_dialog_view)
            // 다이얼로그를 하단에 표시
            dialog.window?.setGravity(Gravity.BOTTOM)
        }
    }

    companion object {
        const val TAG = "ReadLetterAddFriendDial"
    }
}