// required for Gatling core structure DSL

// required for Gatling HTTP DSL

// can be omitted if you don't use jdbcFeeder

// used for specifying durations with a unit, eg Duration.ofMinutes(5)
import io.gatling.javaapi.core.CoreDsl.StringBody
import io.gatling.javaapi.core.CoreDsl.constantUsersPerSec
import io.gatling.javaapi.core.CoreDsl.exec
import io.gatling.javaapi.core.CoreDsl.rampUsersPerSec
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.header
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import java.time.Duration
import java.time.LocalDateTime
import kotlin.random.Random

class ApiSimulation : Simulation() {
    val httpProtocol = http
        .baseUrl("http://localhost:8080/v1")
        .acceptHeader("application/json")

    fun randomString(length: Int): String {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    val createAndGetUsers = scenario("Create and Get Users")
        .exec { s ->
            val birthDate = LocalDateTime.now()
            val nick = randomString(32)
            val name = randomString(255)
            val session = s.setAll(mapOf(
                "birthDate" to birthDate,
                "nick" to nick,
                "name" to name,
                "stack" to setOf("Java", "Kotlin", "Scala"),
            ))
            session
        }.exec(
            http("Create")
                .post("/users")
                .body(StringBody(
                    """
                    {
                        "birth_date": "#{birthDate}",
                        "nick": "#{nick}",
                        "name": "#{name}",
                        "stack": ["Java", "Kotlin", "Scala"]
                    }
                    """.trimIndent()
                ))
                .header("Content-Type", "application/json")
                .check(status().`in`(201))
                .check(status().saveAs("httpStatus"))
                .checkIf { session -> session.getString("httpStatus") == "201" }
                .then(header("Location").saveAs("location"))

        ).pause(Duration.ofMillis(1), Duration.ofMillis(30))
        .doIf { session -> session.getString("httpStatus") == "201" }.then(
            exec(
                http("Get")
                    .get("#{location}")
                    .header("Content-Type", "application/json")
                    .check(status().`in`(200))
            )
        )

    val listUsers = scenario("List Users")
        .exec(
            http("List")
                .get("/users")
                .header("Content-Type", "application/json")
                .check(status().`in`(200))
        )

    init {
        this.setUp(
            createAndGetUsers.injectOpen(
                constantUsersPerSec(2.0).during(Duration.ofSeconds(5)),
                rampUsersPerSec(6.0).to(100.0).during(Duration.ofMinutes(1)),
                constantUsersPerSec(100.0).during(Duration.ofMinutes(1))
            ),
            listUsers.injectOpen(
                constantUsersPerSec(2.0).during(Duration.ofSeconds(5)),
                rampUsersPerSec(6.0).to(50.0).during(Duration.ofMinutes(3)),
                constantUsersPerSec(50.0).during(Duration.ofMinutes(3))
            )
        ).protocols(httpProtocol)

    }
}