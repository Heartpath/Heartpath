package com.zootopia.presentation.characterencyclopedia

import android.util.Log
import com.zootopia.domain.model.store.ChangeMainCharacterRequestDto
import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.domain.usecase.store.ChangeMainCharacterUseCase
import com.zootopia.domain.usecase.store.GetCharacterEncyclopediaListUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val TAG = "CharacterEncyclopediaVi"
@HiltViewModel
class CharacterEncyclopediaViewModel @Inject constructor(
    private val getCharacterEncyclopediaListUseCase: GetCharacterEncyclopediaListUseCase,
    private val changeMainCharacterUseCase: ChangeMainCharacterUseCase
) : BaseViewModel() {
    private val _isSendSuccess = MutableStateFlow<Boolean>(false)
    var isSendSuccess: StateFlow<Boolean> = _isSendSuccess

    private var _characterEncyclopediaList = MutableStateFlow<MutableList<CharacterDto>>(
        mutableListOf()
    )
    val characterEncyclopediaList: StateFlow<MutableList<CharacterDto>> = _characterEncyclopediaList

    fun getCharacterEncyclopediaList(){
        getApiResult(
            block = {
                getCharacterEncyclopediaListUseCase.invoke()
            },
            success = {
                Log.d(TAG, "getCharacterEncyclopediaList: get ${it.size}")
                it.forEach {
                    Log.d(TAG, "getCharacterEncyclopediaList: ?? ${it.characterName} ${it.isMain}")
                }
                _characterEncyclopediaList.emit(it)
            }
        )
    }

    fun changeMainCharacter(characterId: Int){
        getApiResult(
            block = {
                changeMainCharacterUseCase.invoke(
                    ChangeMainCharacterRequestDto(characterId)
                )
            },
            success = {
                getCharacterEncyclopediaList()
                _isSendSuccess.value = true
            }
        )
    }

    fun resetIsSendSuccess() {
        _isSendSuccess.value = false
    }

}