package com.zootopia.presentation.readletter

import com.zootopia.domain.usecase.user.AddFriendUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadLetterViewModel @Inject constructor(
    private val addFriendUseCase: AddFriendUseCase,
): BaseViewModel(){

}