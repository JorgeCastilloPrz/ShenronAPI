package cake

import play.api.mvc.{Headers, RequestHeader}

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
