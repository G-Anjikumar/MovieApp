package com.llyod.remote.data.model

data class Person(
    val _links: Links,
    val birthday: String,
    val country: Country,
    val deathday: Any,
    val gender: String,
    val id: Int,
    val image: ImageX,
    val name: String,
    val updated: Int,
    val url: String
)