package com.zootopia.presentation.writeletter.typingwrite

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.ui.geometry.Rect
import androidx.core.view.marginTop
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentTypingWriteBinding
import com.zootopia.presentation.R
import com.zootopia.presentation.util.MaxLineLimitTextWatcher
import com.zootopia.presentation.writeletter.WriteLetterViewModel
import kotlinx.coroutines.launch

private const val TAG = "TypingWriteFragment"
class TypingWriteFragment : BaseFragment<FragmentTypingWriteBinding>(
    FragmentTypingWriteBinding::bind,
    R.layout.fragment_typing_write
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val writeLetterViewModel: WriteLetterViewModel by activityViewModels()
    private var imageViewHeight: Float = 0F
    private var imageViewWidth: Float = 0F

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initViewReadyListener()
        initCollecter()
        initListener()
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
                    setLetterPaper(writeLetterViewModel.selectedLetterPaperUrl.value)
                }
            })
        }
    }

    private fun initCollecter() = with(binding) {
        lifecycleScope.launch {
            writeLetterViewModel.selectedLetterPaperUrl.collect {
                Log.d(TAG, "initCollecter: url collect")
                setLetterPaper(it)
            }
        }

    }

    private fun setLetterPaper(url: String) = with(binding) {
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
                    imageviewLetterPaper.layoutParams.width = newImageWidth.toInt()
                    imageviewLetterPaper.layoutParams.height = newImageHeight.toInt()
                    imageviewLetterPaper.requestLayout()

                    val horizontalPadding = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._32sdp)
                    val topPadding = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._60sdp)
                    val bottomPadding = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._20sdp)

                    var fontHeight = edittextLetter.paint.fontMetrics.ascent - edittextLetter.paint.fontMetrics.descent
                    if(fontHeight < 0){
                        fontHeight = -fontHeight
                    }
                    val textViewHeightResult = newImageHeight.toInt()-topPadding-bottomPadding
                    val maxLineCount = (textViewHeightResult/fontHeight).toInt()
                    Log.d(TAG, "onResourceReady: ${maxLineCount}")

                    edittextLetter.apply {
                        width = newImageWidth.toInt()
                        height = newImageHeight.toInt()
                        setPadding(horizontalPadding, topPadding, horizontalPadding, bottomPadding)
                    }
                    edittextLetter.requestLayout()
                    edittextLetter.addTextChangedListener(MaxLineLimitTextWatcher(maxLineCount, edittextLetter))
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
    }

    private fun initListener() = with(binding) {

    }

    override fun onDestroy() {
        super.onDestroy()
        writeLetterViewModel.setSelectedLetterPaperUrl("")
    }
}