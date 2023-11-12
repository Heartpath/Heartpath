package com.zootopia.presentation.store

import com.zootopia.domain.model.store.BuyStoreCharacterRequestDto
import com.zootopia.domain.model.store.BuyStoreLetterPaperRequestDto
import com.zootopia.domain.model.store.StoreCharacterDto
import com.zootopia.domain.model.store.StoreItemLetterPaperDto
import com.zootopia.domain.model.user.UserInfoDto
import com.zootopia.domain.usecase.store.BuyStoreCharacterUseCase
import com.zootopia.domain.usecase.store.BuyStoreLetterPaperUseCase
import com.zootopia.domain.usecase.store.GetStoreCharacterListUseCase
import com.zootopia.domain.usecase.store.GetStoreItemLetterPaperListUseCase
import com.zootopia.domain.usecase.user.GetUserInfoUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getStoreCharacterListUseCase: GetStoreCharacterListUseCase,
    private val getStoreItemLetterPaperUseCase: GetStoreItemLetterPaperListUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val buyStoreCharacterUseCase: BuyStoreCharacterUseCase,
    private val buyStoreLetterPaperUseCase: BuyStoreLetterPaperUseCase
) : BaseViewModel() {

    private val _userInfo = MutableStateFlow(UserInfoDto())
    var userInfo = _userInfo.asStateFlow()

    private var _storeCharacterList =
        MutableStateFlow<MutableList<StoreCharacterDto>>(mutableListOf())
    val storeCharacterList: StateFlow<MutableList<StoreCharacterDto>> = _storeCharacterList

    private var _storeLetterPaperList =
        MutableStateFlow<MutableList<StoreItemLetterPaperDto>>(mutableListOf())
    val storeLetterPaperList: StateFlow<MutableList<StoreItemLetterPaperDto>> =
        _storeLetterPaperList

    fun getUserInfo(){
        getApiResult(
            block = {
                getUserInfoUseCase.invoke()
            },
            success = {
                if(it != null){
                    _userInfo.emit(it)
                }
            }
        )
    }

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
        getApiResult(
            block = {
                getStoreItemLetterPaperUseCase.invoke()
            },
            success = {
                _storeLetterPaperList.emit(it)
            }
        )
    }

    fun buyStoreCharacter(characterId: Int){
        getApiResult(
            block = {
                buyStoreCharacterUseCase.invoke(
                    BuyStoreCharacterRequestDto(characterId)
                )
            },
            success = {
                getStoreCharacterList()
                getUserInfo()
            }
        )
    }

    fun buyStoreLetterPaper(letterPaper: Int){
        getApiResult(
            block = {
                buyStoreLetterPaperUseCase.invoke(
                    BuyStoreLetterPaperRequestDto(letterPaper)
                )
            },
            success = {
                getStoreLetterPaperList()
                getUserInfo()
            }
        )
    }

}