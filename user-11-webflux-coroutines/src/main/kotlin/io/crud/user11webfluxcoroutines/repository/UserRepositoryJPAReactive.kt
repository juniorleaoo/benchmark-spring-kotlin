package io.crud.user11webfluxcoroutines.repository

import io.crud.user11webfluxcoroutines.entity.User
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.stereotype.Repository
import reactor.core.scheduler.Scheduler
import java.util.Optional

@Repository
class UserRepositoryJPAReactive(
    val userRepository: UserRepositoryJPA,
    val scheduler: Scheduler
) : UserRepository {

    override suspend fun findById(id: Long): User? {
        return coroutineScope { userRepository.findById(id).orElse(null) }
    }

    override suspend fun findAll(): Flow<User> {
        return coroutineScope { userRepository.findAll().asFlow() }
    }

    override suspend fun deleteById(id: Long) {
        coroutineScope { userRepository.deleteById(id) }
    }

    override suspend fun deleteAll() {
        coroutineScope { userRepository.deleteAll() }
    }

    override suspend fun save(user: User): User {
        return coroutineScope { userRepository.save(user) }
    }


}