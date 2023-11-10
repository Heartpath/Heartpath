package com.zootopia.data.model.map.response.navermap

import com.google.gson.annotations.SerializedName

data class TrafastResponse(
    @SerializedName("summary")
    val summary: SummaryResponse,
    @SerializedName("path")
    val path: List<List<Double>>,
    @SerializedName("section")
    val section: List<SectionResponse>,
    @SerializedName("guide")
    val guide: List<GuideResponse>
)
