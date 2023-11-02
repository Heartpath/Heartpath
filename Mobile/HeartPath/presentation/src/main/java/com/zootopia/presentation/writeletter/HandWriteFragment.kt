package com.zootopia.presentation.writeletter

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentHandWriteBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "HandWriteFragment_HP"

class HandWriteFragment : BaseFragment<FragmentHandWriteBinding>(
    FragmentHandWriteBinding::bind,
    R.layout.fragment_hand_write
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val writeLetterViewModel: WriteLetterViewModel by activityViewModels()

    // 드로잉 펜 설정
    private val strokePaint = Paint().apply {
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = convertDpToPixel(2F)
    }

    // 지우개 모드일 때 지워지는 영역을 표시하기 위한 설정
    private val eraserCirclePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = convertDpToPixel(1F)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initCollecter()
        initClickListener()
    }

    private fun initCollecter() = with(binding) {
        lifecycleScope.launch{

            writeLetterViewModel.selectedLetterPaperUrl.collectLatest {
                Glide.with(mainActivity).load(it)
                    .into(object : CustomTarget<Drawable>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {

                            // 이미지의 너비와 높이를 가져옴
                            val imageWidth = resource.intrinsicWidth.toFloat()
                            val imageHeight = resource.intrinsicHeight.toFloat()

                            // 캔버스의 너비와 높이를 가져옴
                            val canvasWidth = imageviewLetterPaper.width.toFloat()
                            val canvasHeight = imageviewLetterPaper.height.toFloat()

                            // 이미지의 비율을 계산
                            val imageAspectRatio = imageWidth / imageHeight

                            // 이미지의 비율에 따라 캔버스에 맞게 크기 조정
                            val newImageWidth: Float
                            val newImageHeight: Float
                            if (imageAspectRatio > canvasWidth / canvasHeight) {
                                newImageWidth = canvasWidth
                                newImageHeight = canvasWidth / imageAspectRatio
                            } else {
                                newImageWidth = canvasHeight * imageAspectRatio
                                newImageHeight = canvasHeight
                            }

                            // 이미지뷰 크기와 이미지 크기 설정
                            imageviewLetterPaper.background = resource
                            imageviewLetterPaper.layoutParams.width = newImageWidth.toInt()
                            imageviewLetterPaper.layoutParams.height = newImageHeight.toInt()
                            imageviewLetterPaper.requestLayout()
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }

                    })
            }
        }

    }

    private fun initClickListener() = with(binding) {
        buttonPalette.setOnClickListener {
            val bottomSheetPalette = BottomSheetPalette()
            bottomSheetPalette.show(childFragmentManager, "BottomSheetPalette")
        }
    }

    private fun convertDpToPixel(dp: Float): Float {
        return if (context != null) {
            val resources = requireContext().resources
            val metrics = resources.displayMetrics

            dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        } else {
            val metrics = Resources.getSystem().displayMetrics

            dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }
}