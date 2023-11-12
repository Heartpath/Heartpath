package com.zootopia.presentation.store.storeletterpapper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.zootopia.domain.model.store.StoreItemLetterPaperDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.ItemStoreLetterpaperBinding

class StoreLetterPaperAdapter(var storeLetterPaperList: MutableList<StoreItemLetterPaperDto>) :
    RecyclerView.Adapter<StoreLetterPaperAdapter.StoreLetterPaperViewHolder>() {

    interface ItemClickListener {
        fun onItemClicked(character: StoreItemLetterPaperDto)
    }

    lateinit var itemClickListener: ItemClickListener

    inner class StoreLetterPaperViewHolder(var binding: ItemStoreLetterpaperBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setStoreLetterPaper(letterPaper: StoreItemLetterPaperDto) {
            binding.textviewStoreLetterpapperPrice.text =
                binding.root.context.getString(R.string.store_price, letterPaper.price)
            binding.textviewStoreLetterpapperName.text = letterPaper.letterName
            Glide.with(binding.root).load(letterPaper.imagePath)
                .transform(CenterInside(), RoundedCorners(20))
                .into(binding.imageviewStoreLetterpapper)
            if (letterPaper.isOwned) {
                binding.imageviewIsOwned.visibility = View.VISIBLE
            } else {
                binding.imageviewIsOwned.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreLetterPaperViewHolder {
        return StoreLetterPaperViewHolder(
            ItemStoreLetterpaperBinding.inflate(
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