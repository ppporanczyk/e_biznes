package models

import play.api.libs.json.{Json, OFormat}
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.ProvenShape

case class Order(id: Long, address: String, description: String, amount: Int, productId: Long, userId: Long)

object Order {
  implicit val orderFormat: OFormat[Order] = Json.format[Order]
}

class OrderTable(tag: Tag) extends Table[Order](tag, "order") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def address = column[String]("address")
  def description = column[String]("description")
  def amount = column[Int]("amount")
  def productId = column[Long]("productId")
  def productFk = foreignKey("productFk",
    productId, TableQuery[ProductTable])(_.id)
  def userId = column[Long]("userId")
  def userFk = foreignKey("userFk",
    userId, TableQuery[UserTable])(_.id)

  override def * : ProvenShape[Order] =
    (id, address, description, amount, productId, userId) <> ((Order.apply _).tupled, Order.unapply)

}
