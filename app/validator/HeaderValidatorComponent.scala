package validator

import play.api.mvc.Headers

/**
  * component to inject HeaderValidator.
  *
  * @author jorge
  * @since 19/06/16
  */
trait HeaderValidatorComponent {

  val headerValidator: HeaderValidator

  trait HeaderValidator {

    def validate(headers: Headers): Boolean
  }

}
