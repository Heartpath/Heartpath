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

class MyPageFragment : BaseFragment<FragmentMyPageBinding> (
    FragmentMyPageBinding::bind,
    R.layout.fragment_my_page
){
    private lateinit var myPageFriendAdapter: MyPageFriendAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initClickEvent()

    }
    private fun initAdapter() = with(binding) {
        myPageFriendAdapter = MyPageFriendAdapter()

    }
    private fun initClickEvent() = with(binding) {
        linearlayoutAddFriend.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_friendSearchFragment)
        }
        linearlayoutPoint.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_pointHistoryFragment)
        }
        recyclerviewMypageFriend.apply {
            adapter = myPageFriendAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}