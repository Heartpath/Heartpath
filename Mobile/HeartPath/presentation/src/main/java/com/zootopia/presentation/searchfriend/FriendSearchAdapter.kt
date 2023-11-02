package com.zootopia.presentation.searchfriend

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.ItemSearchedFriendBinding

private const val TAG = "FriendSearchAdapter_HP"
class FriendSearchAdapter():
RecyclerView.Adapter<FriendSearchAdapter.FriendSearchViewHolder>()
{
    inner class FriendSearchViewHolder(val binding: ItemSearchedFriendBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bindInfo() = with(binding) {
            Glide.with(root).load(R.drawable.image_default_profile)
                .into(textviewFriendSearchFriendImg)
            textviewFriendSearchFriendName.text = "김뱁새 친구"
            textviewFriendSearchFriendId.text = "@iam_babsae_friend"
            buttonAddFriend.setOnClickListener {
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
        return 10
    }

    override fun onBindViewHolder(holder: FriendSearchViewHolder, position: Int) {
        holder.bindInfo()
    }

}