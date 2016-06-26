package repository.database

import model.Character

/**
  * @author jorge
  * @since 26/06/16
  */
trait CharacterQueryProvider {

  def createCharacterQuery(character: Character): String = {
    "INSERT INTO characters (name, description, photourl) VALUES (\"" +
      character.name + "\", \"" + character.description + "\", \"" + character.photoUrl + "\")"
  }

  def updateCharacterQuery(character: Character): String = {
    "UPDATE characters SET " +
      "name='" + character.name + "', " +
      "description='" + character.description + "', " +
      "photourl='" + character.photoUrl + "'" +
      " WHERE id=" + character.id + " OR name='" + character.name + "'"
  }

  def deleteCharacterQuery(id: Long): String = {
    "DELETE FROM characters WHERE id=" + id
  }

  def findAllQuery(): String = {
    "SELECT * FROM characters"
  }

  def findByIdQuery(id: Long) = {
    "SELECT * FROM characters WHERE id=" + id
  }

  def findByNameQuery(name: String) = {
    "SELECT * FROM characters WHERE name=\"" + name + "\""
  }
}
