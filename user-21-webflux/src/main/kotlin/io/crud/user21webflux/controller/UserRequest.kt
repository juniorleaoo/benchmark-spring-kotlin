package io.crud.user21webflux.controller

import io.crud.user21webflux.entity.User
import java.time.LocalDateTime
import java.util.UUID
import jakarta.validation.constraints.Size

data class UserRequest(
    val birthDate: LocalDateTime,
    @field:Size(min = 1, max = 32, message = "O campo apelido deve estar entre 1 e 32")
    val nick: String?,
    @field:Size(min = 1, max = 255, message = "O campo nome é obrigatório e deve estar entre 1 e 255")
    val name: String,
    @field:SizeElementsOfList
    val stack: List<String>? = null,
)

fun UserRequest.toUser(): User {
    return User(
        id = null,
        nick = nick,
        name = name,
        birthDate = birthDate,
        stack = stack
    )
}