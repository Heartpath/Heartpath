package com.zootopia.presentation.sendletter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.domain.model.unplacedletter.UnplacedLetterDto
import com.zootopia.presentation.databinding.ItemSendLetterBinding

// todo : dto 생성
private const val TAG = "SendLetterAdapter_HeartPath"
class SendLetterAdapter(
    var unplacedLetterList: MutableList<UnplacedLetterDto> = mutableListOf()
) : RecyclerView.Adapter<SendLetterAdapter.SendLetterViewHolder>() {
    
    inner class SendLetterViewHolder(val binding: ItemSendLetterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(unplacedLetterDto: UnplacedLetterDto) = with(binding) {
            textviewReceiveUser.text = "${unplacedLetterDto.receiver}님에게 보낼 편지"
            
            // Click Event
            linearlayoutLetter.setOnClickListener {
                itemClickListener.itemClick(it, unplacedLetterDto, layoutPosition)
            }
        }
    }
    
    interface ItemClickListener {
        fun itemClick(view: View, unplacedLetterDto: UnplacedLetterDto, position: Int)
    }
    lateinit var itemClickListener: ItemClickListener
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendLetterViewHolder {
        return SendLetterViewHolder(
            ItemSendLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }
    
    fun submitData(unplacedLetterList: MutableList<UnplacedLetterDto>) {
        this.unplacedLetterList = unplacedLetterList
    }
    
    override fun onBindViewHolder(holder: SendLetterViewHolder, position: Int) {
        holder.bindInfo(unplacedLetterList[position])
    }
    
    override fun getItemCount(): Int {
        return unplacedLetterList.size
    }
}
