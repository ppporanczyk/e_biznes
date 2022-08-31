package services

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.impl.providers.OAuth2Info
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import models.{OAuth2InfoDto, OAuth2InfoTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery
import slick.jdbc.SQLiteProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

class OAuth2InfoService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                 (implicit ec: ExecutionContext,
                                  implicit val classTag: ClassTag[OAuth2Info])
  extends DelegableAuthInfoDAO[OAuth2Info] with HasDatabaseConfigProvider[JdbcProfile]{


  val oAuth2Infos = TableQuery[OAuth2InfoTable]

  override def find(loginInfo: LoginInfo): Future[Option[OAuth2Info]] = dbConfig.db.run {
    oAuth2Infos.filter(_.providerId === loginInfo.providerID)
      .filter(_.providerKey === loginInfo.providerKey)
      .result
      .headOption
  }.map(_.map(dto => OAuth2Info(dto.accessToken, dto.tokenType, dto.expiresIn)))

  override def add(loginInfo: LoginInfo, authInfo: OAuth2Info): Future[OAuth2Info] = dbConfig.db.run {
    (oAuth2Infos.map(c => (c.providerId, c.providerKey, c.accessToken, c.tokenType, c.expiresIn))
      returning oAuth2Infos.map(_.id)
      into { case ((providerId, providerKey, accessToken, tokenType, expiresIn), id) => OAuth2InfoDto(id, providerId, providerKey, accessToken, tokenType, expiresIn) }
      ) += (loginInfo.providerID, loginInfo.providerKey, authInfo.accessToken, authInfo.tokenType, authInfo.expiresIn)
  }.map(_ => authInfo)


  override def update(loginInfo: LoginInfo, authInfo: OAuth2Info): Future[OAuth2Info] = dbConfig.db.run {
    oAuth2Infos.filter(_.providerId === loginInfo.providerID)
      .filter(_.providerKey === loginInfo.providerKey)
      .map(u => (u.accessToken, u.tokenType, u.expiresIn))
      .update((authInfo.accessToken, authInfo.tokenType, authInfo.expiresIn))
  }.map(_ => authInfo)

  override def save(loginInfo: LoginInfo, authInfo: OAuth2Info): Future[OAuth2Info] =
    find(loginInfo)
      .flatMap {
        case Some(_) => update(loginInfo, authInfo)
        case None => add(loginInfo, authInfo)
      }

  override def remove(loginInfo: LoginInfo): Future[Unit] = dbConfig.db.run {
    oAuth2Infos.filter(_.providerId === loginInfo.providerID)
      .filter(_.providerKey === loginInfo.providerKey)
      .delete
  }.map(_ => ())
}