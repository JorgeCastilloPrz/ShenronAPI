package controllers

import auth.DBBasicAuthActionComponent
import controllers.character.CharacterController
import controllers.user.UserController
import play.api.mvc.Action
import repository.database.{DBCharacterRepositoryComponent, DBUserRepositoryComponent}

/**
  * @author jorge
  * @since 19/06/16
  */
object Application extends CharacterController
  with DBCharacterRepositoryComponent with UserController
  with DBUserRepositoryComponent with DBBasicAuthActionComponent {

  def index = Action {
    Ok("ShenronAPI is running!")
  }
}
