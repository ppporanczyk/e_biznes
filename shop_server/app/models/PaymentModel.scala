package models

import play.api.libs.json.{Json, OFormat}
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.ProvenShape

case class Payment(id: Long, creditCardNumber: String, cvvNumber: String, expirationDate: String,  totalValue: BigDecimal, orderId: Long)

object Payment {
  implicit val paymentFormat: OFormat[Payment] = Json.format[Payment]
}

class PaymentTable(tag: Tag) extends Table[Payment](tag, "payment") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def creditCardNumber = column[String]("creditCardNumber")
  def cvvNumber = column[String]("cvvNumber")
  def expirationDate = column[String]("expirationDate")
  def totalValue = column[BigDecimal]("totalValue")
  def orderId = column[Long]("orderId")
  def orderFk = foreignKey("orderFk",
    orderId, TableQuery[OrderTable])(_.id)

  override def * : ProvenShape[Payment] =
    (id, creditCardNumber, cvvNumber, expirationDate, totalValue, orderId) <> ((Payment.apply _).tupled, Payment.unapply)

}
