package io.crud.user21mvc.entity

import java.time.LocalDateTime
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Converter
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long?,

    @Column(name = "nick", length = 32)
    val nick: String?,

    @Column(name = "name", length = 255, unique = true, nullable = false)
    val name: String,

    @Column(name = "birth_date", nullable = false)
    val birthDate: LocalDateTime,

    @Convert(converter = StringListConverter::class)
    @Column(name = "stack", columnDefinition = "text")
    val stack: List<String>?,
) {
    constructor() : this(null, "", "", LocalDateTime.now(), null)
}

@Converter
class StringListConverter : AttributeConverter<List<String>, String> {

    override fun convertToDatabaseColumn(attribute: List<String>?): String {
        return attribute?.joinToString(";") ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): List<String>{
        return if (dbData?.isNotBlank() == true) dbData.split(";") else listOf()
    }
}