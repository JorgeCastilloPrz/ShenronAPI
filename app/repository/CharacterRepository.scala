package repository

import model.Character

/**
  * @author jorge
  * @since 10/07/16
  */
trait CharacterRepository {

  def create(character: Character): Character

  def update(character: Character): Character

  def findAll(): List[Character]

  def find(id: Long): Option[Character]

  def find(name: String): Option[Character]

  def delete(id: Long): Boolean
}
