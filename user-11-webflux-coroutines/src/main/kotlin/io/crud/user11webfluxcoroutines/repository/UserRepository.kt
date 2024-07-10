package io.crud.user11webfluxcoroutines.repository

import io.crud.user11webfluxcoroutines.entity.User
import kotlinx.coroutines.flow.Flow
import java.util.Optional

interface UserRepository {

    suspend fun findById(id: Long): User?
    suspend fun findAll(): Flow<User>
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun save(user: User): User

}