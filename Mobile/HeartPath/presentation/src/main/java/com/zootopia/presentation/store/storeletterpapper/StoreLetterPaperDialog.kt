package com.zootopia.presentation.store.storeletterpapper

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.zootopia.domain.model.store.StoreCharacterDto
import com.zootopia.domain.model.store.StoreItemLetterPaperDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.DialogStoreCharacterBinding
import com.zootopia.presentation.databinding.DialogStoreLetterpaperBinding
import com.zootopia.presentation.store.StoreViewModel

class StoreLetterPaperDialog(context: Context, private val storeItemLetterPaperDto: StoreItemLetterPaperDto) :
    DialogFragment() {
    private lateinit var binding: DialogStoreLetterpaperBinding
    private val storeViewModel: StoreViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogStoreLetterpaperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initData()
    }

    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val newWidth = width - (width / 10)
        val newHeight = height - (height / 2)

        dialog?.let { dialog ->
            dialog.window?.setLayout(newWidth, newHeight)
            dialog.window?.setBackgroundDrawableResource(R.drawable.custom_round_dialog_view)
            dialog.window?.setGravity(Gravity.CENTER)
        }

    }


    private fun initListener() = with(binding) {
        buttonDiaologClose.setOnClickListener {
            dismiss()
        }
        buttonBuy.setOnClickListener {
            dismiss()
        }
    }

    private fun initData() = with(binding){
        textviewStoreLetterpaperName.text = storeItemLetterPaperDto.letterName
        textviewStoreLetterpaperDescription.text = storeItemLetterPaperDto.description
        if(storeItemLetterPaperDto.isOwned){
            buttonBuy.visibility = View.INVISIBLE
        }else{
            buttonBuy.visibility = View.VISIBLE
        }
        Glide.with(binding.root).load(storeItemLetterPaperDto.imagePath)
            .transform(CenterInside(), RoundedCorners(20))
            .into(binding.imageviewStoreLetterpaper)
    }


}