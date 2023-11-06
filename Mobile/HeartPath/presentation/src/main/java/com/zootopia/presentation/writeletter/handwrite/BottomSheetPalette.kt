package com.zootopia.presentation.writeletter.handwrite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.BottomSheetPaletteBinding
import com.zootopia.presentation.util.PenColorState
import com.zootopia.presentation.writeletter.WriteLetterViewModel
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

    private fun initListener() = with(binding) {
        sliderPenSize.addOnChangeListener { slider, value, fromUser ->
            writeLetterViewModel.setPenSize(value)
        }
        buttonPen.setOnClickListener {
            writeLetterViewModel.setEraserState(false)
        }
        buttonEraser.setOnClickListener {
            writeLetterViewModel.setEraserState(true)
        }
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
                binding.imageviewPenStyleView.apply {
                    setColorFilter(
                        ContextCompat.getColor(
                            binding.root.context,
                            colorId
                        )
                    )
                    requestLayout()
                }
            }
        }
        lifecycleScope.launch {
            writeLetterViewModel.penSize.collectLatest {
                binding.sliderPenSize.value = it
                binding.imageviewPenStyleView.apply {
                    layoutParams.width = it.toInt()/2
                    layoutParams.height = it.toInt()/2
                    requestLayout()
                }
                binding.imageviewEraserStyleView.apply {
                    layoutParams.width = it.toInt()/2
                    layoutParams.height = it.toInt()/2
                    requestLayout()
                }
            }
        }
        lifecycleScope.launch {
            writeLetterViewModel.isEraserSelected.collectLatest {
                Log.d(TAG, "initCollecter: eraser ${it}")
                if(it){
                    binding.imageviewPenStyleView.visibility = View.GONE
                    binding.imageviewEraserStyleView.visibility = View.VISIBLE
                    binding.buttonPen.apply {
                        setColorFilter(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.LightBlueGray
                            )
                        )
                        requestLayout()
                    }
                    binding.buttonEraser.apply {
                        setColorFilter(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.DarkBlueGray
                            )
                        )
                        requestLayout()
                    }
                }else{
                    binding.imageviewEraserStyleView.visibility = View.GONE
                    binding.imageviewPenStyleView.visibility = View.VISIBLE
                    binding.buttonEraser.apply {
                        setColorFilter(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.LightBlueGray
                            )
                        )
                        requestLayout()
                    }
                    binding.buttonPen.apply {
                        setColorFilter(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.DarkBlueGray
                            )
                        )
                        requestLayout()
                    }
                }
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