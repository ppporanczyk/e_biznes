package services
import models.{CategoryTable, Product, ProductTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ProductService @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                         (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  val products = TableQuery[ProductTable]
  val categories_ = TableQuery[CategoryTable]

  def listAll: Future[Seq[Product]] = dbConfig.db.run(products.result)

  def get(id: Long): Future[Option[Product]] =
    dbConfig.db.run(products.filter(_.id === id).result.headOption)

  def add(product: Product): Future[Product] =
    dbConfig.db.run(products += product).map(_ => product)

  def update(id: Long, product: Product): Future[Int] =
    dbConfig.db.run(products.filter(_.id === id).update(product.copy(id)))

  def delete(id: Long): Future[Int] = dbConfig.db.run(products.filter(_.id === id).delete)

  def listByCategory(categoryId: Long): Future[Seq[Product]] =
    dbConfig.db.run(products.filter(_.categoryId === categoryId).result)

}