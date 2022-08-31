package services

import models.{Payment, PaymentTable, Product, UserTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


class PaymentService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  val payments = TableQuery[PaymentTable]
  val users = TableQuery[UserTable]

  def listAll: Future[Seq[Payment]] = dbConfig.db.run(payments.result)

  def get(id: Long): Future[Option[Payment]] =
    dbConfig.db.run(payments.filter(_.id === id).result.headOption)

  def add(payment: Payment): Future[Payment] =
    dbConfig.db.run(payments += payment).map(_ => payment)

  def update(id: Long, payment: Payment): Future[Int] =
    dbConfig.db.run(payments.filter(_.id === id).update(payment.copy(id)))

  def delete(id: Long): Future[Int] = dbConfig.db.run(payments.filter(_.id === id).delete)

  def listByOrder(orderId: Long): Future[Seq[Payment]] =
    dbConfig.db.run(payments.filter(_.orderId === orderId).result)
}