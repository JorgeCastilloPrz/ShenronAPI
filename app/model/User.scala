package model

/**
  * @author jorge
  * @since 26/06/16
  */
case class User(id: Long = User.NoId, username: String, password: String)

object User {
  val NoId = -1;
}
