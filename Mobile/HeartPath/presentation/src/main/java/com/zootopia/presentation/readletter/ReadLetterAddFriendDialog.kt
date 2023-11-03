package com.zootopia.presentation.readletter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.DialogLetterAddFriendBinding

class ReadLetterAddFriendDialog : DialogFragment() {
    private lateinit var binding: DialogLetterAddFriendBinding

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
                // TODO: 친구 추가
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // dialog 크기 동적으로 크기
        val parentWidth = resources.displayMetrics.widthPixels
        val size = parentWidth - (parentWidth / 10)
        dialog?.window?.setLayout(size, ViewGroup.LayoutParams.WRAP_CONTENT)    // (너비, 높이) 지정

        // dialog background 동적으로 주기
        dialog?.window?.setBackgroundDrawableResource(R.drawable.custom_round_dialog_view)
    }

    companion object {
        const val TAG = "ReadLetterAddFriendDial"
    }
}