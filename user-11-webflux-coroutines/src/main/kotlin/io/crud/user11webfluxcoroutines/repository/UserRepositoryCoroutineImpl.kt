package io.crud.user11webfluxcoroutines.repository

import io.crud.user11webfluxcoroutines.entity.User
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryCoroutineImpl(
    val userRepositoryCoroutine: UserRepositoryCoroutine
): UserRepository {

    override suspend fun findById(id: Long): User? {
        return userRepositoryCoroutine.findById(id)
    }

    override suspend fun findAll(): Flow<User> {
        return userRepositoryCoroutine.findAll()
    }

    override suspend fun deleteById(id: Long) {
        userRepositoryCoroutine.deleteById(id)
    }

    override suspend fun deleteAll() {
        userRepositoryCoroutine.deleteAll()
    }

    override suspend fun save(user: User): User {
        return userRepositoryCoroutine.save(user)
    }


}