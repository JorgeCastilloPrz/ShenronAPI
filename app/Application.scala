import controllers.character.CharacterController
import main.scala.cake.MemoryCharacterRepositoryComponent
import play.api.mvc.Action
import validator.Oauth2HeaderValidatorComponent

/**
  * @author jorge
  * @since 19/06/16
  */
object Application extends CharacterController
  with MemoryCharacterRepositoryComponent with Oauth2HeaderValidatorComponent {

  def index = Action {
    Ok("ShenronAPI is running!")
  }
}
