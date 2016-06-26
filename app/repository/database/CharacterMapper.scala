package repository.database

import java.sql.ResultSet

import model.Character

/**
  * @author jorge
  * @since 26/06/16
  */
trait CharacterMapper {
  
  def mapCharacterFromResult(resultSet: ResultSet): Character = {
    new Character(
      resultSet.getLong("id"),
      resultSet.getString("name"),
      resultSet.getString("description"),
      resultSet.getString("photourl"))
  }
}
