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
                add("https://img.freepik.com/free-vector/blank-rectangle-gold-frame-on-pink-background-template-vector_53876-136483.jpg?w=360&t=st=1698396216~exp=1698396816~hmac=493c55aaf2c74d7fa7f212227714a33533b48d4bfd139b58e3cd3b728e979f64")
                add("https://img.freepik.com/free-vector/abstract-botanical-frame-template-vector_53876-136489.jpg?w=360&t=st=1698396237~exp=1698396837~hmac=a6ae72a5fbe0160a9e79bcd0f84af42fd7253eaa6c0ca0e88e81c0e33d8d3db0")
                add("https://img.freepik.com/free-vector/terrazzo-frame-on-beige-background_53876-100754.jpg?w=360&t=st=1698396277~exp=1698396877~hmac=f3754740f3daecf172aaa95a8a10ac21520e15557942b0a751ad894a0d45c1f2")
                add("https://img.freepik.com/free-vector/cute-colorful-illustrated-star-frame-on-a-beige-background_53876-115059.jpg?w=360&t=st=1698396256~exp=1698396856~hmac=3de671417b53b9be68eddc6acfc2b825b2fbc6703476eb9845898a8c398e3ebe")
                add("https://img.freepik.com/premium-vector/doodle-nature-patterned-frame-vector_53876-166757.jpg?w=360")
                add("https://img.freepik.com/free-vector/paper-note-frame-vector-on-memphis-pattern_53876-108786.jpg?w=360&t=st=1698396316~exp=1698396916~hmac=94d23abab6ea4c081de421792add4fd65d41c77270948725a80fae01ad16e994")
                add("https://img.freepik.com/free-vector/empty-paper-on-daisy-field-patterned-background_53876-115658.jpg?w=360&t=st=1698396333~exp=1698396933~hmac=fdb3786b47d67f9256107a83d9fe7c4df5988a330311df12e6032eb87e910013")
                add("https://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2022/04/22/91193624-51b9-4b28-b3b2-bef73c770691.jpg")
                add("https://img.allurekorea.com/allure/2023/08/style_64d0bb505c802-768x1024.jpg")
                add("https://img.freepik.com/premium-photo/cute-gray-cat-kid-animal-with-interested-question-facial-face-expression-look-up-on-copy-space-small-tabby-kitten-on-white-background-vertical-format_221542-2278.jpg?w=360")
            }
            _letterPaperList.emit(list)
        }
    }
}