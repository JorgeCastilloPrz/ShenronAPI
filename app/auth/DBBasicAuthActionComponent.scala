package auth

/**
  * @author jorge
  * @since 8/07/16
  */
trait DBBasicAuthActionComponent extends BasicAuthActionComponent {

  override val basicAuth = new MemoryBasicAuthAction

  class MemoryBasicAuthAction {

  }

}
