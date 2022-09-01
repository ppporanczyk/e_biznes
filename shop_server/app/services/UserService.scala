package services

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.api.util.PasswordInfo
import models.{User, UserDto, UserTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

@Singleton
class UserService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                           (implicit executionContext: ExecutionContext,
                            implicit val classTag: ClassTag[PasswordInfo])
  extends IdentityService[User] with HasDatabaseConfigProvider[JdbcProfile] {

  val users = TableQuery[UserTable]

  def listAll: Future[Seq[User]] = dbConfig.db.run(users.result).map(_.map(dto => toModel(dto)))

  def get(id: Long): Future[Option[User]] =
    dbConfig.db.run(users.filter(_.id === id).result.headOption).map(_.map(dto => toModel(dto)))

  def delete(id: Long): Future[Int] = dbConfig.db.run(users.filter(_.id === id).delete)

  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] =
    dbConfig.db.run(users.filter(_.providerId === loginInfo.providerID)
      .filter(_.providerKey === loginInfo.providerKey)
      .result.headOption).map(_.map(dto => toModel(dto)))

  def create(providerId: String, providerKey: String, email: String): Future[User] =
    dbConfig.db.run {(
      users.map(c => (c.providerId, c.providerKey, c.email))
      returning users.map(_.id)
      into { case ((providerId, providerKey, email), id) => UserDto(id, providerId, providerKey, email) }
      ) += (providerId, providerKey, email)
  }.map(dto => toModel(dto))

  def getUserByEmail(email: String): Future[Option[User]] = {
    dbConfig.db.run(users.filter(_.email === email).result.headOption).map(_.map(dto => toModel(dto)))
  }

  private def toModel(dto: UserDto): User =
    User(dto.id, LoginInfo(dto.providerId, dto.providerKey), dto.email)

}