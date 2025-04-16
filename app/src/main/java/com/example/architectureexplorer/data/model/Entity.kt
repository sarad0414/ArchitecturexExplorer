// data/model/Entity.kt
package com.example.architectureexplorer.data.model

import java.io.Serializable

// Represents a single architectural entity
data class Entity(
    val name: String,
    val architect: String,
    val location: String,
    val yearCompleted: Int,
    val style: String,
    val height: Int,
    val description: String
) : Serializable // Allows safe passing between activities

// Login API response
data class LoginResponse(
    val keypass: String
)

// Dashboard API response
data class DashboardResponse(
    val entities: List<Entity>,
    val entityTotal: Int
)
