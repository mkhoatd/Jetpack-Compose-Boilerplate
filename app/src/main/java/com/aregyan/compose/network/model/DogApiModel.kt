package com.aregyan.compose.network.model

import com.aregyan.compose.domain.Dog
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DogApiModel(
    @SerialName("bred_for") val bredFor: String?,
    @SerialName("breed_group") val breedGroup: String?,
    val description: String?,
    @SerialName("country_code") val countryCode: String?,
    @SerialName("height") val height: Height?,
    val history: String?,
    @SerialName("id") val id: Int?,
    @SerialName("life_span") val lifeSpan: String?,
    @SerialName("name") val name: String?,
    @SerialName("origin") val origin: String?,
    @SerialName("temperament") val temperament: String?,
    @SerialName("weight") val weight: Weight?,
    @SerialName("url") val url: String?
)

@Serializable
data class Height(
    val imperial: String?,
    val metric: String?
)

@Serializable
data class Weight(
    val imperial: String?,
    val metric: String?
)

fun List<DogApiModel>.asDomainModel(): List<Dog> {
    return map {
        Dog(
            name = it.name ?: "",
            origin = it.origin ?: "",
            url = it.url ?: "",
            lifeSpan = it.lifeSpan ?: "",
            bredFor = it.bredFor ?: "",
            breedGroup = it.breedGroup ?: "",
            temperament = it.temperament ?: "",
            id = it.id ?: 0,
            height = it.height?.metric ?: "",
            weight = it.weight?.metric ?: ""
        )
    }
}