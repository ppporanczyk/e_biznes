package controllers
import models.Category
import play.api.Logging
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.libs.json.OFormat.oFormatFromReadsAndOWrites
import play.api.libs.json.Reads._
import play.api.mvc._
import services.CategoryService

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class CategoryController @Inject()(cc: ControllerComponents, categoryService: CategoryService)
extends AbstractController(cc) with Logging {


  def showAll(): Action[AnyContent] = Action.async {
    categoryService.listAll.map( categories =>
      Ok(Json.toJson(categories))
    )
  }

  def showById(id: Long): Action[AnyContent] = Action.async {
    categoryService.get(id) map {
      case Some(res) =>
        Ok(Json.toJson(res))
      case None => NotFound("Category with id: " + id +" cannot be found")
    }
  }

  def add(): Action[JsValue] = Action.async(parse.json){implicit request=>
    request.body.validate[Category] match {
      case success:JsSuccess[Category] =>  categoryService.add(success.get).map {
        res=>
          Ok(Json.toJson(res))
      }
      case _:JsError => {Future.successful(BadRequest("invalid data - create category"))}
    }
  }

  def update(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request=>
    request.body.validate[Category] match {
      case success:JsSuccess[Category] => categoryService.update(id, success.get).map {
        res=>
          Ok(Json.toJson(res))
      }
      case _:JsError => {Future.successful(BadRequest("invalid data - update category"))}
    }
  }

  def delete(id: Long): Action[AnyContent] = Action.async {
    categoryService.delete(id) map { res => Ok(Json.toJson(res))}
  }

}
