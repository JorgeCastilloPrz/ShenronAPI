package model

/**
  * @author jorge
  * @since 19/06/16
  */
case class Character(id: Long = Character.NoId, name: String, description: String, photoUrl: String)

object Character {
  val NoId = -1;
}
