package controllers

import models._
import play.api.Logging
import play.api.i18n.I18nSupport
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc._
import services.{OrderService, PaymentService, UserService}

import javax.inject._
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class OrderController @Inject()(cc: ControllerComponents, orderService: OrderService, paymentService: PaymentService, userService: UserService)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with Logging with I18nSupport {


  def showAll(): Action[AnyContent] = Action.async {
    orderService.listAll map { orders =>
      Ok(Json.toJson(orders))
    }
  }

  def showById(id: Long): Action[AnyContent] = Action.async {
    orderService.get(id) map {
      case Some(order) => Ok(Json.toJson(order))
      case None => NotFound("Order with id: " + id +" cannot be found")
    }
  }

  def add():  Action[JsValue] = Action.async(parse.json){implicit request=>
    request.body.validate[Order] match {
      case success:JsSuccess[Order] =>  orderService.add(success.get).map {
        res=>
          Ok(Json.toJson(res))
      }
      case _:JsError => {Future.successful(BadRequest("invalid data - create order"))}
    }
  }

  def update(id: Long):  Action[JsValue] = Action.async(parse.json) { implicit request=>
    request.body.validate[Order] match {
      case success:JsSuccess[Order] => orderService.update(id, success.get).map {
        res=>
          Ok(Json.toJson(res))
      }
      case _:JsError => {Future.successful(BadRequest("invalid data - update order"))}
    }
  }

  def delete(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    orderService.delete(id) map { res => Ok(Json.toJson(res))
    }
  }

  def getOrderByUser(userId: Long): Action[AnyContent]=Action.async { implicit request: Request[AnyContent] =>
    orderService.listByUser(userId) map { res =>
      Ok(Json.toJson(res))
    }
  }

}
