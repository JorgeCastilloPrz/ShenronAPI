package repository

import java.sql.{Connection, DriverManager}

import main.scala.cake.CharacterRepositoryComponent
import model.Character
import play.api.Play

/**
  * Implementation for the CharacterRepository component which provides a memory character
  * repository.
  *
  * @author jorge
  * @since 19/06/16
  */
trait DBCharacterRepositoryComponent extends CharacterRepositoryComponent {

  override val characterRepo = new DBCharacterRepository

  class DBCharacterRepository extends CharacterRepository {

    val driver = Play.configuration.getString("db.default.driver")
    val url = Play.configuration.getString("db.default.url")
    val user = Play.configuration.getString("db.default.user")
    val password = Play.configuration.getString("db.default.password")

    def connectToDatabase: Connection = {
      Class.forName(driver.get)
      DriverManager.getConnection(url.get, user.get, password.get)
    }

    override def create(character: Character): Character = {
      val connection = connectToDatabase
      connection.createStatement.executeQuery(characterQuery(character))
      connection.close()
    }

    override def update(character: Character): Character = {
      characters.put(character.id, character)
    }

    override def delete(id: Long) {
      characters.remove(id)
    }

    override def findAll(): List[Character] = {
      characters.values().toList
    }

    override def find(id: Long): Option[Character] = {
      Option(characters.get(id))
    }

    override def find(name: String): Option[Character] = {
      characters.values().find(_.name == name)
    }

    private def characterQuery(character: Character): String = {
      "INSERT INTO characters () VALUES "
    }
  }

}
