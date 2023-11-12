package com.zootopia.presentation.readletter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zootopia.presentation.databinding.ItemReadLetterBinding

class LetterViewPagerAdapter(var letterImageList: MutableList<String>):
RecyclerView.Adapter<LetterViewPagerAdapter.PagerViewHolder>() {
    inner class PagerViewHolder(var binding: ItemReadLetterBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun setImage(image: String) {
                    // image view 이미지 값 설정
                    Glide
                        .with(binding.root)
                        .load(image)
                        .error("")
                        .into(binding.imageviewLetterResult)
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(
            ItemReadLetterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return letterImageList.size
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        return holder.setImage(letterImageList[position])
    }


}