package com.zootopia.presentation.readletter

import androidx.lifecycle.viewModelScope
import com.zootopia.domain.model.letter.ReadLetterDto
import com.zootopia.domain.usecase.user.AddFriendUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadLetterViewModel @Inject constructor(
    private val addFriendUseCase: AddFriendUseCase,
): BaseViewModel(){

    private val _checkFriendCnt = MutableStateFlow(1)
    var checkFriendCnt = _checkFriendCnt.asStateFlow()

    private val _readLetterResult = MutableStateFlow(ReadLetterDto())
    var readLetterResult = _readLetterResult.asStateFlow()

    fun setCheckFriendCnt() = viewModelScope.launch {
        _checkFriendCnt.value -= 1
    }

    fun addAsFriend() {
        getApiResult(
            block = {
                addFriendUseCase.invoke(readLetterResult.value.sender)
            },
            success = {

            }
        )
    }

    fun getReadLetter() {
        getApiResult(
            block = {

            },
            success = {

            }
        )
    }

}