package controllers.character

import main.scala.cake.CharacterRepositoryComponent
import main.scala.controller.request.resource.CharacterResource
import model.Character
import play.api.Logger
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.mvc._

/**
  * @author jorge
  * @since 19/06/16
  */
class CharacterController extends Controller {
  self: CharacterRepositoryComponent =>

  // Reads converters are used to convert from a JsValue to another type. You can combine and nest
  // Reads to create more complex ones. Here I am nesting reads to creating a CharacterResource

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

  def createOrUpdateCharacter = Action(parse.json) { request =>
    mapToCharacterResource(request) { resource: CharacterResource =>
      val character = Character(name = resource.name,
        description = resource.description,
        photoUrl = resource.photoUrl)

      if (alreadyExists(character)) update(character) else create(character)
    }
  }

  private def alreadyExists(character: Character): Boolean = {
    characterRepo.find(character.id).nonEmpty
  }

  def update(character: Character) = {
    characterRepo.update(character)
    Ok
  }

  def create(character: Character) = {
    characterRepo.create(character)
    Created
  }

  def deleteCharacter(id: Long) = Action {
    characterRepo.delete(id)
    Ok
  }

  def findCharacterById(id: Long) = Action {
    val character = characterRepo.find(id)
    if (character.isDefined) {
      Ok(Json.toJson(character))
    } else {
      NotFound("The character you are looking for is not available in our datebase.")
    }
  }

  def findCharacterByName(name: String) = Action {
    val character = characterRepo.find(name)
    if (character.isDefined) {
      Ok(Json.toJson(character))
    } else {
      NotFound("The character you are looking for is not available in our datebase.")
    }
  }

  def findAllCharacters() = Action {
    val characters = characterRepo.findAll()
    Ok(Json.toJson(characters))
  }

  def mapToCharacterResource(request: Request[JsValue])
                            (action: CharacterResource => Result)
                            (implicit characterReads: Reads[CharacterResource]): Result =
    request.body.validate[CharacterResource](characterReads).fold(
      valid = action,
      invalid = e => {
        val error = e.mkString
        Logger.error(error)
        BadRequest(error)
      }
    )
}
