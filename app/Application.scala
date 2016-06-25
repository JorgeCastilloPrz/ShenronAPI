import controllers.character.CharacterController
import play.api.mvc.Action
import repository.DBCharacterRepositoryComponent
import validator.Oauth2HeaderValidatorComponent

/**
  * @author jorge
  * @since 19/06/16
  */
object Application extends CharacterController
  with DBCharacterRepositoryComponent with Oauth2HeaderValidatorComponent {

  def index = Action {
    Ok("ShenronAPI is running!")
  }
}
