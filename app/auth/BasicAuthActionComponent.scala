package auth

import play.api.mvc.{ActionBuilder, Request}

/**
  * @author jorge
  * @since 8/07/16
  */
trait BasicAuthActionComponent {

  val basicAuth: BasicAuthAction

  trait BasicAuthAction extends ActionBuilder[Request]

}
