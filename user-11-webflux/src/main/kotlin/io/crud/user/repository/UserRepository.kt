package io.crud.user.repository

import io.crud.user.entity.User
import reactor.core.publisher.Mono
import java.util.Optional

interface UserRepository {
    fun findById(id: Long): Mono<Optional<User>>
    fun findAll(): Mono<List<User>>
    fun deleteById(id: Long): Mono<Unit>
    fun deleteAll(): Mono<Unit>
    fun save(user: User): Mono<User>
}