package com.zootopia.presentation.writeletter

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zootopia.domain.model.letter.UserLetterPaperDto
import com.zootopia.domain.model.user.SearchUserInfoDto
import com.zootopia.domain.model.writeletter.HandLetterRequestDto
import com.zootopia.domain.model.writeletter.TypingLetterRequestDto
import com.zootopia.domain.usecase.user.SearchUserUseCase
import com.zootopia.domain.usecase.writeletter.GetUserLetterPaperUseCase
import com.zootopia.domain.usecase.writeletter.PostHandLetterUseCase
import com.zootopia.domain.usecase.writeletter.PostTypingLetterUseCase
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
    private val postHandLetterUseCase: PostHandLetterUseCase,
    private val postTypingLetterUseCase: PostTypingLetterUseCase,
    private val searchUserUseCase: SearchUserUseCase
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

    private var _searchedUserList: MutableStateFlow<List<SearchUserInfoDto>> =
        MutableStateFlow(mutableListOf<SearchUserInfoDto>())
    val searchedUserList: StateFlow<List<SearchUserInfoDto>> = _searchedUserList

    private var _selectedUser: MutableStateFlow<SearchUserInfoDto> =
        MutableStateFlow<SearchUserInfoDto>(SearchUserInfoDto("", "", "", false))
    val selectedUser: StateFlow<SearchUserInfoDto> = _selectedUser

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

    private val _letterText = MutableStateFlow<String>("")
    var letterText: StateFlow<String> = _letterText

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
        getApiResult(
            block = {
                getUserLetterPaperUseCase.invoke()
            },
            success = {
                _letterPaperList.emit(it)
            }
        )
    }

    fun searchUser(searchKeyWord: String, limit: Int) {
        getApiResult(
            block = {
                searchUserUseCase.invoke(
                    id = searchKeyWord,
                    limit = limit
                )
            },
            success = {
                _searchedUserList.emit(it)
            }
        )
//        viewModelScope.launch {
//            var list = mutableListOf<SearchUserInfoDto>().apply {
//                add(SearchUserInfoDto("dodo2504", "도연쓰", "https://picsum.photos/id/237/200/300"))
//                add(SearchUserInfoDto("dodo2504_2", "도연쓰2", "https://picsum.photos/id/237/200/300"))
//                add(SearchUserInfoDto("dodo2504_3", "도연쓰3", "https://picsum.photos/id/237/200/300"))
//                add(SearchUserInfoDto("dodo2504_4", "도연쓰4", "https://picsum.photos/id/237/200/300"))
//            }
//            _searchedUserList.emit(list)
//        }
    }

    fun setSelectedUser(user: SearchUserInfoDto) {
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

    fun setLetterText(text: String){
        _letterText.value = text
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

    fun resetLetterText(){
        _letterText.value = ""
    }

    fun saveHandWriteLetter(contentUri: String, imageList: MutableList<String>) {

        getApiResult(
            block = {
                postHandLetterUseCase.invoke(
                    handLetterRequestDto = HandLetterRequestDto(_selectedUser.value.memberID),
                    content = contentUri,
                    fileList = imageList
                )
            },
            success = {
                _isSendSuccess.value = true
            }
        )
    }

    fun saveTypingWriteLetter(contentUri: String, imageList: MutableList<String>, text: String){
        getApiResult(
            block = {
                postTypingLetterUseCase.invoke(
                    typingLetterRequestDto = TypingLetterRequestDto(_selectedUser.value.memberID, text),
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