package auth

import controllers.user.UserController
import play.api.mvc.{Action, Request, Result}
import repository.UserRepositoryComponent

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * @author jorge
  * @since 26/06/16
  */
class BasicAuth[A](action: Action[A]) extends Action[A] with UserController {
  self: UserRepositoryComponent =>

  def apply(request: Request[A]): Future[Result] = {
    val authHeader = request.headers.get(BasicAuth.AUTHORIZATION)
    if (authHeader.isEmpty) {
      return unauthorized
    }

    val auth = authHeader.get.substring(6)
    val decodedAuth = new sun.misc.BASE64Decoder().decodeBuffer(auth)
    val credString = new String(decodedAuth, "UTF-8").split(":")
    if (credString == null || credString.length != 2) {
      return unauthorized
    }

    val username = credString(0)
    val password = credString(1)

    if (authenticate(username, password)) {
      action(request)
    } else {
      return unauthorized
    }
  }

  lazy val parser = action.parser

  def unauthorized: Future[Result] = {
    Future(play.api.mvc.Results.Unauthorized)
  }
}

object BasicAuth {
  val AUTHORIZATION = "Authorization"
  val WWW_AUTHENTICATE = "WWW-Authenticate"
  val REALM = "Basic realm=\"Shenron\""
}
