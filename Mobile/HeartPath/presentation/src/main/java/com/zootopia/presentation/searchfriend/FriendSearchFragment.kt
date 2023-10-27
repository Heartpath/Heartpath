package com.zootopia.presentation.searchfriend

import android.os.Bundle
import android.view.View
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentFriendSearchBinding

class FriendSearchFragment : BaseFragment<FragmentFriendSearchBinding>(
    FragmentFriendSearchBinding::bind,
    R.layout.fragment_friend_search
) {
    private lateinit var friendSearchAdapter: FriendSearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }
    private fun initAdapter() = with(binding) {
        friendSearchAdapter = FriendSearchAdapter()
    }
}