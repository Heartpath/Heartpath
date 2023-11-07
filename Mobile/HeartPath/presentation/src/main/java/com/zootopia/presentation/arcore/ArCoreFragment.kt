package com.zootopia.presentation.arcore

import android.content.Context
import android.os.Bundle
import android.view.View
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentArCoreBinding
import io.github.sceneview.node.ModelNode


class ArCoreFragment
    : BaseFragment<FragmentArCoreBinding>(FragmentArCoreBinding::bind, R.layout.fragment_ar_core) {
    
    private lateinit var mainActivity: MainActivity
    
    lateinit var modelNode: ModelNode
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initArModel()
        initClickEvent()
    }
    
    private fun initArModel() {
    
    }
    
    private fun initClickEvent() {
//        modelNode = ModelNode(
//            modelInstance = ModelInstance
//            autoAnimate = true,
//            scaleToUnits = null,
//            centerOrigin = null,
//        )
    }
    
    
}