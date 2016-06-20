package controllers

import controllers.character.CharacterController
import main.scala.cake.MemoryCharacterRepositoryComponent
import play.api.mvc.Action

/**
  * @author jorge
  * @since 19/06/16
  */
object Application extends CharacterController with MemoryCharacterRepositoryComponent {

  def index = Action {
    Ok("Your new application is ready.")
  }
}
