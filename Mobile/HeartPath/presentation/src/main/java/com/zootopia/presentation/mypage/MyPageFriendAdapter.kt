package com.zootopia.presentation.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zootopia.domain.model.user.FriendDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.ItemMypageFriendBinding

class MyPageFriendAdapter(val list: MutableList<FriendDto>) :
    RecyclerView.Adapter<MyPageFriendAdapter.MyPageFriendViewHolder>() {
    inner class MyPageFriendViewHolder(val binding: ItemMypageFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(friend: FriendDto) = with(binding) {
            textviewFriendName.text = friend.nickname
            textviewFriendId.text = friend.memberId
            if(friend.profileImage == "") { // 이미지 빈 값일 때
                Glide
                    .with(root)
                    .load(R.drawable.image_default_profile)
                    .into(imageviewFriendProfileImg)
            } else {    // 이미지 값 있을 때
                Glide.with(root).load(friend.profileImage)
                    .error(R.drawable.image_default_profile)
                    .circleCrop()
                    .into(imageviewFriendProfileImg)
            }
            linearlayoutFriendInfo.setOnClickListener {
                itemClickListener.itemLongClick(it, layoutPosition)
            }
        }
    }
    interface ItemClickListener {
        fun itemClick(view: View, position: Int)
        fun itemLongClick(view: View, position: Int)
    }

    lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageFriendViewHolder {
        return MyPageFriendViewHolder(
            ItemMypageFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyPageFriendViewHolder, position: Int) {
        holder.bindInfo(list[position])
    }
}