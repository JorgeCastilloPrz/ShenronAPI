package auth

import controllers.user.UserController
import play.libs.F.{Function0, Promise}
import play.mvc.Http.Context
import play.mvc.{Action, Result}
import repository.database.DBUserRepositoryComponent

/**
  * @author jorge
  * @since 26/06/16
  */
class BasicAuthAction extends Action[Result] with UserController with DBUserRepositoryComponent {

  override def call(ctx: Context): Promise[Result] = {
    val authHeader = ctx.request().getHeader(BasicAuthAction.AUTHORIZATION)
    if (authHeader == null) {
      ctx.response().setHeader(BasicAuthAction.WWW_AUTHENTICATE, BasicAuthAction.REALM)
      return unauthorized
    }

    val auth = authHeader.substring(6)
    val decodedAuth = new sun.misc.BASE64Decoder().decodeBuffer(auth)
    val credString = new String(decodedAuth, "UTF-8").split(":")
    if (credString == null || credString.length != 2) unauthorized

    val username = credString(0)
    val password = credString(1)

    if (authenticate(username, password)) {
      delegate.call(ctx)
    } else unauthorized
  }

  def unauthorized: Promise[Result] = {
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
  val REALM = "Basic realm=\"Your Realm Here\""
}
