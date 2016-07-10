package repository.database

import java.sql.{Connection, DriverManager}

import main.scala.cake.CharacterRepositoryComponent
import model.Character
import play.api.Play
import play.api.Play.current
import repository.CharacterRepository
import utils.StreamExtensions

/**
  * Implementation for the CharacterRepository component which provides a persistence character
  * repo.
  *
  * @author jorge
  * @since 19/06/16
  */
trait DBCharacterRepositoryComponent extends CharacterRepositoryComponent
  with StreamExtensions with CharacterQueryProvider with CharacterMapper {

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
      connection.createStatement.executeUpdate(createCharacterQuery(character))
      connection.close()
      find(character.id).get
    }

    override def update(character: Character): Character = {
      val connection = connectToDatabase
      connection.createStatement.executeUpdate(updateCharacterQuery(character))
      connection.close()
      find(character.id).getOrElse(find(character.name).get)
    }

    override def delete(id: Long): Boolean = {
      val connection = connectToDatabase
      val affectedRows = connection.createStatement.executeUpdate(deleteCharacterQuery(id))
      connection.close()
      affectedRows > 0
    }

    override def findAll(): List[Character] = {
      val connection = connectToDatabase
      val resultSet = connection.createStatement.executeQuery(findAllQuery())

      if (!resultSet.isBeforeFirst) {
        connection.close()
        List()
      } else {
        val characters = resultSetStream(resultSet).map(result => mapCharacterFromResult(result)).toList
        connection.close()
        characters
      }
    }

    override def find(id: Long): Option[Character] = {
      val connection = connectToDatabase
      val resultSet = connection.createStatement.executeQuery(findByIdQuery(id))

      if (!resultSet.isBeforeFirst) {
        connection.close()
        None
      } else {
        resultSet.next()
        val character = mapCharacterFromResult(resultSet)
        connection.close()
        Some(character)
      }
    }

    override def find(name: String): Option[Character] = {
      val connection = connectToDatabase
      val resultSet = connection.createStatement.executeQuery(findByNameQuery(name))

      if (!resultSet.isBeforeFirst) {
        connection.close()
        None
      } else {
        resultSet.next()
        val character = mapCharacterFromResult(resultSet)
        connection.close()
        Some(character)
      }
    }
  }

}
