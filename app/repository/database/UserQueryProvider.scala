package repository.database

import model.User

/**
  * @author jorge
  * @since 26/06/16
  */
trait UserQueryProvider {

  def findUserQuery(username: String, password: String) = {
    "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'"
  }

  def createUserQuery(user: User): String = {
    "INSERT INTO users (username, password) VALUES (\"" +
      user.username + "\", \"" + user.password + "\")"
  }
}
