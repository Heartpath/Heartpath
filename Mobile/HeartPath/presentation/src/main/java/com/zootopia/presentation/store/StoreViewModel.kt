package com.zootopia.presentation.store

import androidx.lifecycle.viewModelScope
import com.zootopia.domain.model.store.StoreCharacterDto
import com.zootopia.domain.model.store.StoreLetterPaperDto
import com.zootopia.domain.usecase.store.GetStoreCharacterListUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getStoreCharacterListUseCase: GetStoreCharacterListUseCase
): BaseViewModel() {
    private var _storeCharacterList =
        MutableStateFlow<MutableList<StoreCharacterDto>>(mutableListOf())
    val storeCharacterList: StateFlow<MutableList<StoreCharacterDto>> = _storeCharacterList

    private var _storeLetterPaperList =
        MutableStateFlow<MutableList<StoreLetterPaperDto>>(mutableListOf())
    val storeLetterPaperList: StateFlow<MutableList<StoreLetterPaperDto>> = _storeLetterPaperList

    fun getStoreCharacterList() {
        getApiResult(
            block = {
                getStoreCharacterListUseCase.invoke()
            },
            success = {
                _storeCharacterList.emit(it)
            }
        )
    }

    fun getStoreLetterPaperList() {
        viewModelScope.launch {
            val list = mutableListOf<StoreLetterPaperDto>().apply {
                add(
                    StoreLetterPaperDto(
                        0,
                        "편지1",
                        100,
                        "편지1임",
                        "https://zootopia-s3.s3.ap-northeast-2.amazonaws.com/letterpapaer/c5811a03-5987-4285-a214-987215186530.%EA%B0%90%EA%B7%A4.png",
                        false
                    )

                )
                add(
                    StoreLetterPaperDto(
                        0,
                        "편지2",
                        100,
                        "편지2임",
                        "https://zootopia-s3.s3.ap-northeast-2.amazonaws.com/letterpapaer/c5811a03-5987-4285-a214-987215186530.%EA%B0%90%EA%B7%A4.png",
                        false
                    )

                )
                add(
                    StoreLetterPaperDto(
                        0,
                        "편지3",
                        100,
                        "편지3임",
                        "https://zootopia-s3.s3.ap-northeast-2.amazonaws.com/letterpapaer/c5811a03-5987-4285-a214-987215186530.%EA%B0%90%EA%B7%A4.png",
                        false
                    )

                )
            }
            _storeLetterPaperList.emit(list)
        }
    }

}