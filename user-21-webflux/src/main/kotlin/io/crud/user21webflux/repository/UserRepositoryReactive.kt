package io.crud.user21webflux.repository

import io.crud.user21webflux.entity.User
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import java.util.Optional

@Repository
class UserRepositoryReactive(
    val userRepository: UserRepositoryJPA,
    val scheduler: Scheduler
): UserRepository {

    override fun findById(id: Long): Mono<Optional<User>> {
        return async { userRepository.findById(id) }
    }

    override fun findAll(): Mono<Iterable<User>> {
        return async { userRepository.findAll() }
    }

    @Transactional
    override fun deleteById(id: Long): Mono<Unit> {
        return async { userRepository.deleteById(id) }
    }

    override fun deleteAll(): Mono<Unit> {
        return async { userRepository.deleteAll() }
    }

    override fun save(user: User): Mono<User> {
        return async { userRepository.save(user) }
    }

    private fun <T> async (block: () -> T): Mono<T> {
        return Mono.fromCallable(block).subscribeOn(scheduler)
    }

}