package com.zootopia.presentation.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.DialogLoadingBinding

class LoadingDialog : DialogFragment() {
    private lateinit var binding : DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogLoadingBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        Glide
            .with(this@LoadingDialog)
            .load(R.drawable.animation_bird_flying)
            .into(imageviewLoadingBird)
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        const val TAG = "LoadingDialog"
    }
}