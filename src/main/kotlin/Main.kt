import java.sql.Connection
import java.sql.DriverManager

class DatabaseHandler(private val url: String, private val user: String, private val password: String) {

    fun connect(): Connection {
        return DriverManager.getConnection(url, user, password)
    }

    fun createTable() {
        val sql = """
            CREATE TABLE IF NOT EXISTS TEST_TABLE (
                id SERIAL PRIMARY KEY,
                data VARCHAR(255)
            )
        """

        connect().use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(sql)
            }
        }
    }

    fun insertData(data: String) {
        val sql = "INSERT INTO TEST_TABLE(data) VALUES (?)"

        connect().use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, data)
                preparedStatement.executeUpdate()
            }
        }
    }
}

fun main() {
    val url = "jdbc:postgresql://localhost:5432/TEST_DB"
    val user = "test_user"
    val password = "test_password"

    val dbHandler = DatabaseHandler(url, user, password)

    dbHandler.createTable()

    val data = "some useful test data"

    dbHandler.insertData(data)
}
