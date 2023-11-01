package com.zootopia.presentation.map

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.domain.model.map.MapLetterDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.ItemMapLetterBinding

private const val TAG = "MapLetterAdapter_HP"
/**
 * ListAdapter
 */
class MapLetterAdapter(
    val mapViewModel: MapViewModel
) : ListAdapter<MapLetterDto, MapLetterAdapter.MapLetterViewHolder>(diffUtil) {
    
    inner class MapLetterViewHolder(val binding: ItemMapLetterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(mapLetterDto: MapLetterDto) = with(binding) {
            
            textviewLetterTitle.text = "하동혁님이 보낸 편지"
            textviewLetterTime.text = "2023.10.31"
            
            //
            linearlayoutMapLetter.setOnClickListener {
                itemClickListener.itemClick(it, layoutPosition, mapLetterDto)
            }
            
            // 신고버튼 클릭 이벤트
            buttonReport.setOnClickListener{
                if(mapLetterDto.isSelected) {
                    buttonReport.setBackgroundResource(R.drawable.image_selected_button)
                } else {
                    buttonReport.setBackgroundResource(R.drawable.image_un_selected_button)
                }
                mapLetterDto.isSelected = !mapLetterDto.isSelected
                itemClickListener.reportClick(it, layoutPosition)
            }
        }
    }
    
    interface ItemClickListener {
        fun itemClick(view: View, position: Int, mapLetterDto: MapLetterDto)
        fun reportClick(view: View, position: Int)
    }
    lateinit var itemClickListener: ItemClickListener
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapLetterViewHolder {
        return MapLetterViewHolder(
            ItemMapLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    
    override fun onBindViewHolder(holder: MapLetterViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ${mapViewModel.isReport}")
//        if(currentList[position].isSelected) {
        if(mapViewModel.isReport) {
            holder.binding.buttonReport.visibility = View.VISIBLE
        } else {
            holder.binding.buttonReport.visibility = View.GONE
        }
        
        holder.bindInfo(currentList[position])
    }
    
    // todo GoalDto랑 oldItem, newItem . 값 변경해야함 수신하는 메시지 dto에 맞게
    companion object {
        // diffUtil: currentList에 있는 각 아이템들을 비교하여 최신 상태를 유지하도록 한다.
        val diffUtil = object : DiffUtil.ItemCallback<MapLetterDto>() {
            override fun areItemsTheSame(oldItem: MapLetterDto, newItem: MapLetterDto): Boolean {
                return oldItem.uid == newItem.uid
            }
            
            override fun areContentsTheSame(oldItem: MapLetterDto, newItem: MapLetterDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}