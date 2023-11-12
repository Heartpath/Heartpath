package com.zootopia.data.mapper

import com.zootopia.data.model.store.CharacterEncyclopediaListResponse
import com.zootopia.domain.model.store.CharacterDto

fun CharacterEncyclopediaListResponse.toDomain(): MutableList<CharacterDto>{
    return data
}