package io.crud.user21mvc.controller

import io.crud.user21mvc.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long): ResponseEntity<UserResponse> {
        val user = userService.findById(id)
        return ResponseEntity.ok(user.toResponse())
    }

    @GetMapping
    fun list(): ResponseEntity<List<UserResponse>> {
        val users = userService.findAll()
            .map { it.toResponse() }
        return ResponseEntity.ok(users)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Void> {
        userService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteAll(): ResponseEntity<Void> {
        userService.deleteAll()
        return ResponseEntity.noContent().build()
    }

    @PostMapping
    fun create(@Valid @RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val user = userService.save(userRequest.toUser())
        return ResponseEntity
            .created(URI.create("/users/${user.id}"))
            .body(user.toResponse())
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @Valid @RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val user = userService.findById(id)
        val updatedUser = userService.save(user.copy(
            name = userRequest.name,
            nick = userRequest.nick,
            birthDate = userRequest.birthDate,
            stack = userRequest.stack
        ))
        return ResponseEntity.ok(updatedUser.toResponse())
    }

}