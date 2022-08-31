package controllers

import models.Product
import play.api.Logging
import play.api.i18n.I18nSupport
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc._
import services.{CategoryService, ProductService}

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductController @Inject()(cc: ControllerComponents, productService: ProductService, categoryService: CategoryService)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with Logging with I18nSupport {


  def showAll(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    productService.listAll map { res =>
      Ok(Json.toJson(res))
    }
  }

  def showById(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    productService.get(id) map {
      case Some(res) => Ok(Json.toJson(res))
      case None => NotFound("Product with id: " + id +" cannot be found")
    }
  }

  def add():  Action[JsValue] = Action.async(parse.json){implicit request=>
    request.body.validate[Product] match {
      case success:JsSuccess[Product] =>  productService.add(success.get).map {
        res=>
          Ok(Json.toJson(res))
      }
      case _:JsError => {Future.successful(BadRequest("invalid data - create product"))}
    }
  }

  def update(id: Long):  Action[JsValue] = Action.async(parse.json) { implicit request=>
    request.body.validate[Product] match {
      case success:JsSuccess[Product] => productService.update(id, success.get).map {
        res=>
          Ok(Json.toJson(res))
      }
      case _:JsError => {Future.successful(BadRequest("invalid data - update product"))}
    }
  }

  def delete(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    productService.delete(id) map { res =>
      Ok(Json.toJson(res))
    }
  }

  def getProductByCategory(categoryId: Long): Action[AnyContent]=Action.async { implicit request: Request[AnyContent] =>
    productService.listByCategory(categoryId) map { res =>
      Ok(Json.toJson(res))
    }
  }

}
