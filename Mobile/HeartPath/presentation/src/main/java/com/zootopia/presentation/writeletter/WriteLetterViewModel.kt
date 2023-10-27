package com.zootopia.presentation.writeletter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zootopia.presentation.util.LetterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteLetterViewModel @Inject constructor(

) : ViewModel() {
    private var _selectedLetterType: LetterType = LetterType.HAND_WRITE
    val selectedLetterType: LetterType = _selectedLetterType

    private var _selectedLetterPaperId: Int = 0
    val selectedLetterPaperId: Int = _selectedLetterPaperId

    private var _letterPaperList: MutableStateFlow<MutableList<String>> = MutableStateFlow(
        mutableListOf()
    )
    val letterPaperList: StateFlow<MutableList<String>> = _letterPaperList

    fun setSelectedLetterType(type: LetterType) {
        _selectedLetterType = type
    }

    fun setSelectedLetterPaperId(id: Int) {
        _selectedLetterPaperId = id
    }

    fun getLetterPaperList(){
        //서버로 부터 편지지 리스트를 받는다.
        viewModelScope.launch {
            var list = mutableListOf<String>().apply {
                add("https://picsum.photos/id/237/400/600")
                add("https://picsum.photos/id/238/400/600")
                add("https://picsum.photos/id/239/400/600")
                add("https://picsum.photos/id/240/400/600")
                add("https://picsum.photos/id/241/400/600")
                add("https://picsum.photos/id/242/400/600")
                add("https://picsum.photos/id/243/400/600")
                add("https://picsum.photos/id/244/400/600")
                add("https://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2022/04/22/91193624-51b9-4b28-b3b2-bef73c770691.jpg")
            }
            _letterPaperList.emit(list)
        }
    }
}