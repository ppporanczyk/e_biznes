package models
import play.api.libs.json.{Json, OFormat}
import slick.jdbc.SQLiteProfile.api._

case class AuthToken(id: Long, userId: Long)
object AuthToken {
  implicit val orderFormat: OFormat[AuthToken] = Json.format[AuthToken]
}

class AuthTokenTable(tag: Tag) extends Table[AuthToken](tag, "authToken") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def userId = column[Long]("userId")

  override def * = (id, userId) <> ((AuthToken.apply _).tupled, AuthToken.unapply)
}