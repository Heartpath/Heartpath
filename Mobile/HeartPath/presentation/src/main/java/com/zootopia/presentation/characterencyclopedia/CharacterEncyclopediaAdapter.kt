package com.zootopia.presentation.characterencyclopedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.presentation.databinding.ItemCharacterEncyclopediaBinding

class CharacterEncyclopediaAdapter(var characterList: MutableList<CharacterDto>) :
RecyclerView.Adapter<CharacterEncyclopediaAdapter.CharacterEncyclopediaViewHolder>(){

    interface ItemClickListener{
        fun onItemClicked(character: CharacterDto)
    }
    lateinit var itemClickListener: ItemClickListener

    inner class CharacterEncyclopediaViewHolder(var binding: ItemCharacterEncyclopediaBinding):
    RecyclerView.ViewHolder(binding.root){
        fun setCharacter(character: CharacterDto){
            binding.textviewBirdName.text = character.characterName
            Glide.with(binding.root).load(character.imagePath).into(binding.imageviewBird)
            binding.linearlayoutCharacterEncyclopedia.setOnClickListener {
                itemClickListener.onItemClicked(character)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterEncyclopediaViewHolder {
        return CharacterEncyclopediaViewHolder(
            ItemCharacterEncyclopediaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CharacterEncyclopediaViewHolder, position: Int) {
        holder.setCharacter(characterList[position])
    }

}