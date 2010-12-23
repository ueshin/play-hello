package models

import _root_.siena._

import _root_.scala.collection.JavaConversions._

import _root_.java.util.{ ArrayList, Date }

class User (

  @NotNull
  var email: String

) extends Model {

  def this() = this(null)

  @Id
  var id: Long = _

  @NotNull
  var joinedAt = new Date
}

object User {

  private def all = Model.all(classOf[User])

  def get(id: Long) = Option(all.filter("id", id).get)

  def get(email: String) = Option(all.filter("email", email).get)

  def joinedRecently(limit: Int = 20, offset: Int = 0) = all.order("-joinedAt").fetch(limit, offset).toList
}
