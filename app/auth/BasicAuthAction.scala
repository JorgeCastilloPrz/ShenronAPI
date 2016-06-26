package auth

import controllers.user.UserController
import play.libs.F.{Function0, Promise}
import play.mvc.Http.Context
import play.mvc.{Action, Result}
import repository.database.DBUserRepositoryComponent

/**
  * Custom Action to do basic authentication. Added to the CharacterController class through
  * BasicAuth annotation. By doing that, all the controller exposed action methods (API calls)
  * are authenticated.
  *
  * @author jorge
  * @since 26/06/16
  */
class BasicAuthAction extends Action[Result] with UserController with DBUserRepositoryComponent {

  override def call(ctx: Context): Promise[Result] = {
    val authHeader = ctx.request().getHeader(BasicAuthAction.AUTHORIZATION)
    if (authHeader == null) {
      return unauthorized(ctx)
    }

    val auth = authHeader.substring(6)
    val decodedAuth = new sun.misc.BASE64Decoder().decodeBuffer(auth)
    val credString = new String(decodedAuth, "UTF-8").split(":")
    if (credString == null || credString.length != 2) {
      return unauthorized(ctx)
    }

    val username = credString(0)
    val password = credString(1)

    if (authenticate(username, password)) {
      delegate.call(ctx)
    } else {
      return unauthorized(ctx)
    }
  }

  def unauthorized(ctx: Context): Promise[Result] = {
    ctx.response().setHeader(BasicAuthAction.WWW_AUTHENTICATE, BasicAuthAction.REALM)
    Promise.promise(new Function0[Result]() {
      override def apply(): Result = {
        play.mvc.Results.unauthorized()
      }
    })
  }
}

private object BasicAuthAction {
  val AUTHORIZATION = "authorization"
  val WWW_AUTHENTICATE = "WWW-Authenticate"
  val REALM = "Basic realm=\"Shenron\""
}
