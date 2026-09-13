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
    val species: String? = null,
    val scientificName: String? = null,
    val habitat: String? = null,
    val diet: String? = null,
    val conservationStatus: String? = null,
    val averageLifespan: Int? = null,
    val description: String? = null
) : Serializable

data class DashboardResponse(
    val entities: List<Entity>,
    val entityTotal: Int
)