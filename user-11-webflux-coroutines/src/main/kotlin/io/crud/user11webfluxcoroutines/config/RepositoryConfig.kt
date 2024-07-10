package io.crud.user11webfluxcoroutines.config

import io.crud.user11webfluxcoroutines.repository.UserRepository
import io.crud.user11webfluxcoroutines.repository.UserRepositoryCoroutineImpl
import io.crud.user11webfluxcoroutines.repository.UserRepositoryJPAReactive
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

enum class RepositoryType {
    COROUTINE,
    JPA_REACTIVE
}

@Configuration
class RepositoryConfig(
    @Value("\${repository.type}")
    val repositoryType: RepositoryType,
    val userRepositoryCoroutine: UserRepositoryCoroutineImpl,
    val userRepositoryJPAReactive: UserRepositoryJPAReactive
) {

    @Bean
    fun userRepository(): UserRepository {
        return when(repositoryType) {
            RepositoryType.COROUTINE -> userRepositoryCoroutine
            RepositoryType.JPA_REACTIVE -> userRepositoryJPAReactive
        }
    }

}