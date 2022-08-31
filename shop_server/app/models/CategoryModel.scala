package models
import play.api.libs.json.{Json, OFormat}
import slick.jdbc.SQLiteProfile.api._

case class Category(id: Long, name: String)

object Category {
  implicit val categoryFormat: OFormat[Category] = Json.format[Category]
}

class CategoryTable(tag: Tag) extends Table[Category](tag, "category") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def name = column[String]("name")

  def * =
    (id, name) <>((Category.apply _).tupled, Category.unapply)
}

