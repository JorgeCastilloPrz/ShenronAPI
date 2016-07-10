package controllers.character

import java.util.concurrent.TimeUnit

import akka.util.Timeout
import auth.MemoryBasicAuthActionComponent
import model.User
import org.scalatestplus.play.PlaySpec
import play.api.mvc.Results._
import play.api.mvc.{AnyContentAsEmpty, Headers}
import play.api.test.{FakeHeaders, FakeRequest, Helpers}
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
        FakeRequest("GET", "/", fakeAuthorizationHeader(
          CharacterControllerTest.ANY_USERNAME, CharacterControllerTest.ANY_PASSWORD),
          AnyContentAsEmpty))

      Helpers.status(result) mustEqual BadRequest.header.status
    }
  }

  private def fakeRequest: FakeRequest[AnyContentAsEmpty.type] = {
    FakeRequest("POST", "/", FakeHeaders(), AnyContentAsEmpty)
  }

  def fakeAuthorizationHeader(username: String, password: String): Headers = {
    Headers.apply(("Authorization", encodeBasicAuth(username, password)));
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

  private def givenThereIsValidUser(userRepoComponent: MemoryUserRepositoryComponent): Unit = {
    userRepoComponent.userRepo.create(
      new User(CharacterControllerTest.ANY_ID,
        CharacterControllerTest.ANY_USERNAME,
        CharacterControllerTest.ANY_PASSWORD))
  }

  object CharacterControllerTest {
    val ANY_USERNAME = "testuser"
    val ANY_PASSWORD = "123456"
    val ANY_ID = 10L
  }

}
