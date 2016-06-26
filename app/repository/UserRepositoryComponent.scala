package repository

import model.User

/**
  * @author jorge
  * @since 26/06/16
  */
trait UserRepositoryComponent {

  val userRepo: UserRepository

  trait UserRepository {

    def find(username: String, password: String): Option[User]

    def create(user: User): User
  }

}
