package com.zootopia.presentation.readletter

import androidx.lifecycle.viewModelScope
import com.zootopia.domain.model.letter.ReadLetterDto
import com.zootopia.domain.usecase.letter.received.GetLetterToReadUseCase
import com.zootopia.domain.usecase.user.AddFriendUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadLetterViewModel @Inject constructor(
    private val addFriendUseCase: AddFriendUseCase,
    private val getLetterToReadUseCase: GetLetterToReadUseCase,
) : BaseViewModel() {

    private val _checkFriendCnt = MutableStateFlow(1)
    var checkFriendCnt = _checkFriendCnt.asStateFlow()

    private val _readLetterResult = MutableStateFlow(ReadLetterDto())
    var readLetterResult = _readLetterResult.asStateFlow()

    private val _addFriendResult = MutableStateFlow("")
    var addFriendResult = _addFriendResult.asStateFlow()

    private val _letterList = MutableSharedFlow<List<String>>()
    var letterList = _letterList.asSharedFlow()

    private val _imageCnt = MutableStateFlow(0)
    var imageCnt = _imageCnt.asStateFlow()

    fun setCheckFriendCnt() = viewModelScope.launch {
        _checkFriendCnt.value -= 1
    }

    fun addAsFriend() {
        getApiResult(
            block = {
                addFriendUseCase.invoke(readLetterResult.value.sender)
            },
            success = { result ->
                _addFriendResult.emit(result)
                setCheckFriendCnt()
            }
        )
    }

    fun getReadLetter(id: Int) {
        getApiResult(
            block = {
                getLetterToReadUseCase.invoke(letterId = id)
            },
            success = { result ->
                if (result != null) {
                    _readLetterResult.emit(result)
                    setLetterImgList()
                }
            }
        )
    }

    fun clearLetterResult() = viewModelScope.launch {
        _readLetterResult.emit(ReadLetterDto())
    }

    fun setLetterImgList() = viewModelScope.launch {
        readLetterResult.collect {
            val tmpList = mutableListOf<String>()
            // 먼저 it.content를 추가
            it.content?.let {
                tmpList.add(it)
            }

            it.files?.let { files ->
                tmpList.addAll(files)
            }
            _letterList.emit(tmpList)
            _imageCnt.emit(it.files.size)
        }
    }
}