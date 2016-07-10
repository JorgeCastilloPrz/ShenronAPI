package main.scala.cake

import model.Character
import repository.CharacterRepository

/**
  * component to inject CharacterRepository.
  *
  * @author jorge
  * @since 19/06/16
  */
trait CharacterRepositoryComponent {

  val characterRepo: CharacterRepository
}
