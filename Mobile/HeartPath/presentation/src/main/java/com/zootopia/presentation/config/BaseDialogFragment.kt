package com.zootopia.presentation.config

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<B: ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int,
): DialogFragment(layoutResId){
    private var _binding: B? = null
    
    protected val binding get() = _binding!!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정시 : 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않음.
        isCancelable = true
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        // 배경색 투명
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }
    
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}