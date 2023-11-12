package com.zootopia.presentation.store.storeletterpapper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.zootopia.domain.model.store.StoreLetterPaperDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.ItemStoreLetterpapperBinding

class StoreLetterPaperAdapter(var storeLetterPaperList: MutableList<StoreLetterPaperDto>) :
    RecyclerView.Adapter<StoreLetterPaperAdapter.StoreLetterPaperViewHolder>() {

    interface ItemClickListener {
        fun onItemClicked(character: StoreLetterPaperDto)
    }

    lateinit var itemClickListener: ItemClickListener

    inner class StoreLetterPaperViewHolder(var binding: ItemStoreLetterpapperBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setStoreLetterPaper(letterPaper: StoreLetterPaperDto) {
            binding.textviewStoreLetterpapperPrice.text =
                binding.root.context.getString(R.string.store_price, letterPaper.price)
            binding.textviewStoreLetterpapperName.text = letterPaper.letterName
            Glide.with(binding.root).load(letterPaper.imagePath)
                .transform(CenterInside(), RoundedCorners(20))
                .into(binding.imageviewStoreLetterpapper)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreLetterPaperViewHolder {
        return StoreLetterPaperViewHolder(
            ItemStoreLetterpapperBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return storeLetterPaperList.size
    }

    override fun onBindViewHolder(holder: StoreLetterPaperViewHolder, position: Int) {
        holder.setStoreLetterPaper(storeLetterPaperList[position])
    }

}