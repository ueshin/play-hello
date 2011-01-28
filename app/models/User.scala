package models

import _root_.siena._

import _root_.scala.collection.JavaConversions._

import _root_.java.util.{ ArrayList, Date }

@serializable
class User (

  @NotNull
  var email: String,

  var joinedAt: Date = new Date,

  var invitedAt: Date = null

) extends Model {

  def this() = this(null)

  @Id
  var id: Long = _
}

object User {

  private def all = Model.all(classOf[User])

  def get(id: Long) = Option(all.filter("id", id).get)

  def get(email: String) = Option(all.filter("email", email).get)

  def joinedRecently(limit: Int = 20, offset: Int = 0) = all.order("-joinedAt").fetch(limit, offset).toList
}
