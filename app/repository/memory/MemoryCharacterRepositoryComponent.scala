package repository.memory

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

import main.scala.cake.CharacterRepositoryComponent
import model.Character
import repository.CharacterRepository

/**
  * Implementation for the CharacterRepository component which provides a memory character
  * repository. Using a static shared instance to avoid reinstantiating repo for each BasicAuth
  * action executed.
  *
  * @author jorge
  * @since 19/06/16
  */
trait MemoryCharacterRepositoryComponent extends CharacterRepositoryComponent {

  override val characterRepo = MemoryCharacterRepositoryComponent.characterRepo
}

object MemoryCharacterRepositoryComponent extends CharacterRepositoryComponent {

  val characterRepo = new MemoryCharacterRepository

  class MemoryCharacterRepository extends CharacterRepository {

    val characters = new ConcurrentHashMap[Long, Character]
    val idSequence = new AtomicLong

    override def create(character: Character): Character = {
      val newId = idSequence.incrementAndGet()
      val createdCharacter = character.copy(id = newId)
      characters.put(newId, createdCharacter)
    }

    override def update(character: Character): Character = {
      characters.put(character.id, character)
    }

    override def delete(id: Long): Boolean = {
      val removedCharacter = characters.remove(id)
      removedCharacter != null
    }

    override def findAll(): List[Character] = {
      import scala.collection.JavaConversions._
      characters.values().toList
    }

    override def find(id: Long): Option[Character] = {
      Option(characters.get(id))
    }

    override def find(name: String): Option[Character] = {
      import scala.collection.JavaConversions._
      characters.values().find(_.name == name)
    }
  }
}
