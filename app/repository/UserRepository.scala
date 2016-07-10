package repository

import model.User

/**
  * @author jorge
  * @since 10/07/16
  */
trait UserRepository {

  def find(username: String, password: String): Option[User]

  def create(user: User): User
}
