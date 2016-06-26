package controllers.character

import auth.BasicAuth
import main.scala.cake.CharacterRepositoryComponent
import main.scala.controller.request.resource.CharacterResource
import model.Character
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.mvc._
import validator.HeaderValidatorComponent

/**
  * Controllers are used as query routers in Play framework. Static methods returning actions are
  * linked to url paths in order to resolve petitions.
  *
  * Applied cake pattern to inject CharacterRepository and HeaderValidator
  *
  * @author jorge
  * @since 19/06/16
  */
@BasicAuth()
class CharacterController extends Controller {
  self: CharacterRepositoryComponent with HeaderValidatorComponent =>

  // Reads converters are used to convert from a JsValue to another type. You can combine and nest
  // Reads to create more complex ones. Here I am nesting reads to create a CharacterResource

  implicit val characterReads: Reads[CharacterResource] = (
    (__ \ "name").read[String] and
      (__ \ "description").read[String] and
      (__ \ "photoUrl").read[String]
    ) (CharacterResource.apply _)

  // Write converters are used to convert from any model type to JsValue

  implicit val characterWrites = new Writes[Character] {
    override def writes(character: Character): JsValue = {
      Json.obj(
        "id" -> character.id,
        "name" -> character.name,
        "description" -> character.description,
        "photoUrl" -> character.photoUrl
      )
    }
  }

  def createOrUpdateCharacter = Action(BodyParsers.parse.json) {
    implicit request => {
      if (!headerValidator.validate(request.headers)) BadRequest("Wrong Headers")
      else {
        mapToCharacterResource(request) {
          resource: CharacterResource =>
            val character = Character(name = resource.name,
              description = resource.description,
              photoUrl = resource.photoUrl)

            if (characterRepo.find(character.id).nonEmpty || characterRepo.find(character.name).nonEmpty)
              update(character)
            else create(character)
        }
      }
    }
  }

  def update(character: Character) = {
    characterRepo.update(character)
    Ok
  }

  def create(character: Character) = {
    characterRepo.create(character)
    Created
  }

  def deleteCharacter(id: Option[Long]) = Action {
    request => {
      if (!headerValidator.validate(request.headers)) BadRequest("Wrong Headers")
      else if (id.nonEmpty) {
        if (characterRepo.delete(id.get)) Ok else NotFound
      } else {
        BadRequest
      }
    }
  }

  def findCharacter(id: Option[Long], name: Option[String]) = Action {
    request => {
      if (!headerValidator.validate(request.headers)) BadRequest("Wrong Headers")
      else if (request.queryString.count(x => x._1 != "id" && x._1 != "name") > 0) BadRequest
      else if (id.isEmpty && name.isEmpty) BadRequest
      else if (id.isDefined && name.isDefined)
        findCharacterWithDoubleCriteria(id.get, name.get)
      else {
        val character = if (id.nonEmpty) characterRepo.find(id.get) else characterRepo.find(name.get)
        if (character.isDefined) {
          Ok(Json.toJson(character))
        } else {
          NotFound("The character you are looking for is not available in our datebase.")
        }
      }
    }
  }

  def findCharacterWithDoubleCriteria(id: Long, name: String) = {
    val character = characterRepo.find(id);
    if (character.nonEmpty && character.get.name == name)
      Ok(Json.toJson(character))
    else
      NotFound("There are no characters available matching given conditions.")
  }

  def findAllCharacters() = Action {
    request => {
      if (!headerValidator.validate(request.headers)) BadRequest("Wrong Headers")
      else {
        val characters = characterRepo.findAll()
        Ok(Json.toJson(characters))
      }
    }
  }

  def mapToCharacterResource(request: Request[JsValue])
                            (action: CharacterResource => Result)
                            (implicit characterReads: Reads[CharacterResource]): Result =
    request.body.validate[CharacterResource](characterReads).fold(
      errors => BadRequest(Json.obj("errors" -> JsError.toJson(errors))),
      characterResource => {
        action.apply(characterResource)
      }
    )
}
