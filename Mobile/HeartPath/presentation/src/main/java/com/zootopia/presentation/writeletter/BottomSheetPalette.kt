package com.zootopia.presentation.writeletter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zootopia.presentation.databinding.BottomSheetPaletteBinding
import com.zootopia.presentation.R

class BottomSheetPalette : BottomSheetDialogFragment(){
    private lateinit var binding: BottomSheetPaletteBinding
    private val writeLetterViewModel: WriteLetterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetPaletteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initOberver()
    }

    private fun initListener() {

    }

    private fun initOberver(){

    }
    override fun getTheme(): Int = R.style.RoundCornerBottomSheetDialogTheme
}