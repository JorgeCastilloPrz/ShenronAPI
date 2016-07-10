package controllers.character

import java.util.concurrent.TimeUnit

import akka.util.Timeout
import auth.MemoryBasicAuthActionComponent
import model.{User, Character}
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.mvc.Results._
import play.api.mvc.{AnyContentAsEmpty, Headers}
import play.api.test.{FakeApplication, FakeHeaders, FakeRequest, Helpers}
import repository.memory.{MemoryCharacterRepositoryComponent, MemoryUserRepositoryComponent}

import scala.language.postfixOps

/**
  * @author jorge
  * @since 7/07/16
  */
class CharacterControllerTest extends PlaySpec {

  implicit val timeout = Timeout(5, TimeUnit.SECONDS)

  "findCharacter request" should {
    "return Unauthorized on not authenticated request" in {
      val controller = givenCharacterController

      val result = controller.findCharacter(None)(fakeRequest)

      Helpers.status(result) mustEqual Unauthorized.header.status
    }

    "return BadRequest on required parameters absence" in {
      val controller = givenCharacterController
      givenThereIsValidUser(controller)

      val result = controller.findCharacter(None)(
        FakeRequest("GET", "/characters", fakeAuthorizationHeader(
          CharacterControllerTest.ANY_USERNAME, CharacterControllerTest.ANY_PASSWORD),
          AnyContentAsEmpty))

      Helpers.status(result) mustEqual BadRequest.header.status
    }

    "return BadRequest on unknown paramters sent" in {
      val controller = givenCharacterController
      givenThereIsValidUser(controller)

      val result = controller.findCharacter(None)(
        FakeRequest("GET", "/characters/?any_random_param=true", fakeAuthorizationHeader(
          CharacterControllerTest.ANY_USERNAME, CharacterControllerTest.ANY_PASSWORD),
          AnyContentAsEmpty))

      Helpers.status(result) mustEqual BadRequest.header.status
    }

    "return Success with json character into its body when querying for double criteria" in {
      val controller = givenCharacterController
      givenThereIsValidUser(controller)
      givenThereIsCharacter(controller)

      val result = controller.findCharacter(
        Option(CharacterControllerTest.ANY_ID),
        Option(CharacterControllerTest.ANY_CHARACTER_NAME))(
        FakeRequest("GET", "/characters/", fakeAuthorizationHeader(
          CharacterControllerTest.ANY_USERNAME, CharacterControllerTest.ANY_PASSWORD),
          AnyContentAsEmpty))

      Helpers.status(result) mustEqual Ok.header.status
    }
  }

  private def fakeRequest: FakeRequest[AnyContentAsEmpty.type] = {
    FakeRequest("GET", "/characters", FakeHeaders(), AnyContentAsEmpty)
  }

  def fakeAuthorizationHeader(username: String, password: String): Headers = {
    Headers.apply(("Authorization", encodeBasicAuth(username, password)))
  }

  def encodeBasicAuth(username: String, password: String): String = {
    val encodedCredentials = new sun.misc.BASE64Encoder()
      .encodeBuffer((username + ":" + password).getBytes())
    "Basic " + encodedCredentials
  }

  private def givenCharacterController: CharacterController with MemoryCharacterRepositoryComponent with MemoryUserRepositoryComponent with MemoryBasicAuthActionComponent = {
    new CharacterController
      with MemoryCharacterRepositoryComponent
      with MemoryUserRepositoryComponent
      with MemoryBasicAuthActionComponent
  }

  private def givenThereIsValidUser(userRepoComponent: MemoryUserRepositoryComponent) = {
    userRepoComponent.userRepo.create(
      new User(CharacterControllerTest.ANY_ID,
        CharacterControllerTest.ANY_USERNAME,
        CharacterControllerTest.ANY_PASSWORD))
  }

  def givenThereIsCharacter(characterRepoComponent: MemoryCharacterRepositoryComponent) = {
    characterRepoComponent.characterRepo.create(
      new Character(CharacterControllerTest.ANY_ID,
        CharacterControllerTest.ANY_CHARACTER_NAME,
        CharacterControllerTest.ANY_DESCRIPTION,
        CharacterControllerTest.ANY_PHOTO_URL)
    )
  }

  object CharacterControllerTest {
    val ANY_USERNAME = "testuser"
    val ANY_PASSWORD = "123456"
    val ANY_CHARACTER_NAME = "goku"
    val ANY_DESCRIPTION = "Goku is great a badass and he is the main character in DBZ."
    val ANY_PHOTO_URL = "http://anyimages.com/goku"
    val ANY_ID = 10L
  }

}
