package com.zootopia.presentation.writeletter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.presentation.databinding.ItemPaletteColorBinding

class PaletteColorAdapter(var colorList: ArrayList<Int>) :
    RecyclerView.Adapter<PaletteColorAdapter.GridViewHolder>() {

    inner class GridViewHolder(var binding: ItemPaletteColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setColor(colorId: Int) {
            binding.buttonItemPaletteColor.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    colorId
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        return GridViewHolder(
            ItemPaletteColorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.setColor(colorList[position])
    }
}