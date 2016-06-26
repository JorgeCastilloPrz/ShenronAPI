package controllers.user

import controllers.user.resource.UserResource
import model.User
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._
import repository.UserRepositoryComponent

/**
  * Controllers are used as query routers in Play framework. Static methods returning actions are
  * linked to url paths in order to resolve petitions.
  *
  * @author jorge
  * @since 19/06/16
  */
trait UserController extends Controller {
  self: UserRepositoryComponent =>

  implicit val userReads: Reads[UserResource] = (
    (__ \ "username").read[String] and
      (__ \ "password").read[String]
    ) (UserResource.apply _)

  def authenticate(username: String, password: String): Boolean = {
    userRepo.find(username, password).nonEmpty
  }

  def createUser = Action(BodyParsers.parse.json) {
    implicit request => {
      mapToUserResource(request) {
        resource: UserResource =>
          val user = User(username = resource.username,
            password = resource.password)

          if (userRepo.find(user.username, user.password).nonEmpty)
            Conflict("The given user is already registered.")
          else create(user)
      }
    }
  }

  def create(user: User) = {
    userRepo.create(user)
    Created
  }

  def mapToUserResource(request: Request[JsValue])
                       (action: UserResource => Result)
                       (implicit userReads: Reads[UserResource]): Result =
    request.body.validate[UserResource](userReads).fold(
      errors => BadRequest(Json.obj("errors" -> JsError.toJson(errors))),
      userResource => {
        action.apply(userResource)
      }
    )
}