package com.zootopia.presentation.writeletter.handwrite

import android.content.Context
import android.content.Intent.getIntent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentHandWriteBinding
import com.zootopia.presentation.util.LetterType
import com.zootopia.presentation.util.viewToBitmap
import com.zootopia.presentation.writeletter.WriteLetterViewModel
import com.zootopia.presentation.writeletter.typingwrite.TypingWriteFragmentArgs
import kotlinx.coroutines.launch


private const val TAG = "HandWriteFragment_HP"

class HandWriteFragment : BaseFragment<FragmentHandWriteBinding>(
    FragmentHandWriteBinding::bind,
    R.layout.fragment_hand_write
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val writeLetterViewModel: WriteLetterViewModel by activityViewModels()
    private var imageViewHeight: Float = 0F
    private var imageViewWidth: Float = 0F
    private var bmp: Bitmap? = null
    private val args: HandWriteFragmentArgs by navArgs()
    private var url = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        url = args.letterUrl
        initViewReadyListener()
        initCollecter()
        initClickListener()
    }

    private fun initViewReadyListener() {
        var viewTreeObserver: ViewTreeObserver = binding.imageviewLetterPaper.getViewTreeObserver()
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    Log.d(TAG, "onGlobalLayout: view ready")
                    binding.imageviewLetterPaper.getViewTreeObserver()
                        .removeOnGlobalLayoutListener(this)
                    imageViewHeight = binding.imageviewLetterPaper.getHeight().toFloat()
                    imageViewWidth = binding.imageviewLetterPaper.getWidth().toFloat()
                    setLetterPaper()
                }
            })
        }
    }

    private fun initCollecter() = with(binding) {

    }

    private fun setLetterPaper() = with(binding) {

        Glide.with(mainActivity).load(url)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    // 이미지의 너비와 높이를 가져옴
                    val imageWidth = resource.intrinsicWidth.toFloat()
                    val imageHeight = resource.intrinsicHeight.toFloat()

                    // 이미지의 비율을 계산
                    val imageAspectRatio = imageWidth / imageHeight

                    // 이미지의 비율에 따라 캔버스에 맞게 크기 조정
                    val newImageWidth: Float
                    val newImageHeight: Float
                    if (imageAspectRatio > imageViewWidth / imageViewHeight) {
                        newImageWidth = imageViewWidth
                        newImageHeight = imageViewWidth / imageAspectRatio
                    } else {
                        newImageWidth = imageViewHeight * imageAspectRatio
                        newImageHeight = imageViewHeight
                    }

                    // 이미지뷰 크기와 이미지 크기 설정
                    imageviewLetterPaper.background = resource
                    writeLetterViewModel.setLetterPaperSize(newImageWidth, newImageHeight)
                    imageviewLetterPaper.layoutParams.width = newImageWidth.toInt()
                    imageviewLetterPaper.layoutParams.height = newImageHeight.toInt()
                    imageviewLetterPaper.requestLayout()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })


       
        
    }

    private fun initClickListener() = with(binding) {
        buttonPalette.setOnClickListener {
            val bottomSheetPalette = BottomSheetPalette()
            bottomSheetPalette.show(childFragmentManager, "BottomSheetPalette")
        }
        buttonSave.setOnClickListener {
            bmp = viewToBitmap(imageviewLetterPaper)
            writeLetterViewModel.setDrawingBitmap(bmp!!)
            navController.navigate(HandWriteFragmentDirections.actionHandWriteFragmentToAddLetterImageFragment(LetterType.HAND_WRITE))
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        writeLetterViewModel.setSelectedColor(R.color.black)
        writeLetterViewModel.setSelectedLetterPaperUrl("")
        writeLetterViewModel.setPenSize(10f)
        writeLetterViewModel.setEraserState(false)
        writeLetterViewModel.resetBitmap()
        writeLetterViewModel.resetImageList()
    }
}