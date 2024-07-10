package io.crud.user21webflux.repository

import io.crud.user21webflux.entity.User
import reactor.core.publisher.Mono
import java.util.Optional

interface UserRepository {
    fun findById(id: Long): Mono<Optional<User>>
    fun findAll(): Mono<Iterable<User>>
    fun deleteById(id: Long): Mono<Unit>
    fun deleteAll(): Mono<Unit>
    fun save(user: User): Mono<User>
}