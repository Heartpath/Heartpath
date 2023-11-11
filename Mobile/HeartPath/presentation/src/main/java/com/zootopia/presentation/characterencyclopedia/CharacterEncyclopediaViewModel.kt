package com.zootopia.presentation.characterencyclopedia

import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.domain.usecase.store.GetCharacterEncyclopediaListUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterEncyclopediaViewModel @Inject constructor(
    private val getCharacterEncyclopediaListUseCase: GetCharacterEncyclopediaListUseCase
) : BaseViewModel() {
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
                _characterEncyclopediaList.emit(it)
            }
        )
    }

}