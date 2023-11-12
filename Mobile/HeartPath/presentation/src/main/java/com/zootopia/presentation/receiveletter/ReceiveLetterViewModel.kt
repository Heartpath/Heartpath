package com.zootopia.presentation.receiveletter

import com.zootopia.domain.model.letter.ReceiveLetterDto
import com.zootopia.domain.usecase.letter.received.GetStoredLetterListUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class ReceiveLetterViewModel @Inject constructor(
    private val getStoredLetterListUseCase: GetStoredLetterListUseCase

): BaseViewModel() {
    private val _storedLetterList = MutableSharedFlow<List<ReceiveLetterDto>>()
    var storedLetterList = _storedLetterList.asSharedFlow()

    fun getStoredLetterList() {
        getApiResult(
            block = {
                getStoredLetterListUseCase.invoke()
            },
            success = { result ->
                if (result != null) {
                    _storedLetterList.emit(result)
                }
            }
        )
    }
}
