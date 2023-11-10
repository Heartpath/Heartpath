package com.zootopia.presentation.searchfriend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zootopia.domain.model.user.FriendDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.ItemSearchedFriendBinding
import com.zootopia.presentation.util.clickAnimation

class SearchFriendAdapter(val list: MutableList<FriendDto>):
RecyclerView.Adapter<SearchFriendAdapter.FriendSearchViewHolder>()
{
    inner class FriendSearchViewHolder(val binding: ItemSearchedFriendBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(friend: FriendDto) = with(binding) {
            textviewFriendSearchFriendName.text = friend.nickname
            textviewFriendSearchFriendId.text = friend.memberId
            if(friend.profileImage == "") { // 이미지 없는 경우
                Glide.with(root)
                    .load(R.drawable.image_default_profile)
                    .into(textviewFriendSearchFriendImg)
            } else {    // 이미지 있는 경우
                Glide.with(root)
                    .load(friend.profileImage)
                    .error(R.drawable.image_default_profile)
                    .circleCrop()
                    .into(textviewFriendSearchFriendImg)
            }
            buttonAddFriend.setOnClickListener {
                this@FriendSearchViewHolder.itemView.findViewTreeLifecycleOwner()
                    ?.let { it1 -> it.clickAnimation(lifeCycleOwner = it1) }
                itemClickListener.itemClick(it, layoutPosition)
            }
        }
    }
    interface ItemClickListener {
        fun itemClick(view: View, position: Int)
    }
    lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendSearchViewHolder {
        return FriendSearchViewHolder(
            ItemSearchedFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FriendSearchViewHolder, position: Int) {
        holder.bindInfo(list[position])
    }

    companion object {
        private const val TAG = "FriendSearchAdapter_HP"
    }

}