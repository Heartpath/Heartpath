package com.zootopia.presentation.mypage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentMyPageBinding
import com.zootopia.presentation.util.makeComma
import kotlinx.coroutines.launch

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(
    FragmentMyPageBinding::bind,
    R.layout.fragment_my_page
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var myPageFriendAdapter: MyPageFriendAdapter
    private val myPageViewModel: MyPageViewModel by activityViewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myPageViewModel.getUserInfo()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
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
        lifecycleScope.launch {
            myPageViewModel.userInfo.collect {user ->
                textviewProfileId.text = user.memberID
                textviewProfileName.text = user.nickname
                textviewPoint.text = makeComma(user.point)
                Glide.with(mainActivity).load(user.profileImagePath).circleCrop().into(imageviewProfileImg)
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