package services

import models.{PasswordInfoDto, PasswordInfoTable}
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

class PasswordInfoService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                   (implicit ec: ExecutionContext,
                                    implicit val classTag: ClassTag[PasswordInfo])
  extends DelegableAuthInfoDAO[PasswordInfo] with HasDatabaseConfigProvider[JdbcProfile]{


  val passwordInfos = TableQuery[PasswordInfoTable]

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = dbConfig.db.run {
    passwordInfos.filter(_.providerId === loginInfo.providerID)
      .filter(_.providerKey === loginInfo.providerKey)
      .result
      .headOption
  }.map(_.map(dto => PasswordInfo(dto.hasher, dto.password, dto.salt)))

  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = dbConfig.db.run {
    (passwordInfos.map(c => (c.providerId, c.providerKey, c.hasher, c.password, c.salt))
      returning passwordInfos.map(_.id)
      into { case ((providerId, providerKey, hasher, password, salt), id) => PasswordInfoDto(id, providerId, providerKey, hasher, password, salt) }
      ) += (loginInfo.providerID, loginInfo.providerKey, authInfo.hasher, authInfo.password, authInfo.salt)
  }.map(_ => authInfo)

  override def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = dbConfig.db.run {
    passwordInfos.filter(_.providerId === loginInfo.providerID)
      .filter(_.providerKey === loginInfo.providerKey)
      .map(u => (u.hasher, u.password, u.salt))
      .update((authInfo.hasher, authInfo.password, authInfo.salt))
  }.map(_ => authInfo)

  override def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] =
    find(loginInfo)
      .flatMap {
        case Some(_) => update(loginInfo, authInfo)
        case None => add(loginInfo, authInfo)
      }

  override def remove(loginInfo: LoginInfo): Future[Unit] = dbConfig.db.run {
    passwordInfos.filter(_.providerId === loginInfo.providerID)
      .filter(_.providerKey === loginInfo.providerKey)
      .delete
  }.map(_ => ())
}