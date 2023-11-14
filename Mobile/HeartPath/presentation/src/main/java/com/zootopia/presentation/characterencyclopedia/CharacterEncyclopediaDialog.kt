package com.zootopia.presentation.characterencyclopedia

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.DialogCharacterEncyclopediaBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterEncyclopediaDialog (context: Context, private val characterDto: CharacterDto) :
    DialogFragment() {
    private lateinit var binding: DialogCharacterEncyclopediaBinding
    private val characterEncyclopediaViewModel: CharacterEncyclopediaViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCharacterEncyclopediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initData()
        initCollecter()
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
        buttonChangeMainCharacter.setOnClickListener {
            characterEncyclopediaViewModel.changeMainCharacter(characterDto.characterId)
        }
    }

    private fun initData() = with(binding){
        textviewEncyclopediaCharacterName.text = characterDto.characterName
        textviewEncyclopediaCharacterDescription.text = characterDto.description

        Glide.with(binding.root).load(characterDto.imagePath)
            .into(binding.imageviewEncyclopediaBird)

        if(characterDto.isMain){
            buttonChangeMainCharacter.visibility = View.GONE
        }else{
            buttonChangeMainCharacter.visibility = View.VISIBLE
        }

    }

    private fun initCollecter(){
        lifecycleScope.launch {
            characterEncyclopediaViewModel.isSendSuccess.collectLatest {
                if (it) {
                    characterEncyclopediaViewModel.resetIsSendSuccess()
                    Toast.makeText(
                        binding.root.context,
                        R.string.character_encyclopedia_change_success,
                        Toast.LENGTH_LONG
                    ).show()
                    dismiss()
                }
            }
        }
    }

}