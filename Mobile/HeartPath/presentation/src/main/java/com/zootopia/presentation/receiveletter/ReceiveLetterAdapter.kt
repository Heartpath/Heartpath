package com.zootopia.presentation.receiveletter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.domain.model.letter.ReceiveLetterDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.ItemReceiveLetterBinding
import com.zootopia.presentation.util.convertDateFormat

@RequiresApi(Build.VERSION_CODES.O)
class ReceiveLetterAdapter(val list: MutableList<ReceiveLetterDto>) :
    RecyclerView.Adapter<ReceiveLetterAdapter.ReceiveLetterViewHolder>() {
    
    inner class ReceiveLetterViewHolder(val binding: ItemReceiveLetterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindInfo(receiveLetter: ReceiveLetterDto) = with(binding) {
            if(receiveLetter.read) {
                constraintlayoutLetter.setBackgroundResource(R.drawable.image_open_letter)
            } else {
                constraintlayoutLetter.setBackgroundResource(R.drawable.image_unopen_letter)
            }
            
            textviewSendUser.text = "from.${receiveLetter.sender}"
            textviewSendTime.text = convertDateFormat(receiveLetter.time)
            
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
        holder.bindInfo(list[position])
    }
    
    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        private const val TAG = "ReceiveLetterAdapter_HP"
    }
}
