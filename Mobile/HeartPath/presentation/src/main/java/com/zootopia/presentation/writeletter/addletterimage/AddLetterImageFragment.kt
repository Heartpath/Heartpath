package com.zootopia.presentation.writeletter.addletterimage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import com.zootopia.presentation.util.getRealPathFromUri
import com.zootopia.presentation.util.hasPermissions
import com.zootopia.presentation.util.requestPermissionsOnClick
import com.zootopia.presentation.util.saveImageToGallery
import com.zootopia.presentation.writeletter.WriteLetterViewModel
import com.zootopia.presentation.writeletter.handwrite.HandWriteFragmentDirections
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
                    imageList.removeAt(index)
                    writeLetterViewModel.setImageList(imageList)
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
                Log.d(TAG, "initCollecter: ${it.size}")
                imageList.clear()
                imageList.addAll(it)
                addLetterImageAdapter.notifyDataSetChanged()
            }
        }
        lifecycleScope.launch {
            writeLetterViewModel.isSendSuccess.collectLatest {
                if (it) {
                    writeLetterViewModel.resetIsSendSuccess()
                    Toast.makeText(mainActivity, R.string.add_letter_image_write_success, Toast.LENGTH_LONG).show()
                    navController.navigate(AddLetterImageFragmentDirections.actionAddLetterImageFragmentToHomeFragment())
                }
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
                val intent = Intent()
                intent.setType("image/*")
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.action = Intent.ACTION_GET_CONTENT

                selectImagesActivityResult.launch(intent)
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

    val selectImagesActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val list = mutableListOf<Uri>()
                val data: Intent? = result.data
                //If multiple image selected
                if (data?.clipData != null) {
                    val count = data.clipData?.itemCount ?: 0
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        list.add(imageUri)
                    }
                }
                //If single image selected
                else if (data?.data != null) {
                    val imageUri: Uri? = data.data
                    if (imageUri != null) {
                        list.add(imageUri)
                    }
                }
                writeLetterViewModel.addImageList(list)
            }
        }
}