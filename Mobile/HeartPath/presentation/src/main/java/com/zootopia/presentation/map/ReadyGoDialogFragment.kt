package com.zootopia.presentation.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.zootopia.domain.model.letter.uncheckedletter.UncheckLetterDto
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseDialogFragment
import com.zootopia.presentation.databinding.FragmentReadyGoBinding

class ReadyGoDialogFragment(
    val uncheckLetterDto: UncheckLetterDto
) : BaseDialogFragment<FragmentReadyGoBinding>(FragmentReadyGoBinding::bind, R.layout.fragment_ready_go) {
    
    private val mapViewModel: MapViewModel by activityViewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initClickEvent()
    }
    
    private fun initClickEvent() = with(binding) {
        buttonStart.setOnClickListener {
            mapViewModel.requestTmapWalkRoad(
                uncheckLetterDto = uncheckLetterDto
            )
            dismiss()
        }
    }

}