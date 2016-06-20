package main.scala.cake

import model.Character

/**
  * Cake pattern component to inject CharacterRepository.
  *
  * @author jorge
  * @since 19/06/16
  */
trait CharacterRepositoryComponent {

  val characterRepo: CharacterRepository

  trait CharacterRepository {

    def create(character: Character): Character

    def update(character: Character): Character

    def findAll(): List[Character]

    def find(id: Long): Option[Character]

    def find(name: String): Option[Character]

    def delete(id: Long)
  }

}
