package com.zootopia.presentation.mypage

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.DialogFriendEditBinding

class MyPageEditFriendDialog: DialogFragment() {
    private lateinit var binding: DialogFriendEditBinding

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
        binding = DialogFriendEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonFriendEditDelete.setOnClickListener {
                // TODO: 친구 삭제

//                MyPageDeleteFriendDialog().show(childFragmentManager, tag)
            }
            buttonFriendEditReport.setOnClickListener {
                // TODO: 사용자 신고

//                MyPageReportFriendDialog().show(childFragmentManager, tag)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val parentWidth = resources.displayMetrics.widthPixels
        val size = parentWidth - (parentWidth/6)
        dialog?.let {dialog ->
            // dialog 크기 동적으로 주기
            dialog.window?.setLayout(size, ViewGroup.LayoutParams.WRAP_CONTENT)
            // dialog background 동적으로 주기
            dialog.window?.setBackgroundDrawableResource(R.drawable.custom_round_dialog_view)
            // 중간에 표시
            dialog.window?.setGravity(Gravity.CENTER)
        }
    }
}