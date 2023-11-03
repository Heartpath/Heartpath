package com.zootopia.presentation.receiveletter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.presentation.databinding.ItemReceiveLetterBinding

private const val TAG = "ReceiveLetterAdapter_HeartPath"
class ReceiveLetterAdapter() :
    RecyclerView.Adapter<ReceiveLetterAdapter.ReceiveLetterViewHolder>() {
    
    inner class ReceiveLetterViewHolder(val binding: ItemReceiveLetterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo() = with(binding) {
            textviewSendUser.text = "하동혁님이 보낸 편지"
            textviewSendTime.text = "보낸 시간 2023.03.10"
            
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
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiveLetterViewHolder {
        return ReceiveLetterViewHolder(
            ItemReceiveLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }
    
    override fun onBindViewHolder(holder: ReceiveLetterViewHolder, position: Int) {
        holder.bindInfo()
    }
    
    override fun getItemCount(): Int {
        return 8
    }
}
