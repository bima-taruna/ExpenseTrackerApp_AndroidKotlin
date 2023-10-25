package com.bima.expensetrackerapp.data.remote

import com.bima.expensetrackerapp.domain.model.UserProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
	@SerialName("name")
	val name: String? = null,
	@SerialName("created_at")
	val createdAt: String? = null,
	@SerialName("id")
	val id: String? = null,
	@SerialName("role")
	val role: String? = null,
	@SerialName("email")
	val email: String? = null
)

fun UserProfileDto.toUserProfile() : UserProfile {
	return UserProfile(
		id = id,
		name = name,
		email = email,
		role = role
	)
}

