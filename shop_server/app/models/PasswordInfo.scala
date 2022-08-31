package models
import play.api.libs.json.{Json, OFormat}
import slick.jdbc.SQLiteProfile.api._

case class PasswordInfoDto(id: Long, providerId: String, providerKey: String, hasher: String, password: String, salt: Option[String])
object PasswordInfoDto {
  implicit val orderFormat: OFormat[PasswordInfoDto] = Json.format[PasswordInfoDto]
}

class PasswordInfoTable(tag: Tag) extends Table[PasswordInfoDto](tag, "passwordInfo") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def providerId = column[String]("providerId")

  def providerKey = column[String]("providerKey")

  def hasher = column[String]("hasher")

  def password = column[String]("password")

  def salt = column[Option[String]]("salt")

  override def * = (id, providerId, providerKey, hasher, password, salt) <> ((PasswordInfoDto.apply _).tupled, PasswordInfoDto.unapply)
}
