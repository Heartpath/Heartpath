package com.zootopia.presentation.searchfriend

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentFriendSearchBinding

private const val TAG = "FriendSearchFragment_HP"
class FriendSearchFragment : BaseFragment<FragmentFriendSearchBinding>(
    FragmentFriendSearchBinding::bind,
    R.layout.fragment_friend_search
) {
    private lateinit var friendSearchAdapter: FriendSearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }
    private fun initView() = with(binding) {
        // toolbar setting
        toolbarHeartpathSearchFriend.apply {
            textviewCurrentPageTitle.text = resources.getString(R.string.toolbar_friend_search_title)
            imageviewBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
    private fun initAdapter() = with(binding) {
        friendSearchAdapter = FriendSearchAdapter().apply { 
            itemClickListener = object : FriendSearchAdapter.ItemClickListener {
                override fun itemClick(view: View, position: Int) {
                    Log.d(TAG, "itemClick_fragment: $position")
                    FriendSearchFriendAddDialog(requireContext()).show(childFragmentManager, TAG)
                }
            }
        }
        recyclerviewFriendSearchResult.apply {
            adapter = friendSearchAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}