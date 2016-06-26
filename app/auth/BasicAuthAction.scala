package auth

import play.api.mvc.{Action, ActionBuilder, Request, Result}

import scala.concurrent.Future

/**
  * @author jorge
  * @since 26/06/16
  */
object BasicAuthAction extends ActionBuilder[Request] {
  def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    block(request)
  }
  override def composeAction[A](action: Action[A]) = new BasicAuth(action)
}
