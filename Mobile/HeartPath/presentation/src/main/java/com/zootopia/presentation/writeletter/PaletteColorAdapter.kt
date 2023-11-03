package com.zootopia.presentation.writeletter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zootopia.presentation.databinding.ItemPaletteColorBinding
import com.zootopia.presentation.util.PenColorState

class PaletteColorAdapter(var colorList: ArrayList<PenColorState>) :
    RecyclerView.Adapter<PaletteColorAdapter.GridViewHolder>() {

    interface ColorClickListener {
        fun onColorClicked(id: Int, index: Int)
    }

    lateinit var colorClickListener: ColorClickListener

    inner class GridViewHolder(var binding: ItemPaletteColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setColor(penColorState: PenColorState, index: Int) {
            if (penColorState.penState == true) {
                binding.imageviewCheck.visibility = View.VISIBLE
            } else {
                binding.imageviewCheck.visibility = View.INVISIBLE
            }
            binding.buttonItemPaletteColor.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    penColorState.penColor
                )
            )
            binding.buttonItemPaletteColor.setOnClickListener {
                colorClickListener.onColorClicked(penColorState.penColor, index)
            }
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
        holder.setColor(colorList[position], position)
    }
}