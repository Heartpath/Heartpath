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
import com.zootopia.domain.model.user.FriendDto
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
    private var myFriendList: MutableList<FriendDto> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initCollector()
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
            myPageViewModel.userInfo.collect { user ->
                textviewProfileId.text = root.context.getString(R.string.default_id, user.memberID)
                textviewProfileName.text = user.nickname
                textviewPoint.text = makeComma(user.point)
                if(user.profileImagePath == "") { // 이미지 빈 값일 때
                    Glide
                        .with(mainActivity)
                        .load(R.drawable.image_default_profile)
                        .into(imageviewProfileImg)
                } else {    // 이미지 값 있을 때
                    Glide.with(mainActivity)
                        .load(user.profileImagePath)
                        .error(R.drawable.image_default_profile)
                        .circleCrop()
                        .into(imageviewProfileImg)
                }
            }
        }
    }

    private fun initAdapter() = with(binding) {
        myPageFriendAdapter = MyPageFriendAdapter(list = myFriendList).apply {
            itemClickListener = object : MyPageFriendAdapter.ItemClickListener {
                override fun itemClick(view: View, position: Int) {
                }

                override fun itemLongClick(view: View, position: Int) {
                    // 친구 수정 다이얼로그 띄우기
                    MyPageEditFriendDialog(list[position].memberId).show(childFragmentManager, tag)
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

    private fun initData() {
        myPageViewModel.getUserInfo()
        myPageViewModel.getFriendList()
    }

    private fun initCollector() {
        lifecycleScope.launch {
            myPageViewModel.friendListInfo.collect { value ->
                myFriendList.clear()
                myFriendList.addAll(value)
                myPageFriendAdapter.notifyDataSetChanged()
            }
        }
    }
}