package com.zootopia.data.mapper

import com.zootopia.data.model.store.response.CharacterEncyclopediaListResponse
import com.zootopia.data.model.store.response.StoreCharacterListResponse
import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.domain.model.store.StoreCharacterDto

fun CharacterEncyclopediaListResponse.toDomain(): MutableList<CharacterDto>{
    return data
}

fun StoreCharacterListResponse.toDomain(): MutableList<StoreCharacterDto>{
    return data
}