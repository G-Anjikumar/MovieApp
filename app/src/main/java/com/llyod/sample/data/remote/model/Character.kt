package com.llyod.sample.data.remote.model

data class Character(
    val _links: LinksX,
    val id: Int,
    val image: ImageX,
    val name: String,
    val url: String
)