package io.crud.user21mvc.service

import io.crud.user21mvc.entity.User
import io.crud.user21mvc.exception.NotFoundException
import io.crud.user21mvc.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserService(
    val userRepository: UserRepository
) {

    fun findById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { NotFoundException("User with id $id not found") }
    }

    fun findAll() = userRepository.findAll()

    fun deleteById(id: Long) {
        val user = findById(id)
        return userRepository.deleteById(user.id!!)
    }

    fun deleteAll() = userRepository.deleteAll()

    fun save(user: User) = userRepository.save(user)

}