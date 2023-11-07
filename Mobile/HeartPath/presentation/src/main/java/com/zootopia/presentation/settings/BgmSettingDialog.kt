package com.zootopia.presentation.settings

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.DialogBgmSettingBinding
import kotlinx.coroutines.flow.collectLatest

class BgmSettingDialog : DialogFragment() {
    private lateinit var binding: DialogBgmSettingBinding
    private val settingViewModel : SettingViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true // 화면 밖에 클릭하면 dismiss 되도록
        Log.d(TAG, "onCreate: bgm dialog oncreate")
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
        settingViewModel.getBgmState()
        binding.apply {
            switchBgmSetting.isChecked = settingViewModel.switchState.value
            buttonDone.setOnClickListener {
                settingViewModel.saveBgmState()
                dismiss()
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
        // switch custom 값 동적으로 주기
        binding.switchBgmSetting.apply {
            trackDrawable = context?.getDrawable(R.drawable.custom_switch_track)
            thumbDrawable = context?.getDrawable(R.drawable.custom_switch_thumb)
            setOnClickListener {
                Log.d(TAG, "onResume: switch button clicked")
                settingViewModel.changeBgmState()
            }
        }
    }

    companion object {
        const val TAG = "BgmSettingDialog"
    }
}