package controllers.character

import java.util.concurrent.TimeUnit

import akka.util.Timeout
import auth.MemoryBasicAuthActionComponent
import model.User
import org.scalatestplus.play.PlaySpec
import play.api.mvc.AnyContentAsEmpty
import play.api.mvc.Results._
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

      val result = controller.findCharacter(Option(10L), None)(fakeRequest)

      Helpers.status(result) mustEqual Unauthorized.header.status
    }

    "return BadRequest for on request without required parameters" in {
      val controller = givenCharacterController
      givenThereIsValidUser(controller)

      val result = controller.findCharacter(None)(fakeRequest)

      Helpers.status(result) mustEqual BadRequest.header.status
    }
  }

  private def fakeRequest: FakeRequest[AnyContentAsEmpty.type] = {
    FakeRequest("POST", "/", FakeHeaders(), AnyContentAsEmpty)
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
