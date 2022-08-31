package services

import models.{Order, OrderTable, PaymentTable, UserTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


class OrderService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  val orders = TableQuery[OrderTable]
  val payments = TableQuery[PaymentTable]
  val users = TableQuery[UserTable]

  def listAll: Future[Seq[Order]] = dbConfig.db.run(orders.result)

  def get(id: Long): Future[Option[Order]] =
    dbConfig.db.run(orders.filter(_.id === id).result.headOption)

  def add(order: Order): Future[Order] =
    dbConfig.db.run(orders += order).map(_ => order)

  def update(id: Long, order: Order): Future[Int] =
    dbConfig.db.run(orders.filter(_.id === id).update(order.copy(id)))

  def delete(id: Long): Future[Int] = dbConfig.db.run(orders.filter(_.id === id).delete)

  def listByUser(userId: Long): Future[Seq[Order]] =
    dbConfig.db.run(orders.filter(_.userId === userId).result)
}