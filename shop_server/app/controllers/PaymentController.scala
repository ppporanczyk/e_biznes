package controllers

import models.Payment
import play.api.Logging
import play.api.i18n.I18nSupport
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc._
import services.{PaymentService, UserService}

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentController @Inject()(cc: ControllerComponents, paymentService: PaymentService, userService: UserService)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with Logging with I18nSupport {


  def showAll(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    paymentService.listAll map { payments =>
      Ok(Json.toJson(payments))
    }
  }

  def showById(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    paymentService.get(id) map {
      case Some(payment) => Ok(Json.toJson(payment))
      case None => NotFound("Payment with id: " + id +" cannot be found")
    }
  }

  def add():  Action[JsValue] = Action.async(parse.json){implicit request=>
    request.body.validate[Payment] match {
      case success:JsSuccess[Payment] =>  paymentService.add(success.get).map {
        res=>
          Ok(Json.toJson(res))
      }
      case _:JsError => {Future.successful(BadRequest("invalid data - create payment"))}
    }
  }

  def update(id: Long):  Action[JsValue] = Action.async(parse.json) { implicit request=>
    request.body.validate[Payment] match {
      case success:JsSuccess[Payment] => paymentService.update(id, success.get).map {
        res=>
          Ok(Json.toJson(res))
      }
      case _:JsError => {Future.successful(BadRequest("invalid data - update payment"))}
    }
  }

  def delete(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    paymentService.delete(id) map { res =>
      Ok(Json.toJson(res))
    }
  }

  def getPaymentByOrder(orderId: Long): Action[AnyContent]=Action.async { implicit request: Request[AnyContent] =>
    paymentService.listByOrder(orderId) map { res =>
      Ok(Json.toJson(res))
    }
  }
}
