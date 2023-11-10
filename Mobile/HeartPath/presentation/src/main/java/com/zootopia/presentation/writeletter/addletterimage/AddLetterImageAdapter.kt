package com.zootopia.presentation.writeletter.addletterimage

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            Log.d(TAG, "bindInfo: ${item}")
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