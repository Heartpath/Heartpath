package com.zootopia.presentation.writeletter.selectletterpaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zootopia.domain.model.letter.UserLetterPaperDto
import com.zootopia.presentation.databinding.ItemLetterPaperBinding

class LetterPaperViewPagerAdapter(var letterPaperList: MutableList<UserLetterPaperDto>) :
    RecyclerView.Adapter<LetterPaperViewPagerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(var binding: ItemLetterPaperBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setImage(userLetterPaperDto: UserLetterPaperDto) {
            Glide.with(binding.root).load(userLetterPaperDto.imageUrl).into(binding.imageviewLetterPaperItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(
            ItemLetterPaperBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return letterPaperList.size
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.setImage(letterPaperList[position])
    }
}