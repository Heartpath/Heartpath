package com.zootopia.presentation.pointhistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.presentation.databinding.ItemPointHistoryBinding

class PointHistoryAdapter():
RecyclerView.Adapter<PointHistoryAdapter.PointHistoryViewHolder>(){
    inner class PointHistoryViewHolder(val binding: ItemPointHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bindInfo() = with(binding) {
            textviewPointHistory.text = "10포인트를 수령했습니다"
            textviewPointHistoryDate.text = "2023.10.25"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointHistoryViewHolder {
        return PointHistoryViewHolder(
            ItemPointHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: PointHistoryViewHolder, position: Int) {
        holder.bindInfo()
    }

}