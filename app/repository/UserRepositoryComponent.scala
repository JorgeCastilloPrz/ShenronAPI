package repository

import model.User

/**
  * @author jorge
  * @since 26/06/16
  */
trait UserRepositoryComponent {

  val userRepo: UserRepository

}
