package com.zootopia.presentation.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentMyPageBinding

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(
    FragmentMyPageBinding::bind,
    R.layout.fragment_my_page
) {
    private lateinit var myPageFriendAdapter: MyPageFriendAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
        initClickEvent()
    }

    private fun initView() = with(binding) {
        // toolbar setting
        toolbarHeartpathMyPage.apply {
            textviewCurrentPageTitle.text = resources.getString(R.string.toolbar_my_page_title)
            imageviewSettingIcon.visibility = View.VISIBLE
            imageviewSettingIcon.setOnClickListener {
                findNavController().navigate(R.id.action_myPageFragment_to_settingFragment)
            }
            imageviewBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initAdapter() = with(binding) {
        myPageFriendAdapter = MyPageFriendAdapter().apply {
            itemClickListener = object : MyPageFriendAdapter.ItemClickListener {
                override fun itemClick(view: View, position: Int) {
                    TODO("Not yet implemented")
                }

                override fun itemLongClick(view: View, position: Int) {
                    // 친구 수정 다이얼로그 띄우기
                    MyPageEditFriendDialog().show(childFragmentManager, tag)
                }
            }
        }
        recyclerviewMypageFriend.apply {
            adapter = myPageFriendAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initClickEvent() = with(binding) {
        linearlayoutAddFriend.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_friendSearchFragment)
        }
        linearlayoutPoint.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_pointHistoryFragment)
        }
    }
}