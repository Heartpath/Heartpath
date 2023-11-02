package com.zootopia.presentation.settings

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.DialogFragment
import com.zootopia.presentation.databinding.DialogBgmSettingBinding

class BgmSettingDialog(): DialogFragment() {
    private lateinit var binding: DialogBgmSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogBgmSettingBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.apply {
            buttonDone.setOnClickListener {
            //TODO: 상태 저장
                dismiss()
            }
        }
        return view
    }

    interface BgmSettingDialogListener {
        fun onClick(dialog: BgmSettingDialog)
    }
}