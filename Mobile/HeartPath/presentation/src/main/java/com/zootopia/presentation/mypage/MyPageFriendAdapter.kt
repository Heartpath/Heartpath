package com.zootopia.presentation.mypage

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.ItemMypageFriendBinding

class MyPageFriendAdapter() :
    RecyclerView.Adapter<MyPageFriendAdapter.MyPageFriendViewHolder>() {
    inner class MyPageFriendViewHolder(val binding: ItemMypageFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo() = with(binding) {
            textviewFriendName.text = "김뱁새"
            textviewFriendId.text = "@iam_babsae"
            Glide.with(binding.root).load(R.drawable.image_default_profile)
                .into(imageviewFriendProfileImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageFriendViewHolder {
        return MyPageFriendViewHolder(
            ItemMypageFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: MyPageFriendViewHolder, position: Int) {
        holder.bindInfo()
    }
}