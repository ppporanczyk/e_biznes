package services
import models.{Category, CategoryTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CategoryService @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                               (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  val categories = TableQuery[CategoryTable]

  def listAll: Future[Seq[Category]] = dbConfig.db.run(categories.result)

  def get(id: Long): Future[Option[Category]] =
    dbConfig.db.run(categories.filter(_.id === id).result.headOption)

  def add(category: Category): Future[Category] =
    dbConfig.db.run(categories += category).map(_ => category)

  def update(id: Long, category: Category): Future[Int] =
    dbConfig.db.run(categories.filter(_.id === id).update(category.copy(id)))

  def delete(id: Long): Future[Int] = dbConfig.db.run(categories.filter(_.id === id).delete)

}