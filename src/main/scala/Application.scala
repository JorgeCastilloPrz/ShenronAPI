import controller.CharacterController
import main.scala.cake.MemoryCharacterRepositoryComponent
import play.api.mvc.Action

/**
  * @author jorge
  * @since 19/06/16
  */
class Application extends CharacterController with MemoryCharacterRepositoryComponent {

  def index = Action {
    Ok("It works!")
  }
}
