package einsen.spikeysanju.dev.database.config

class DatabaseConfig(
    val user: String = "postgres",
    val password: String = "786110",
    host: String = "localhost",
    port: String = "8081",
    name: String = "admin",
) {
    val url = "jdbc:postgresql://$host:$port/$name"
}
