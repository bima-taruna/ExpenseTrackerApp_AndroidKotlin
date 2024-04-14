package com.bima.expensetrackerapp.data.repositoryImpl

import com.bima.expensetrackerapp.data.remote.UserProfileDto
import com.bima.expensetrackerapp.domain.repository.UserProfileRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : UserProfileRepository {
    override suspend fun getProfile(id: String?): UserProfileDto {
        return postgrest["user_profiles"].select {
            if (id != null) {
                filter {
                    eq("id", id)
                }
            }
        }.decodeSingle()
    }
}