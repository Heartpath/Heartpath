package com.zootopia.presentation.map

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.domain.model.letter.uncheckedletter.UncheckLetterDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.ItemMapLetterBinding
import com.zootopia.presentation.util.convertDateFormat

private const val TAG = "MapLetterAdapter_HP"
/**
 * ListAdapter
 */
@RequiresApi(Build.VERSION_CODES.O)
class MapLetterAdapter(
    val mapViewModel: MapViewModel
) : ListAdapter<UncheckLetterDto, MapLetterAdapter.MapLetterViewHolder>(diffUtil) {
    
    inner class MapLetterViewHolder(val binding: ItemMapLetterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bindInfo(uncheckLetterDto: UncheckLetterDto) = with(binding) {
            val time = convertDateFormat(uncheckLetterDto.time)
            textviewLetterTitle.text = "${uncheckLetterDto.sender}님이 보낸 편지"
            textviewLetterTime.text = time
            
            // 메시지 클릭
            linearlayoutMapLetter.setOnClickListener {
                itemClickListener.itemClick(it, layoutPosition, uncheckLetterDto)
            }
            
            // 신고버튼 클릭 이벤트
            buttonReport.setOnClickListener{
//                if(uncheckLetterDto.isSelected) {
//                    buttonReport.setBackgroundResource(R.drawable.image_selected_button)
//                } else {
//                    buttonReport.setBackgroundResource(R.drawable.image_un_selected_button)
//                }
//                uncheckLetterDto.isSelected = !uncheckLetterDto.isSelected
                itemClickListener.reportClick(it, layoutPosition)
            }
        }
    }
    
    interface ItemClickListener {
        fun itemClick(view: View, position: Int, uncheckLetterDto: UncheckLetterDto)
        fun reportClick(view: View, position: Int)
    }
    lateinit var itemClickListener: ItemClickListener
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapLetterViewHolder {
        return MapLetterViewHolder(
            ItemMapLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    
    override fun onBindViewHolder(holder: MapLetterViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ${currentList[position].isSelected}")
//        if(currentList[position].isSelected) {
        if(mapViewModel.isReport) {
            holder.binding.buttonReport.visibility = View.VISIBLE
        } else {
            holder.binding.buttonReport.visibility = View.GONE
        }
    
        if (currentList[position].isSelected) {
            holder.binding.buttonReport.setBackgroundResource(R.drawable.image_selected_button)
        } else {
            holder.binding.buttonReport.setBackgroundResource(R.drawable.image_un_selected_button)
        }
        
        holder.bindInfo(currentList[position])
    }
    
    // todo GoalDto랑 oldItem, newItem . 값 변경해야함 수신하는 메시지 dto에 맞게
    companion object {
        // diffUtil: currentList에 있는 각 아이템들을 비교하여 최신 상태를 유지하도록 한다.
        val diffUtil = object : DiffUtil.ItemCallback<UncheckLetterDto>() {
            override fun areItemsTheSame(oldItem: UncheckLetterDto, newItem: UncheckLetterDto): Boolean {
                return oldItem.index == newItem.index
            }
            
            override fun areContentsTheSame(oldItem: UncheckLetterDto, newItem: UncheckLetterDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}