package repository.memory

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

import model.User
import repository.{UserRepository, UserRepositoryComponent}

/**
  * Implementation for the UserRepositoryComponent component which provides a memory user
  * repository. Using a static shared instance to avoid reinstantiating repo for each BasicAuth
  * action executed.
  *
  * @author jorge
  * @since 19/06/16
  */
trait MemoryUserRepositoryComponent extends UserRepositoryComponent {

  override val userRepo = MemoryUserRepositoryComponent.userRepo
}

object MemoryUserRepositoryComponent extends UserRepositoryComponent {

  val userRepo = new MemoryUserRepository

  class MemoryUserRepository extends UserRepository {

    val users = new ConcurrentHashMap[Long, User]
    val idSequence = new AtomicLong

    override def create(user: User): User = {
      val newId = idSequence.incrementAndGet()
      val createdUser = user.copy(id = newId)
      users.put(newId, createdUser)
    }

    override def find(username: String, password: String): Option[User] = {
      import scala.collection.JavaConversions._
      users.values().find(user => user.username == username && user.password == password)
    }
  }

}