package services

import models.{AuthToken, AuthTokenTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class AuthTokenService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                (implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  val authTokens = TableQuery[AuthTokenTable]

  def create(userId: Long): Future[AuthToken] = dbConfig.db.run {
    (authTokens.map(r => r.userId)
      returning authTokens.map(_.id)
      into { case (userId, id) => AuthToken(id, userId) }
      ) += userId
  }

}