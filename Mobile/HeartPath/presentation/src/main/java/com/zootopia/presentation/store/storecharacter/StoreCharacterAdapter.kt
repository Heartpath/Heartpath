package com.zootopia.presentation.store.storecharacter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zootopia.domain.model.store.StoreCharacterDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.ItemStoreCharacterBinding

class StoreCharacterAdapter(var storeCharacterList: MutableList<StoreCharacterDto>) :
    RecyclerView.Adapter<StoreCharacterAdapter.StoreCharacterViewHolder>() {

    interface ItemClickListener {
        fun onItemClicked(character: StoreCharacterDto)
    }

    lateinit var itemClickListener: ItemClickListener

    inner class StoreCharacterViewHolder(var binding: ItemStoreCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setStoreCharacter(character: StoreCharacterDto) {
            binding.textviewStoreBirdName.text = character.characterName
            binding.textviewStoreBirdPrice.text =
                binding.root.context.getString(R.string.store_price, character.price)
            Glide.with(binding.root).load(character.imagePath).into(binding.imageviewStoreBird)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoreCharacterAdapter.StoreCharacterViewHolder {
        return StoreCharacterViewHolder(
            ItemStoreCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: StoreCharacterAdapter.StoreCharacterViewHolder,
        position: Int
    ) {
        holder.setStoreCharacter(storeCharacterList[position])
    }

    override fun getItemCount(): Int {
        return storeCharacterList.size
    }

}