package controllers.user

import play.api.mvc.Controller
import repository.UserRepositoryComponent

/**
  * Controllers are used as query routers in Play framework. Static methods returning actions are
  * linked to url paths in order to resolve petitions.
  *
  * @author jorge
  * @since 19/06/16
  */
trait UserController extends Controller {
  self: UserRepositoryComponent =>

  def authenticate(username: String, password: String): Boolean = {
    userRepo.find(username, password).nonEmpty
  }
}