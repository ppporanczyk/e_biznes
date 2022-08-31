package controllers

import play.api.Logging
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc._
import services.UserService

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UserController @Inject()(cc: ControllerComponents, userService: UserService)
  extends AbstractController(cc) with Logging with I18nSupport {

  def showAll(): Action[AnyContent] = Action.async {
    userService.listAll map { users =>
      Ok(Json.toJson(users))
    }
  }

  def showById(id: Long): Action[AnyContent] = Action.async {
    userService.get(id) map {
      case Some(res) => Ok(Json.toJson(res))
      case None => NotFound("User with id: " + id +" cannot be found")
    }
  }

  def getUserByEmail(email: String): Action[AnyContent] = Action.async {
    userService.getUserByEmail(email) map { res =>
      Ok(Json.toJson(res))
    }
  }

}
