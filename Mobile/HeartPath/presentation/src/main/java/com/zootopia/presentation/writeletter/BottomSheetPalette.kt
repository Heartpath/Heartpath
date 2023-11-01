package com.zootopia.presentation.writeletter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.BottomSheetPaletteBinding

class BottomSheetPalette : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetPaletteBinding
    private val writeLetterViewModel: WriteLetterViewModel by activityViewModels()
    private lateinit var paletteColorAdapter: PaletteColorAdapter

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
        initRecyclerGridView()
        initListener()
        initOberver()
    }

    private fun initRecyclerGridView() = with(binding) {
        paletteColorAdapter = PaletteColorAdapter(colorList)
        recyclerviewPaletteColors.apply {
            adapter = paletteColorAdapter
            layoutManager = GridLayoutManager(context, 4)
        }
    }

    private fun initListener() {

    }

    private fun initOberver() {

    }

    companion object{
        private val colorList = arrayListOf<Int>(
            R.color.black,
            R.color.Red,
            R.color.Orange,
            R.color.Yellow,
            R.color.Green,
            R.color.SkyBlue,
            R.color.Blue,
            R.color.Purple
        )
    }
}