package repository.database

import java.sql.{Connection, DriverManager}

import model.User
import play.api.Play
import play.api.Play.current
import repository.UserRepositoryComponent

/**
  * Implementation for the CharacterRepository component which provides a persistence character
  * repo.
  *
  * @author jorge
  * @since 19/06/16
  */
trait DBUserRepositoryComponent extends UserRepositoryComponent
  with UserQueryProvider with UserMapper {

  override val userRepo = new DBUserRepository

  class DBUserRepository extends UserRepository {

    val driver = Play.configuration.getString("db.default.driver")
    val url = Play.configuration.getString("db.default.url")
    val user = Play.configuration.getString("db.default.user")
    val password = Play.configuration.getString("db.default.password")

    def connectToDatabase: Connection = {
      Class.forName(driver.get)
      DriverManager.getConnection(url.get, user.get, password.get)
    }

    override def find(username: String, password: String): Option[User] = {
      val connection = connectToDatabase
      val resultSet = connection.createStatement.executeQuery(findUserQuery(username, password))

      if (!resultSet.isBeforeFirst) {
        connection.close()
        None
      } else {
        resultSet.next()
        val user = mapUserFromResult(resultSet)
        connection.close()
        Some(user)
      }
    }

    override def create(user: User): User = {
      val connection = connectToDatabase
      connection.createStatement.executeUpdate(createUserQuery(user))
      connection.close()
      find(user.username, user.password).get
    }
  }

}
