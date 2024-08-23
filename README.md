user-11-mvc
- valid
- 
user-11-webflux
- using JPA
- valid

user-11-webflux-coroutines 
- using JPA
- valid

user-11-webflux-r2dbc
- using coroutines
REPOSITORY_TYPE => JPA_REACTIVE and COROUTINE


## First scenario

SPRING_DATASOURCE_MAX_POOL_SIZE=10

```kotlin
createAndGetUsers.injectOpen(
    constantUsersPerSec(2.0).during(Duration.ofSeconds(5)),
    rampUsersPerSec(6.0).to(200.0).during(Duration.ofMinutes(1)),
    constantUsersPerSec(300.0).during(Duration.ofMinutes(1))
),
listUsers.injectOpen(
    constantUsersPerSec(2.0).during(Duration.ofSeconds(5)),
    rampUsersPerSec(6.0).to(50.0).during(Duration.ofMinutes(3)),
    constantUsersPerSec(50.0).during(Duration.ofMinutes(3))
)
```

## Second scenario

SPRING_DATASOURCE_MAX_POOL_SIZE=20

```kotlin
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
```