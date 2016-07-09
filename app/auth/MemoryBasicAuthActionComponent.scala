package auth

import play.api.mvc.{ActionBuilder, Request}

/**
  * @author jorge
  * @since 8/07/16
  */
trait MemoryBasicAuthActionComponent extends BasicAuthActionComponent {

  val basicAuth = new MemoryAuthAction

  class MemoryAuthAction {

  }
}
