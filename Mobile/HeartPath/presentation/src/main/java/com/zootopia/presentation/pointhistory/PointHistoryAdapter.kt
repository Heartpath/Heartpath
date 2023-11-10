package com.zootopia.presentation.pointhistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.domain.model.user.PointDto
import com.zootopia.presentation.databinding.ItemPointHistoryBinding

class PointHistoryAdapter(var list: MutableList<PointDto>):
RecyclerView.Adapter<PointHistoryAdapter.PointHistoryViewHolder>(){
    inner class PointHistoryViewHolder(val binding: ItemPointHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(point: PointDto) = with(binding) {
            textviewPointHistory.text = point.outline
            textviewPointHistoryDate.text = point.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointHistoryViewHolder {
        return PointHistoryViewHolder(
            ItemPointHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PointHistoryViewHolder, position: Int) {
        holder.bindInfo(list[position])
    }

}