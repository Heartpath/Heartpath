package com.zootopia.presentation.sendletter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.presentation.databinding.ItemSendLetterBinding

// todo : dto 생성
private const val TAG = "SendLetterAdapter_HeartPath"
class SendLetterAdapter() : 
    RecyclerView.Adapter<SendLetterAdapter.SendLetterViewHolder>() {
    
    inner class SendLetterViewHolder(val binding: ItemSendLetterBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo() = with(binding) {
            textviewSendUser.text = "하동혁님이 보낸 편지"
            textviewSendTime.text = "보낸 날짜: 2023.03.11"
            
            // Click Event
            linearlayoutLetter.setOnClickListener {
                itemClickListener.itemClick(it, layoutPosition)
            }
        }
    }
    
    interface ItemClickListener {
        fun itemClick(view: View, position: Int)
    }
    lateinit var itemClickListener: ItemClickListener
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendLetterViewHolder {
        return SendLetterViewHolder(
            ItemSendLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }
    
    override fun onBindViewHolder(holder: SendLetterViewHolder, position: Int) {
        holder.bindInfo()
    }
    
    override fun getItemCount(): Int {
        return 8
    }
}
