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

    private var _letterPaperList: MutableStateFlow<MutableList<UserLetterPaperDto>> = MutableStateFlow(
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

    fun resetBitmap(){
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

    fun setLetterPaperSize(width: Float, height: Float){
        viewModelScope.launch {
            _letterPaperWidth.value = width
            _letterPaperHeight.value = height
        }
    }

    fun getLetterPaperList() {
        //서버로 부터 편지지 리스트를 받는다.
        getApiResult(
            block = {
                getUserLetterPaperUseCase.invoke()
            },
            success = {
                _letterPaperList.emit(it)
            }
        )
//        viewModelScope.launch {
//            var list = mutableListOf<String>().apply {
//                add("https://postfiles.pstatic.net/MjAyMzEwMjhfNjcg/MDAxNjk4NDI0ODE5NjI4.kPCHa288iH5JCl8arHKkxb-X5vq_zph7A8N7B6YTiJIg.56HHDL7-Jb3xGtjdMpdNnUPltZkV7HVZ0Hhk-AosBBEg.PNG.vmfpel0425/export202310280139235243.png?type=w773")
//                add("https://postfiles.pstatic.net/MjAyMzEwMjhfNTMg/MDAxNjk4NDIyODU5NzQz.efoLw5ABlo7eg_dVvCB6gxLCZjvuPRMTOlc7Obt4tHMg.wzujXlvx44kykz1JfcHRikbdFCZoBeGckASWcqJjSmMg.PNG.vmfpel0425/export202310280100244537.png?type=w773")
//                add("https://postfiles.pstatic.net/MjAyMzEwMjhfMTEw/MDAxNjk4NDI4MDk5MzAz.AGlsyuL5RlHfwm4c-n79IfJpDOIScnL9hu8zBIwsJhkg.63uoa5hAWNijsFU8DuSLrZdHqXxnXgsMIM9AlqTz3KAg.PNG.vmfpel0425/export202310280234128271.png?type=w773")
//                add("https://postfiles.pstatic.net/MjAyMzEwMjhfNDcg/MDAxNjk4NDI5OTUzMjc0.zzPnSz3F2lcumdN_KbXvUB6vDa1qgPYvKZ2H2ntqNGEg._x8G_WEtyChmAvRDqpJi3988M6KdxNjLDgA-i5eBGPQg.PNG.vmfpel0425/export202310280304530889.png?type=w773")
//                add("https://postfiles.pstatic.net/MjAyMzEwMjhfMjkx/MDAxNjk4NDMxNjA5ODE4.5DV-03qZur91-juxpIlZlHMKwJ1_YCAcAXVWBYk873Mg.keXERyWc6DbWLHhAEcuhfxZtLJEbqeMx7PvKvtYu_Rcg.PNG.vmfpel0425/export202310280332561002.png?type=w773")
//                add("https://img.freepik.com/free-vector/paper-note-frame-vector-on-memphis-pattern_53876-108786.jpg?w=360&t=st=1698396316~exp=1698396916~hmac=94d23abab6ea4c081de421792add4fd65d41c77270948725a80fae01ad16e994")
//                add("https://img.freepik.com/free-vector/empty-paper-on-daisy-field-patterned-background_53876-115658.jpg?w=360&t=st=1698396333~exp=1698396933~hmac=fdb3786b47d67f9256107a83d9fe7c4df5988a330311df12e6032eb87e910013")
//                add("https://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2022/04/22/91193624-51b9-4b28-b3b2-bef73c770691.jpg")
//                add("https://img.allurekorea.com/allure/2023/08/style_64d0bb505c802-768x1024.jpg")
//                add("https://img.freepik.com/premium-photo/cute-gray-cat-kid-animal-with-interested-question-facial-face-expression-look-up-on-copy-space-small-tabby-kitten-on-white-background-vertical-format_221542-2278.jpg?w=360")
//            }
//            _letterPaperList.emit(list)
//        }
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

    fun setPenBitmap(bitmap: Bitmap){
        _penBitmap.value = bitmap
    }

    fun addImageList(list: MutableList<Uri>){ //리스트에 이미지를 추가함
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

    fun setImageList(list: MutableList<Uri>){ //리스트를 새로 갈아줌
        val newImageList = mutableListOf<Uri>()
        list.forEachIndexed { index, uri ->
            newImageList.add(uri)
        }
        _imageList.value = newImageList
    }

    fun resetImageList(){
        _imageList.value = mutableListOf()
    }

    fun resetIsSendSuccess(){
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