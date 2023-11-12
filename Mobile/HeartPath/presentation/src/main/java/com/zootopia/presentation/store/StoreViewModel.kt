package com.zootopia.presentation.store

import androidx.lifecycle.viewModelScope
import com.zootopia.domain.model.store.StoreCharacterDto
import com.zootopia.domain.model.store.StoreLetterPaperDto
import com.zootopia.presentation.config.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StoreViewModel : BaseViewModel() {
    private var _storeCharacterList =
        MutableStateFlow<MutableList<StoreCharacterDto>>(mutableListOf())
    val storeCharacterList: StateFlow<MutableList<StoreCharacterDto>> = _storeCharacterList

    private var _storeLetterPaperList =
        MutableStateFlow<MutableList<StoreLetterPaperDto>>(mutableListOf())
    val storeLetterPaperList: StateFlow<MutableList<StoreLetterPaperDto>> = _storeLetterPaperList

    fun getStoreCharacterList() {
        viewModelScope.launch {
            val list = mutableListOf<StoreCharacterDto>().apply {
                add(
                    StoreCharacterDto(
                        0,
                        "뱁새1",
                        100,
                        "뱁새1임",
                        "https://zootopia-s3.s3.ap-northeast-2.amazonaws.com/crowtit/98bb8f06-4233-4678-9ebd-50ae629cf780.%EA%B3%B5%ED%95%99_%EB%B1%81%EC%83%88.png",
                        false
                    )

                )
                add(
                    StoreCharacterDto(
                        0,
                        "뱁새1",
                        100,
                        "뱁새1임",
                        "https://zootopia-s3.s3.ap-northeast-2.amazonaws.com/crowtit/98bb8f06-4233-4678-9ebd-50ae629cf780.%EA%B3%B5%ED%95%99_%EB%B1%81%EC%83%88.png",
                        false
                    )

                )
                add(
                    StoreCharacterDto(
                        0,
                        "뱁새1",
                        100,
                        "뱁새1임",
                        "https://zootopia-s3.s3.ap-northeast-2.amazonaws.com/crowtit/98bb8f06-4233-4678-9ebd-50ae629cf780.%EA%B3%B5%ED%95%99_%EB%B1%81%EC%83%88.png",
                        false
                    )

                )
            }
            _storeCharacterList.emit(list)
        }
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