package com.example.littlelemon.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuItemResponse(
    @SerialName("image")
    val image: String = "",
    @SerialName("price")
    val price: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("title")
    val title: String = "",
    @SerialName("category")
    val category: String = ""
)