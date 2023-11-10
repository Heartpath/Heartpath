package com.zootopia.presentation.writeletter.addletterimage

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.MainViewModel
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentAddLetterImageBinding
import com.zootopia.presentation.util.getImages
import com.zootopia.presentation.util.getRealPathFromUri
import com.zootopia.presentation.util.hasPermissions
import com.zootopia.presentation.util.requestPermissionsOnClick
import com.zootopia.presentation.util.saveImageToGallery
import com.zootopia.presentation.writeletter.WriteLetterViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "AddLetterImageFragment_HP"

class AddLetterImageFragment : BaseFragment<FragmentAddLetterImageBinding>(
    FragmentAddLetterImageBinding::bind,
    R.layout.fragment_add_letter_image
) {
    private lateinit var mainActivity: MainActivity
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var navController: NavController
    private val writeLetterViewModel: WriteLetterViewModel by activityViewModels()
    private lateinit var addLetterImageAdapter: AddLetterImageAdapter
    private var imageList: MutableList<Uri> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initRecyclerView()
        initCollecter()
        initClickListener()
    }

    private fun initRecyclerView() = with(binding) {
        addLetterImageAdapter = AddLetterImageAdapter(imageList)
        addLetterImageAdapter.deleteClickListener =
            object : AddLetterImageAdapter.DeleteClickListener {
                override fun deleteClick(index: Int) {
                    TODO("삭제 기능 넣을것")
                }
            }

        recyclerviewAddImage.apply {
            adapter = addLetterImageAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
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
        lifecycleScope.launch {
            writeLetterViewModel.imageList.collectLatest {
                imageList.clear()
                imageList.addAll(it)
                addLetterImageAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initClickListener() = with(binding) {
        buttonLetterConfirm.setOnClickListener {
            if (writeLetterViewModel.drawingBitmap.value != null) {
                val letterUri =
                    saveImageToGallery(mainActivity, writeLetterViewModel.drawingBitmap.value!!)
                if (letterUri != null) {
                    val realPath = getRealPathFromUri(mainActivity, letterUri)
                    if (realPath != null)
                        writeLetterViewModel.saveLetter(realPath, mutableListOf())
                }
            }
        }

        buttonAddImage.setOnClickListener {

            val permission =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    READ_MEDIA_IMAGES
                else READ_EXTERNAL_STORAGE

            if (mainActivity.hasPermissions(permission)) {
                //퍼미션 받은 경우
                val imageList = getImages(mainActivity)

            } else {
                //퍼미션 없는 경우
                requestPermissionsOnClick(
                    activity = mainActivity,
                    mainViewModel = mainViewModel,
                    permissionList = arrayOf(permission)
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        val READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE
        val READ_MEDIA_IMAGES = android.Manifest.permission.READ_MEDIA_IMAGES
    }
}