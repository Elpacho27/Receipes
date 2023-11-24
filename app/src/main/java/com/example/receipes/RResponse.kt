package com.example.receipes

import java.io.Serializable


data class Recipe(
    val recipeId: String,
    val title: String,
    val ingredients: Map<String, String>,
    val instructions: String,
    val image: String
)

data class ApiResponse(
    val s: Int,
    val d: List<Recipe>,
    val t: Int,
    val p: Any,
    val limitstart: Int,
    val limit: Int,
    val total: Int,
    val pagesStart: Int,
    val pagesStop: Int,
    val pagesCurrent: Int,
    val pagesTotal: Int
):Serializable

