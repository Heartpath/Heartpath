package com.zootopia.presentation.settings

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.DialogBgmSettingBinding

class BgmSettingDialog: DialogFragment() {
    private lateinit var binding: DialogBgmSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Dialog_Custom_Style)
        isCancelable = true // 화면 밖에 클릭하면 dismiss 되도록
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogBgmSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonDone.setOnClickListener {
                //TODO: 상태 저장
                dismiss()
            }
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setGravity(Gravity.BOTTOM) // 다이얼로그를 하단에 표시
        return dialog
    }

    companion object {
        const val TAG = "BgmSettingDialog"
    }
}