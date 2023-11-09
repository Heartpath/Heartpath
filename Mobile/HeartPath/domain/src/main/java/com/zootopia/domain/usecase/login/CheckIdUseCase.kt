package com.zootopia.domain.usecase.login

import com.zootopia.domain.repository.login.LoginRepository
import javax.inject.Inject

class CheckIdUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(newId: String): Boolean {
        return loginRepository.checkId(newId =  newId)
    }
}