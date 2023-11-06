package com.zootopia.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment<FragmentSettingBinding>(
    FragmentSettingBinding::bind,
    R.layout.fragment_setting
){
    private val settingViewModel : SettingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClickEvent()
    }

    private fun initView() = with(binding) {
        // toolbar setting
        toolbarHeartpathSetting.apply {
            textviewCurrentPageTitle.text = resources.getString(R.string.toolbar_setting_title)
            imageviewBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
    private fun initClickEvent() = with(binding) {
        linearlayoutSetBgm.setOnClickListener {
            // TODO: bgm 설정
            BgmSettingDialog().show(childFragmentManager, BgmSettingDialog.TAG)
        }
        linearlayoutPrivatePolicy.setOnClickListener {
            // TODO: 개인정보 처리방침 페이지로 이동
        }
        linearlayoutLogout.setOnClickListener {
            // TODO: 로그아웃
        }
        linearlayoutWithdraw.setOnClickListener {
            // TODO: 회원 탈퇴
        }
    }
}