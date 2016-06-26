package utils

import java.sql.ResultSet

/**
  * @author jorge
  * @since 26/06/16
  */
trait StreamExtensions {

  def resultSetStream(resultSet: ResultSet): Stream[ResultSet] = {
    new Iterator[ResultSet] {
      def hasNext = resultSet.next()

      def next() = resultSet
    }.toStream
  }
}
