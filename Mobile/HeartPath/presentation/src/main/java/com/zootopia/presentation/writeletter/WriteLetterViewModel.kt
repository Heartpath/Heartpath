package com.zootopia.presentation.writeletter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zootopia.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "WriteLetterViewModel"

@HiltViewModel
class WriteLetterViewModel @Inject constructor(

) : ViewModel() {

    private var _selectedLetterPaperUrl: MutableStateFlow<String> = MutableStateFlow("")
    val selectedLetterPaperUrl: StateFlow<String> = _selectedLetterPaperUrl

    private var _letterPaperList: MutableStateFlow<MutableList<String>> = MutableStateFlow(
        mutableListOf()
    )
    val letterPaperList: StateFlow<MutableList<String>> = _letterPaperList

    private var _selectedColor: MutableStateFlow<Int> = MutableStateFlow<Int>(R.color.Orange)
    val selectedColor: StateFlow<Int> = _selectedColor

    private var _penSize: MutableStateFlow<Int> = MutableStateFlow<Int>(10)
    val penSize: StateFlow<Int> = _penSize

    fun setSelectedLetterPaperUrl(url: String) {
        viewModelScope.launch {
            _selectedLetterPaperUrl.emit(url)
        }
    }

    fun getLetterPaperList() {
        //서버로 부터 편지지 리스트를 받는다.
        viewModelScope.launch {
            var list = mutableListOf<String>().apply {
                add("https://postfiles.pstatic.net/MjAyMzEwMjhfNjcg/MDAxNjk4NDI0ODE5NjI4.kPCHa288iH5JCl8arHKkxb-X5vq_zph7A8N7B6YTiJIg.56HHDL7-Jb3xGtjdMpdNnUPltZkV7HVZ0Hhk-AosBBEg.PNG.vmfpel0425/export202310280139235243.png?type=w773")
                add("https://postfiles.pstatic.net/MjAyMzEwMjhfNTMg/MDAxNjk4NDIyODU5NzQz.efoLw5ABlo7eg_dVvCB6gxLCZjvuPRMTOlc7Obt4tHMg.wzujXlvx44kykz1JfcHRikbdFCZoBeGckASWcqJjSmMg.PNG.vmfpel0425/export202310280100244537.png?type=w773")
                add("https://postfiles.pstatic.net/MjAyMzEwMjhfMTEw/MDAxNjk4NDI4MDk5MzAz.AGlsyuL5RlHfwm4c-n79IfJpDOIScnL9hu8zBIwsJhkg.63uoa5hAWNijsFU8DuSLrZdHqXxnXgsMIM9AlqTz3KAg.PNG.vmfpel0425/export202310280234128271.png?type=w773")
                add("https://postfiles.pstatic.net/MjAyMzEwMjhfNDcg/MDAxNjk4NDI5OTUzMjc0.zzPnSz3F2lcumdN_KbXvUB6vDa1qgPYvKZ2H2ntqNGEg._x8G_WEtyChmAvRDqpJi3988M6KdxNjLDgA-i5eBGPQg.PNG.vmfpel0425/export202310280304530889.png?type=w773")
                add("https://postfiles.pstatic.net/MjAyMzEwMjhfMjkx/MDAxNjk4NDMxNjA5ODE4.5DV-03qZur91-juxpIlZlHMKwJ1_YCAcAXVWBYk873Mg.keXERyWc6DbWLHhAEcuhfxZtLJEbqeMx7PvKvtYu_Rcg.PNG.vmfpel0425/export202310280332561002.png?type=w773")
                add("https://img.freepik.com/free-vector/paper-note-frame-vector-on-memphis-pattern_53876-108786.jpg?w=360&t=st=1698396316~exp=1698396916~hmac=94d23abab6ea4c081de421792add4fd65d41c77270948725a80fae01ad16e994")
                add("https://img.freepik.com/free-vector/empty-paper-on-daisy-field-patterned-background_53876-115658.jpg?w=360&t=st=1698396333~exp=1698396933~hmac=fdb3786b47d67f9256107a83d9fe7c4df5988a330311df12e6032eb87e910013")
                add("https://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2022/04/22/91193624-51b9-4b28-b3b2-bef73c770691.jpg")
                add("https://img.allurekorea.com/allure/2023/08/style_64d0bb505c802-768x1024.jpg")
                add("https://img.freepik.com/premium-photo/cute-gray-cat-kid-animal-with-interested-question-facial-face-expression-look-up-on-copy-space-small-tabby-kitten-on-white-background-vertical-format_221542-2278.jpg?w=360")
            }
            _letterPaperList.emit(list)
        }
    }

    fun setSelectedColor(id: Int) {
        viewModelScope.launch {
            _selectedColor.value = id
        }
    }
}