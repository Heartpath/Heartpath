package com.zootopia.presentation.arcore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseDialogFragment
import com.zootopia.presentation.databinding.FragmentReadSuccessDialogBinding
import com.zootopia.presentation.map.MapViewModel

class ReadSuccessDialogFragment : BaseDialogFragment<FragmentReadSuccessDialogBinding>(
    FragmentReadSuccessDialogBinding::bind,
    R.layout.fragment_read_success_dialog
) {
    private val mapViewModel: MapViewModel by activityViewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initClickEvent()
    }
    
    
    private fun initClickEvent() = with(binding) {
        buttonMove.setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_receiveLetterFragment)
            dismiss()
        }
        
        cardviewClose.setOnClickListener {
            dismiss()
        }
    }
}