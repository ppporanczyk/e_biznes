package models
import play.api.libs.json.{Json, OFormat}
import slick.jdbc.SQLiteProfile.api._

case class OAuth2InfoDto(id: Long, providerId: String, providerKey: String, accessToken: String, tokenType: Option[String], expiresIn: Option[Int])

object OAuth2InfoDto {
  implicit val orderFormat: OFormat[OAuth2InfoDto] = Json.format[OAuth2InfoDto]
}

class OAuth2InfoTable(tag: Tag) extends Table[OAuth2InfoDto](tag, "oAuth2Info") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def providerId = column[String]("providerId")

  def providerKey = column[String]("providerKey")

  def accessToken = column[String]("accessToken")

  def tokenType = column[Option[String]]("tokenType")

  def expiresIn = column[Option[Int]]("expiresIn")

  override def * = (id, providerId, providerKey, accessToken, tokenType, expiresIn) <> ((OAuth2InfoDto.apply _).tupled, OAuth2InfoDto.unapply)
}