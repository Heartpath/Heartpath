package com.zootopia.presentation.store.storecharacter

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.zootopia.domain.model.store.StoreCharacterDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.DialogStoreCharacterBinding
import com.zootopia.presentation.store.StoreViewModel

class StoreCharacterDialog(context: Context, private val storeCharacterDto: StoreCharacterDto) :
    DialogFragment() {
    private lateinit var binding: DialogStoreCharacterBinding
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
        binding = DialogStoreCharacterBinding.inflate(inflater, container, false)
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
            storeViewModel.buyStoreCharacter(storeCharacterDto.characterId)
            dismiss()
        }
    }

    private fun initData() = with(binding) {
        textviewStoreCharacterName.text = storeCharacterDto.characterName
        textviewStoreCharacterDescription.text = storeCharacterDto.description
        val point = storeViewModel.userInfo.value.point
        if (storeCharacterDto.isOwned) { //이미 보유한 경우
            buttonBuy.visibility = View.GONE
            textviewCantBuy.visibility = View.GONE
            textviewAlreadyPurchased.visibility = View.VISIBLE
        } else if (point - storeCharacterDto.price < 0) { //구매 포인트 부족
            buttonBuy.visibility = View.GONE
            textviewCantBuy.visibility = View.VISIBLE
            textviewAlreadyPurchased.visibility = View.GONE
        } else { //구매가능
            buttonBuy.visibility = View.VISIBLE
            textviewCantBuy.visibility = View.GONE
            textviewAlreadyPurchased.visibility = View.GONE
        }

        Glide.with(binding.root).load(storeCharacterDto.imagePath).into(binding.imageviewStoreBird)
    }


}