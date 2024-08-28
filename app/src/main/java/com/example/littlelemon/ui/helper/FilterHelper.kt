package com.example.littlelemon.ui.helper

import com.example.littlelemon.data.local.Menu

object FilterHelper {

    fun doSearch(menu: List<Menu>, query: String, selectedCategory: String): List<Menu> {
        var filteredMenu = filterByCategory(menu, selectedCategory)
        filteredMenu = filterByTitle(filteredMenu, query)
        return filteredMenu
    }

    private fun filterByTitle(menu: List<Menu>, query: String): List<Menu> {
        return if (query.isBlank()) {
            menu
        } else {
            menu.filter { it.title.contains(other = query, ignoreCase = true) }
        }
    }

    private fun filterByCategory(menu: List<Menu>, selectedCategory: String): List<Menu> {
        return if (selectedCategory.isBlank()) {
            menu
        } else {
            menu.filter { it.category == selectedCategory }
        }
    }

}