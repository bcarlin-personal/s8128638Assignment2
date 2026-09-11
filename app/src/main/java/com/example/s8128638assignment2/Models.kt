package com.example.s8128638assignment2

import java.io.Serializable

data class AuthRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val keypass: String
)

data class Entity(
    val property1: String? = null,
    val property2: String? = null,
    val description: String? = null
) : Serializable

data class DashboardResponse(
    val entities: List<Entity>,
    val entityTotal: Int
)