package com.zootopia.presentation.mypage

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.DialogReportFriendBinding
import com.zootopia.presentation.searchfriend.FriendSearchFriendAddDialog

class MyPageReportFriendDialog: DialogFragment() {
    private lateinit var binding: DialogReportFriendBinding

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
        binding = DialogReportFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonReportFriendCancel.setOnClickListener {
                dismiss()
            }
            buttonReportFriendAccept.setOnClickListener {
                // TODO: 사용자 신고
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // dialog 하단에 띄우기
        dialog?.window?.setGravity(Gravity.BOTTOM)

        // dialog 크기 동적으로 주기
        val parentWidth = resources.displayMetrics.widthPixels
        val size = parentWidth - (parentWidth/10)
        dialog?.window?.setLayout(size, ViewGroup.LayoutParams.WRAP_CONTENT)

        // dialog background 동적으로 주기
        dialog?.window?.setBackgroundDrawableResource(R.drawable.custom_round_dialog_view)
    }
}