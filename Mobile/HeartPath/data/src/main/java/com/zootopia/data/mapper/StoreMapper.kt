package com.zootopia.data.mapper

import com.zootopia.data.model.store.request.BuyStoreCharacterRequest
import com.zootopia.data.model.store.request.BuyStoreLetterPaperRequest
import com.zootopia.data.model.store.request.ChangeMainCharacterRequest
import com.zootopia.data.model.store.response.CharacterEncyclopediaListResponse
import com.zootopia.data.model.store.response.StoreCharacterListResponse
import com.zootopia.data.model.store.response.StoreItemLetterPaperListResponse
import com.zootopia.domain.model.store.BuyStoreCharacterRequestDto
import com.zootopia.domain.model.store.BuyStoreLetterPaperRequestDto
import com.zootopia.domain.model.store.ChangeMainCharacterRequestDto
import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.domain.model.store.StoreCharacterDto
import com.zootopia.domain.model.store.StoreItemLetterPaperDto

fun CharacterEncyclopediaListResponse.toDomain(): MutableList<CharacterDto>{
    return data
}

fun StoreCharacterListResponse.toDomain(): MutableList<StoreCharacterDto>{
    return data
}

fun StoreItemLetterPaperListResponse.toDomain(): MutableList<StoreItemLetterPaperDto>{
    return data
}

fun BuyStoreCharacterRequestDto.toData(): BuyStoreCharacterRequest{
    return BuyStoreCharacterRequest(
        characterId = characterId
    )
}

fun BuyStoreLetterPaperRequestDto.toData(): BuyStoreLetterPaperRequest{
    return BuyStoreLetterPaperRequest(
        letterpaperId = letterpaperId
    )
}

fun ChangeMainCharacterRequestDto.toData(): ChangeMainCharacterRequest{
    return ChangeMainCharacterRequest(
        characterId = characterId
    )
}