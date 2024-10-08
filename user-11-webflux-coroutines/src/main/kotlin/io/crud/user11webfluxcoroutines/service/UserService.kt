package io.crud.user11webfluxcoroutines.service

import io.crud.user11webfluxcoroutines.entity.User
import io.crud.user11webfluxcoroutines.exception.NotFoundException
import io.crud.user11webfluxcoroutines.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserService(
    val userRepository: UserRepository
) {

    suspend fun findById(id: Long): User {
        return userRepository.findById(id) ?: throw NotFoundException("User with id $id not found")
    }

    suspend fun findAll() = userRepository.findAll()

    suspend fun deleteById(id: Long) {
        val user = findById(id)
        return userRepository.deleteById(user.id!!)
    }

    suspend fun deleteAll() = userRepository.deleteAll()

    suspend fun save(user: User) = userRepository.save(user)

}