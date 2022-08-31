package models

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import play.api.libs.json.{Json, OFormat}
import slick.jdbc.SQLiteProfile.api._

case class User(id: Long, loginInfo: LoginInfo, email: String) extends Identity

object User {
  implicit val loginInfoFormat: OFormat[LoginInfo] = Json.format[LoginInfo]
  implicit val userFormat: OFormat[User] = Json.format[User]
}

case class SignOutRequest(email: String)

object SignOutRequest {
  implicit val signOutRequestForm: OFormat[SignOutRequest] = Json.using[Json.WithDefaultValues].format[SignOutRequest]
}

case class SignInRequest(email: String, password: String)

object SignInRequest {
  implicit val signInRequestForm: OFormat[SignInRequest] = Json.using[Json.WithDefaultValues].format[SignInRequest]
}

case class SignUpRequest(email: String, password: String)

object SignUpRequest {
  implicit val signUpRequestForm: OFormat[SignUpRequest] = Json.using[Json.WithDefaultValues].format[SignUpRequest]
}

case class UserDto(id: Long, providerId: String, providerKey: String, email: String)

object UserDto {
  implicit val userFormat: OFormat[UserDto] = Json.format[UserDto]
}

class UserTable(tag: Tag) extends Table[UserDto](tag, "user") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def providerId = column[String]("providerId")
  def providerKey = column[String]("providerKey")
  def email = column[String]("email")

  override def * =
    (id, providerId, providerKey, email) <>((UserDto.apply _).tupled, UserDto.unapply)
}

