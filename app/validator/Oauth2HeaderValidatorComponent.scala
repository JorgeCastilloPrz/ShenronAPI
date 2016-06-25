package validator

import play.api.mvc.Headers

/**
  * @author jorge
  * @since 25/06/16
  */
trait Oauth2HeaderValidatorComponent extends HeaderValidatorComponent {

  override val headerValidator = new Oauth2HeaderValidator

  class Oauth2HeaderValidator extends HeaderValidator {

    override def validate(headers: Headers): Boolean = true
  }

}
