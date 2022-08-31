package models
import play.api.libs.json.{Json, OFormat}
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.ProvenShape

case class Product(id: Long=0, name: String, description: String, price: BigDecimal, categoryId: Long)

object Product {
  implicit val productFormat: OFormat[Product] = Json.format[Product]
}

class ProductTable(tag: Tag) extends Table[Product](tag, "product") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def name = column[String]("name")
  def description = column[String]("description")
  def price = column[BigDecimal]("price")
  def categoryId = column[Long]("categoryId")
  def categoryFk = foreignKey("categoryFk",
    categoryId, TableQuery[CategoryTable])(_.id)

  override def * : ProvenShape[Product] =
    (id, name, description, price, categoryId) <> ((Product.apply _).tupled, Product.unapply)

}
