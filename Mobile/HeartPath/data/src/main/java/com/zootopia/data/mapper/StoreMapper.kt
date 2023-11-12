package com.zootopia.data.mapper

import com.zootopia.data.model.store.response.CharacterEncyclopediaListResponse
import com.zootopia.data.model.store.response.StoreCharacterListResponse
import com.zootopia.data.model.store.response.StoreItemLetterPaperListResponse
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