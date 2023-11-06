package com.zootopia.presentation.writeletter.addletterimage

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentAddLetterImageBinding
import com.zootopia.presentation.writeletter.WriteLetterViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddLetterImageFragment : BaseFragment<FragmentAddLetterImageBinding>(
    FragmentAddLetterImageBinding::bind,
    R.layout.fragment_add_letter_image
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val writeLetterViewModel: WriteLetterViewModel by activityViewModels()

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
        lifecycleScope.launch {
            writeLetterViewModel.drawingBitmap.collectLatest {
                Glide.with(mainActivity)
                    .load(it) // Bitmap 객체를 로드합니다.
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 디스크 캐시를 사용하지 않도록 설정 (선택사항)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                    .into(binding.imageviewLetterPaperConfirm) // ImageView에 설정합니다.
            }
        }
    }

    private fun initClickListener() = with(binding) {
        buttonLetterConfirm.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}