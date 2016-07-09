package auth

import play.api.mvc.{Action, Request, Result}
import repository.database.DBUserRepositoryComponent

import scala.concurrent.Future

/**
  * @author jorge
  * @since 8/07/16
  */
trait DBBasicAuthActionComponent extends BasicAuthActionComponent {

  override val basicAuth = new DBBasicAuthAction

  class DBBasicAuthAction extends BasicAuthAction {
    override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
      block(request)
    }

    override def composeAction[A](action: Action[A]) =
      new BasicAuth(action) with DBUserRepositoryComponent
  }

}
