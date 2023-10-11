package com.bima.expensetrackerapp.domain.repository

import com.bima.expensetrackerapp.data.remote.UserProfileDto

interface UserProfileRepository {
    suspend fun getProfile(id:String): UserProfileDto
}