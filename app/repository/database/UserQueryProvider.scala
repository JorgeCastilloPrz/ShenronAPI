package repository.database

/**
  * @author jorge
  * @since 26/06/16
  */
trait UserQueryProvider {

  def findUserQuery(username: String, password: String) = {
    "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'"
  }
}
