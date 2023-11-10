package com.zootopia.presentation.writeletter.addletterimage

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.zootopia.presentation.databinding.ItemAddLetterImageBinding

private const val TAG = "AddLetterImageAdapter"

class AddLetterImageAdapter(var imageList: MutableList<Uri>) :
    RecyclerView.Adapter<AddLetterImageAdapter.AddLetterImageViewHolder>() {

    interface DeleteClickListener {
        fun deleteClick(index: Int)
    }

    lateinit var deleteClickListener: DeleteClickListener

    inner class AddLetterImageViewHolder(val binding: ItemAddLetterImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(item: Uri, index: Int) = with(binding) {
            Glide.with(binding.root.context)
                .load(item)
                .transform(CenterCrop(), RoundedCorners(20))
                .into(imageviewItemAddLetterImage)
            buttonDeleteImage.setOnClickListener {
                deleteClickListener.deleteClick(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddLetterImageViewHolder {
        return AddLetterImageViewHolder(
            ItemAddLetterImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return imageList.count()
    }

    override fun onBindViewHolder(holder: AddLetterImageViewHolder, position: Int) {
        holder.bindInfo(imageList[position], position)
    }
}