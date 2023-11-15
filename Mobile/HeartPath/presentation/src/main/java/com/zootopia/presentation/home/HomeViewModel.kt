package com.zootopia.presentation.home

import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.domain.usecase.store.GetMainCharacterUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMainCharacterUseCase: GetMainCharacterUseCase
): BaseViewModel(){
    private var _mainCharacter = MutableStateFlow<CharacterDto>(
        CharacterDto(0, "", 0, "", "", false, false)
    )
    val mainCharacter : StateFlow<CharacterDto> = _mainCharacter

    fun getMainCharacter(){
        getApiResult(
            block = {
                getMainCharacterUseCase.invoke()
            },
            success = {
                _mainCharacter.emit(it)
            }
        )
    }
}
