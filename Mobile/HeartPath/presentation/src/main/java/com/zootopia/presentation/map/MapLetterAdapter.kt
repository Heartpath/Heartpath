package com.zootopia.presentation.map

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.domain.model.map.GoalDto
import com.zootopia.presentation.databinding.ItemMapLetterBinding

class MapLetterAdapter : ListAdapter<GoalDto, MapLetterAdapter.MapLetterViewHolder>(diffUtil) {
    
    inner class MapLetterViewHolder(val binding: ItemMapLetterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo() = with(binding) {

        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapLetterViewHolder {
        TODO("Not yet implemented")
    }
    
    override fun onBindViewHolder(holder: MapLetterViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
    
    // todo GoalDto랑 oldItem, newItem . 값 변경해야함 수신하는 메시지 dto에 맞게
    companion object {
        // diffUtil: currentList에 있는 각 아이템들을 비교하여 최신 상태를 유지하도록 한다.
        val diffUtil = object : DiffUtil.ItemCallback<GoalDto>() {
            override fun areItemsTheSame(oldItem: GoalDto, newItem: GoalDto): Boolean {
                return oldItem.location == newItem.location
            }
            
            override fun areContentsTheSame(oldItem: GoalDto, newItem: GoalDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}