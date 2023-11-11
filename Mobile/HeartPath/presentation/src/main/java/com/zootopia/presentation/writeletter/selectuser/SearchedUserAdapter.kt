package com.zootopia.presentation.writeletter.selectuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zootopia.domain.model.user.SearchUserInfoDto
import com.zootopia.presentation.databinding.ItemMypageFriendBinding

private const val TAG = "SearchedUserAdapter"
class SearchedUserAdapter(var searchedUserList: MutableList<SearchUserInfoDto>) :
    RecyclerView.Adapter<SearchedUserAdapter.SearchUserViewHolder>() {

    interface ItemClickListener {
        fun itemClick(user: SearchUserInfoDto)
    }

    lateinit var itemClickListener: ItemClickListener

    inner class SearchUserViewHolder(val binding: ItemMypageFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(user: SearchUserInfoDto) = with(binding) {
            textviewFriendName.text = user.nickname
            textviewFriendId.text = user.memberID

            Glide.with(root).load(user.profileImagePath)
                .circleCrop()
                .into(imageviewFriendProfileImg)

            linearlayoutFriendInfo.setOnClickListener {
                itemClickListener.itemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        return SearchUserViewHolder(
            ItemMypageFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return searchedUserList.count()
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        holder.bindInfo(searchedUserList[position])
    }
}