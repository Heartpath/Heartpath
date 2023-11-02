package com.zootopia.presentation.writeletter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.BottomSheetPaletteBinding
import com.zootopia.presentation.util.PenColorState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "BottomSheetPalette_HP"

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
        initCollecter()
    }

    private fun initRecyclerGridView() = with(binding) {
        paletteColorAdapter = PaletteColorAdapter(colorList)

        paletteColorAdapter.colorClickListener = object : PaletteColorAdapter.ColorClickListener {
            override fun onColorClicked(id: Int, selectedIndex: Int) {
                writeLetterViewModel.setSelectedColor(id)
            }

        }

        recyclerviewPaletteColors.apply {
            adapter = paletteColorAdapter
            layoutManager = GridLayoutManager(context, 4)
        }
    }

    private fun initListener() {

    }

    private fun initCollecter() {
        lifecycleScope.launch {
            writeLetterViewModel.selectedColor.collectLatest {  colorId ->
                colorList.mapIndexed { index, penColorState ->
                    if(penColorState.penColor == colorId){
                        colorList[index].penState = true
                    }else{
                        colorList[index].penState = false
                    }
                }
                paletteColorAdapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        private val colorList = arrayListOf<PenColorState>(
            PenColorState(R.color.black, false),
            PenColorState(R.color.Red, false),
            PenColorState(R.color.Orange, false),
            PenColorState(R.color.Yellow, false),
            PenColorState(R.color.Green, false),
            PenColorState(R.color.SkyBlue, false),
            PenColorState(R.color.Blue, false),
            PenColorState(R.color.Purple, false)
        )
    }
}