package com.zootopia.presentation.writeletter

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zootopia.domain.model.letter.UserLetterPaperDto
import com.zootopia.domain.model.user.UserDto
import com.zootopia.domain.model.writeletter.HandLetterRequestDto
import com.zootopia.domain.usecase.writeletter.GetUserLetterPaperUseCase
import com.zootopia.domain.usecase.writeletter.PostHandLetterUseCase
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "WriteLetterViewModel"

@HiltViewModel
class WriteLetterViewModel @Inject constructor(
    private val getUserLetterPaperUseCase: GetUserLetterPaperUseCase,
    private val postHandLetterUseCase: PostHandLetterUseCase
) : BaseViewModel() {

    private var _selectedLetterPaperUrl: MutableStateFlow<String> = MutableStateFlow("")
    val selectedLetterPaperUrl: StateFlow<String> = _selectedLetterPaperUrl

    private var _letterPaperList: MutableStateFlow<MutableList<UserLetterPaperDto>> =
        MutableStateFlow(
            mutableListOf()
        )
    val letterPaperList: StateFlow<MutableList<UserLetterPaperDto>> = _letterPaperList

    private var _selectedColor: MutableStateFlow<Int> = MutableStateFlow<Int>(R.color.black)
    val selectedColor: StateFlow<Int> = _selectedColor

    private var _penSize: MutableStateFlow<Float> = MutableStateFlow<Float>(10F)
    val penSize: StateFlow<Float> = _penSize

    private var _isEraserSelected: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val isEraserSelected: StateFlow<Boolean> = _isEraserSelected

    private var _searchedUserList: MutableStateFlow<MutableList<UserDto>> =
        MutableStateFlow(mutableListOf<UserDto>())
    val searchedUserList: StateFlow<MutableList<UserDto>> = _searchedUserList

    private var _selectedUser: MutableStateFlow<UserDto> =
        MutableStateFlow<UserDto>(UserDto("", "", ""))
    val selectedUser: StateFlow<UserDto> = _selectedUser

    private val _drawingBitmap = MutableStateFlow<Bitmap?>(null)
    val drawingBitmap: StateFlow<Bitmap?> = _drawingBitmap

    private val _letterPaperWidth = MutableStateFlow<Float>(0F)
    val letterPaperWith = _letterPaperWidth

    private val _letterPaperHeight = MutableStateFlow<Float>(0F)
    val letterPaperHeight = _letterPaperHeight

    private val _penBitmap = MutableStateFlow<Bitmap?>(null)
    var penBitmap: StateFlow<Bitmap?> = _penBitmap

    private val _imageList = MutableStateFlow<MutableList<Uri>>(mutableListOf())
    var imageList: StateFlow<MutableList<Uri>> = _imageList

    private val _isSendSuccess = MutableStateFlow<Boolean>(false)
    var isSendSuccess: StateFlow<Boolean> = _isSendSuccess

    init {
        resetBitmap()
    }

    fun resetBitmap() {
        _drawingBitmap.value = null
        _penBitmap.value = null
    }

    fun setSelectedLetterPaperUrl(url: String) {
        viewModelScope.launch {
            _selectedLetterPaperUrl.value = url
        }
    }

    fun setPenSize(size: Float) {
        viewModelScope.launch {
            _penSize.value = size
        }
    }

    fun setEraserState(isEraserSelected: Boolean) {
        viewModelScope.launch {
            _isEraserSelected.value = isEraserSelected
        }
    }

    fun setLetterPaperSize(width: Float, height: Float) {
        viewModelScope.launch {
            _letterPaperWidth.value = width
            _letterPaperHeight.value = height
        }
    }

    fun getLetterPaperList() {
        //서버로 부터 편지지 리스트를 받는다.
//        getApiResult(
//            block = {
//                getUserLetterPaperUseCase.invoke()
//            },
//            success = {
//                _letterPaperList.emit(it)
//            }
//        )
        viewModelScope.launch {
            var list = mutableListOf<UserLetterPaperDto>().apply {
                add(
                    UserLetterPaperDto(
                        0,
                        "d",
                        12,
                        "https://postfiles.pstatic.net/MjAyMzEwMjhfNjcg/MDAxNjk4NDI0ODE5NjI4.kPCHa288iH5JCl8arHKkxb-X5vq_zph7A8N7B6YTiJIg.56HHDL7-Jb3xGtjdMpdNnUPltZkV7HVZ0Hhk-AosBBEg.PNG.vmfpel0425/export202310280139235243.png?type=w773",
                        false
                    )
                )
            }
            _letterPaperList.emit(list)
        }
    }

    fun searchUser(searchKeyWord: String) {
        //서버로 부터 유저 검색 결과를 받는다
        viewModelScope.launch {
            var list = mutableListOf<UserDto>().apply {
                add(UserDto("dodo2504", "도연쓰", "https://picsum.photos/id/237/200/300"))
                add(UserDto("dodo2504_2", "도연쓰2", "https://picsum.photos/id/237/200/300"))
                add(UserDto("dodo2504_3", "도연쓰3", "https://picsum.photos/id/237/200/300"))
                add(UserDto("dodo2504_4", "도연쓰4", "https://picsum.photos/id/237/200/300"))
            }
            _searchedUserList.emit(list)
        }
    }

    fun setSelectedUser(user: UserDto) {
        viewModelScope.launch {
            _selectedUser.emit(user)
        }
    }

    fun setSelectedColor(id: Int) {
        viewModelScope.launch {
            _selectedColor.value = id
        }
    }

    fun setDrawingBitmap(bitmap: Bitmap) {
        _drawingBitmap.value = bitmap
    }

    fun setPenBitmap(bitmap: Bitmap) {
        _penBitmap.value = bitmap
    }

    fun addImageList(list: MutableList<Uri>) { //리스트에 이미지를 추가함
        viewModelScope.launch {
            val newList = mutableListOf<Uri>()

            _imageList.value.forEachIndexed { index, uri ->
                newList.add(uri)
            }
            list.forEachIndexed { index, uri ->
                newList.add(uri)
            }

            Log.d(TAG, "setImageList: ${newList.size}")
            _imageList.emit(newList)
        }
    }

    fun setImageList(list: MutableList<Uri>) { //리스트를 새로 갈아줌
        val newImageList = mutableListOf<Uri>()
        list.forEachIndexed { index, uri ->
            newImageList.add(uri)
        }
        _imageList.value = newImageList
    }

    fun resetImageList() {
        _imageList.value = mutableListOf()
    }

    fun resetIsSendSuccess() {
        _isSendSuccess.value = false
    }

    fun saveLetter(contentUri: String, imageList: MutableList<String>) {

        getApiResult(
            block = {
                postHandLetterUseCase.invoke(
                    handLetterRequestDto = HandLetterRequestDto(_selectedUser.value.memberId),
                    content = contentUri,
                    fileList = imageList
                )
            },
            success = {
                _isSendSuccess.value = true
            }
        )
    }
}