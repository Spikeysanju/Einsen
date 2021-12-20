package einsen.spikeysanju.dev.database.config

/**
 * Postgres Configuration
 */
// TODO: Replace this values with your local server configuration details
class DatabaseConfig(
    val user: String = TODO("Enter the username which you created while installing Postgresql, it maybe 'postgres' by default."),
    val password: String = TODO("Enter the Password which you created while installing Postgresql."),
    host: String = "localhost",
    port: String = "8081",
    name: String = TODO("Create a new DB on your Postgresql and enter it's name here."),
) {
    val url = "jdbc:postgresql://$host:$port/$name"
}
