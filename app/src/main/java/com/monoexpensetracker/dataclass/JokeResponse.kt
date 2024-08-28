package com.monoexpensetracker.dataclass

import com.google.gson.annotations.SerializedName

data class JokeResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("setup") val setup: String,
    @SerializedName("punchline") val punchline: String,
)
{
    val joke: String
        get() = "$setup \nBecause : $punchline"
}