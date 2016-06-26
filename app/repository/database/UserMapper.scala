package repository.database

import java.sql.ResultSet

import model.User

/**
  * @author jorge
  * @since 26/06/16
  */
trait UserMapper {

  def mapUserFromResult(resultSet: ResultSet): User = {
    new User(
      resultSet.getString("username"),
      resultSet.getString("password"))
  }
}
