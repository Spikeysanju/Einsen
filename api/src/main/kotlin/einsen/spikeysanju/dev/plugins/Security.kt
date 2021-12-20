package einsen.spikeysanju.dev.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.auth.principal

fun Application.configureSecurity() {
    authentication {
        jwt {
            val jwtAudience = environment.config.property("jwt.audience").getString()
            realm = environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256("secret"))
                    .withAudience(jwtAudience)
                    .withIssuer(environment.config.property("jwt.domain").getString())
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
}

val JWTPrincipal.userId: String?
    get() = getClaim("userId", String::class)

val ApplicationCall.userId: String
    get() = principal<JWTPrincipal>()?.userId.toString()
