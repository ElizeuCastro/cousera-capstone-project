package com.example.littlelemon.data.remote

import com.example.littlelemon.data.local.Menu
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuResponse(
    @SerialName("menu")
    val menu: List<MenuItemResponse> = emptyList()
)

fun MenuResponse.toMenu(): List<Menu> = menu
    .map { remote ->
        Menu(
            image = remote.image,
            price = remote.price,
            description = remote.description,
            id = remote.id,
            title = remote.title,
            category = remote.category
        )
    }